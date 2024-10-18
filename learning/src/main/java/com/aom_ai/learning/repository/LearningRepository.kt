package com.aom_ai.learning.repository

import android.content.Context
import com.aom_ai.learning.data.CourseInfo
import com.aom_ai.learning.data.CourseUnit
import com.aom_ai.learning.data.Lesson
import com.aom_ai.learning.ui.model.CourseInfoUiState
import com.aom_ai.learning.ui.model.CourseUnitUiState
import com.aom_ai.learning.ui.model.LearningStatus
import com.aom_ai.learning.ui.model.LessonUiState
import com.aom_ai.learning.ui.model.ResourceType
import com.aom_ai.learning.ui.model.ResourceUiState
import com.google.gson.Gson
import com.aom_ai.learning.data.CourseInitialization
import com.aom_ai.learning.data.CourseRecord
import com.aom_ai.learning.data.LessonProgress
import com.aom_ai.learning.data.LessonRecord
import com.aom_ai.learning.data.Resource
import com.aom_ai.learning.data.ResourceProgress
import com.aom_ai.learning.data.ResourceRecord
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class LearningRepository(private val context: Context) {

    fun getCourseInfoUiState(courseId: String, userId: String): Flow<CourseInfoUiState> = flow {
        // get course info
        val courseInfo = getCourseInfo(courseId)
        val emptyCourseInfoUiState = CourseInfoUiState(null, 0, 0, 0, emptyList())
        if (courseInfo == null) {
            emit(emptyCourseInfoUiState)
            return@flow
        }

        // init lessons record
        var lessonIds: List<String> = emptyList()
        lessonIds = courseInfo.units?.flatMap { unit -> unit?.lessons?.mapNotNull { lesson -> lesson?.id } ?: emptyList() } ?: emptyList()
        initializeCourseStudyRecords(CourseInitialization(userId, courseId, lessonIds))

        // get lessons progress
        val lessonsProgress = getCourseLessonsProgress(courseId, userId)

        val (firstUncompletedUnit, firstUncompletedLesson) = findFirstUncompletedUnit(courseInfo.units, lessonsProgress)
        val resourcesProgress = if (firstUncompletedUnit != null) {
            getLessonResourcesProgress(firstUncompletedLesson?.id ?: "", userId)
        } else {
            null
        }

        var hasFindFirstUncompletedUnit = false
        val completedLessonCount = lessonsProgress?.values?.count { it?.status == LearningStatus.COMPLETED.status } ?: 0
        val courseInfoUiState = CourseInfoUiState(
            courseId = courseInfo.courseId,
            totalLessonsCount = courseInfo.lessonsCount ?: 0,
            completedLessonCount = completedLessonCount,
            modulesCount = courseInfo.modulesCount ?: 0,
            units = courseInfo.units?.mapNotNull { unit ->
                unit?.let {
                    val status = if (unit.unitId != null && unit.unitId == firstUncompletedUnit?.unitId) {
                        hasFindFirstUncompletedUnit = true
                        LearningStatus.IN_PROGRESS
                    } else if (hasFindFirstUncompletedUnit) {
                        LearningStatus.NOT_STARTED
                    } else {
                        LearningStatus.COMPLETED
                    }
                    convertToCourseUnitUiState(
                        it,
                        firstUncompletedLesson,
                        lessonsProgress,
                        resourcesProgress,
                        status
                    )
                }
            } ?: emptyList()
        )

        emit(courseInfoUiState)
    }

    private fun findFirstUncompletedUnit(
        units: List<CourseUnit?>?,
        lessonsProgress: Map<String?, LessonProgress?>?
    ): Pair<CourseUnit?, Lesson?> {
        units?.forEach { unit ->
            unit?.lessons?.forEach { lesson ->
                val lessonId = lesson?.id
                if (lessonId != null && lessonsProgress?.get(lessonId)?.status != LearningStatus.COMPLETED.status) {
                    return Pair(unit, lesson)
                }
            }
        }
        return Pair(null, null)
    }

    private fun convertToCourseUnitUiState(
        unit: CourseUnit,
        firstUncompletedLesson: Lesson?,
        lessonsProgress: Map<String?, LessonProgress?>?,
        resourcesProgress: Map<String?, ResourceProgress?>?,
        unitLearningStatus: LearningStatus
    ): CourseUnitUiState {
        var hasFindFirstUncompletedLesson = false
        val totalLessonsCount = unit.lessons?.size ?: 0
        val completedLessonCount = unit.lessons?.count { lesson ->
            lessonsProgress?.get(lesson?.id)?.status == LearningStatus.COMPLETED.status
        } ?: 0
        return CourseUnitUiState(
            unitId = unit.unitId,
            title = unit.title ?: "",
            sortNumber = unit.sortNumber ?: -1,
            lessons = unit.lessons?.mapNotNull { lesson ->
                lesson?.let {
                    val lessonLearningStatus = when (unitLearningStatus) {
                        LearningStatus.COMPLETED -> LearningStatus.COMPLETED
                        LearningStatus.IN_PROGRESS -> {
                            if (lesson.id == firstUncompletedLesson?.id) {
                                hasFindFirstUncompletedLesson = true
                                LearningStatus.IN_PROGRESS
                            } else if (hasFindFirstUncompletedLesson) {
                                LearningStatus.COMPLETED
                            } else {
                                LearningStatus.NOT_STARTED
                            }
                        }
                        else -> LearningStatus.NOT_STARTED
                    }
                    convertToLessonUiState(it, lessonLearningStatus, resourcesProgress)
                }
            } ?: emptyList(),
            status = unitLearningStatus,
            totalLessonsCount = totalLessonsCount,
            completedLessonCount = completedLessonCount,
            expended = unitLearningStatus == LearningStatus.IN_PROGRESS
        )
    }

    private fun convertToLessonUiState(
        lesson: Lesson,
        lessonLearningStatus: LearningStatus,
        resourcesProgress: Map<String?, ResourceProgress?>?,
    ): LessonUiState {
        return LessonUiState(
            id = lesson.id,
            title = lesson.name ?: "",
            remark = lesson.remark ?: "",
            orderNo = lesson.orderNo ?: 0,
            resources = lesson.resources?.mapNotNull { resource ->
                resource?.let {
                    convertToResourceUiState(it, lessonLearningStatus, resourcesProgress)
                }
            } ?: emptyList(),
            status = lessonLearningStatus,
            expended = lessonLearningStatus == LearningStatus.IN_PROGRESS
        )
    }

    private fun convertToResourceUiState(
        resource: Resource,
        lessonLearningStatus: LearningStatus,
        resourcesProgress: Map<String?, ResourceProgress?>?,
    ): ResourceUiState {
        val resourceStatus = when (lessonLearningStatus) {
            LearningStatus.COMPLETED -> LearningStatus.COMPLETED
            LearningStatus.IN_PROGRESS -> {
                when (resourcesProgress?.get(resource.resourceId)?.status) {
                    LearningStatus.COMPLETED.status -> LearningStatus.COMPLETED
                    LearningStatus.IN_PROGRESS.status -> LearningStatus.IN_PROGRESS
                    LearningStatus.NOT_STARTED.status -> LearningStatus.NOT_STARTED
                    else -> LearningStatus.NOT_STARTED
                }
            }
            else -> LearningStatus.NOT_STARTED
        }
        val resourceType = when (resource.resourceType) {
            ResourceType.VIDEO.type -> ResourceType.VIDEO
            ResourceType.AUDIO.type -> ResourceType.AUDIO
            else -> ResourceType.DOCUMENT
        }
        val title = when (resourceType) {
            ResourceType.VIDEO -> resource.videoExtend?.title ?: ""
            ResourceType.AUDIO -> resource.videoExtend?.title ?: ""
            else -> resource.documentExtend?.title ?: ""
        }
        return ResourceUiState(
            id = resource.id,
            title = title,
            duration = resource.studyTime ?: 0,
            resourceId = resource.resourceId,
            resourceType = resourceType,
            studyTime = resource.studyTime ?: 0,
            documentExtend = resource.documentExtend,
            videoExtend = resource.videoExtend,
            lessonStatus = lessonLearningStatus,
            status = resourceStatus,
            isPlaying = resourceStatus == LearningStatus.IN_PROGRESS
        )
    }

    suspend fun getCourseInfo(courseId: String): CourseInfo? {
        // read json file and convert to CourseInfo object
        val jsonString = readJsonFromAssets("course.json")
        return jsonString?.let {
            val gson = Gson()
            gson.fromJson(it, CourseInfo::class.java)
        }
    }

    suspend fun initializeCourseStudyRecords(courseInitialization: CourseInitialization) {

    }

    suspend fun getCourseLessonsProgress(courseId: String, userId: String): Map<String?, LessonProgress?>? {
        val jsonString = readJsonFromAssets("course_lesson.json")
        return jsonString?.let {
            val gson = Gson()
            val type = object : TypeToken<Map<String?, LessonProgress?>>() {}.type
            gson.fromJson(it, type)
        }
    }

    suspend fun getLessonResourcesProgress(lessonId: String, userId: String): Map<String?, ResourceProgress?>? {
        val jsonString = readJsonFromAssets("lesson_resource.json")
        return jsonString?.let {
            val gson = Gson()
            val type = object : TypeToken<Map<String?, ResourceProgress?>>() {}.type
            gson.fromJson(it, type)
        }
    }

    suspend fun updateResourceStatus(resourceRecord: ResourceRecord) {

    }

    suspend fun updateLessonStatus(resourceRecord: LessonRecord) {

    }

    suspend fun completeCourse(courseRecord: CourseRecord) {

    }

    private fun readJsonFromAssets(fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}