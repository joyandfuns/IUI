package com.aom_ai.learning.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aom_ai.learning.repository.LearningRepository
import com.aom_ai.learning.ui.model.CourseInfoUiState
import com.aom_ai.learning.ui.model.CourseUnitUiState
import com.aom_ai.learning.ui.model.LearningStatus
import com.aom_ai.learning.ui.model.LessonUiState
import com.aom_ai.learning.ui.model.ResourceUiState
import com.aom_ai.learning.ui.model.findPlayingResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LearningViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LearningViewModel::class.java)) {
            return LearningViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LearningViewModel(context: Context) : ViewModel() {

    private val repository = LearningRepository(context)

    private val _uiState = MutableStateFlow<LearningUiState>(LearningUiState.Loading)
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    private val _courseInfo = MutableStateFlow<CourseInfoUiState?>(null)
    val courseInfo: StateFlow<CourseInfoUiState?> = _courseInfo

    private val _currentUnit = MutableStateFlow<CourseUnitUiState?>(null)
    val currentUnit: StateFlow<CourseUnitUiState?> = _currentUnit

    private val _currentLesson = MutableStateFlow<LessonUiState?>(null)
    val currentLesson: StateFlow<LessonUiState?> = _currentLesson

    private val _currentResource = MutableStateFlow<ResourceUiState?>(null)
    val currentResource: StateFlow<ResourceUiState?> = _currentResource

    private val _hasPreviousResource = MutableStateFlow(false)
    val hasPreviousResource: StateFlow<Boolean> = _hasPreviousResource

    private val _hasNextResource = MutableStateFlow(false)
    val hasNextResource: StateFlow<Boolean> = _hasNextResource

    fun loadCourseInfo(courseId: String, userId: String) {
        viewModelScope.launch {
            _uiState.value = LearningUiState.Loading
            try {
                repository.getCourseInfoUiState(courseId, userId)
                    .collectLatest { courseInfoUiState ->
                        if (courseInfoUiState.units.isEmpty()) {
                            _uiState.value = LearningUiState.Empty
                        } else {
                            val playingResource = courseInfoUiState.findPlayingResource()
                            if (playingResource == null) {
                                _uiState.value = LearningUiState.Empty
                            } else {
                                updateCurrentResource(playingResource)
                                _uiState.value = LearningUiState.Success
                                _courseInfo.value = courseInfoUiState
                            }
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiState.value = LearningUiState.Error
            }
        }
    }

    fun switchToNextResource() {
        val currentCourse = _courseInfo.value ?: return
        val currentUnit = _currentUnit.value ?: return
        val currentLesson = _currentLesson.value ?: return
        val currentResource = _currentResource.value ?: return

        val nextResource = findNextResource(currentCourse, currentUnit, currentLesson, currentResource)
        if (nextResource != null) {
            updateCurrentResource(nextResource)
        }
    }

    fun switchToPreviousResource() {
        val currentCourse = _courseInfo.value ?: return
        val currentUnit = _currentUnit.value ?: return
        val currentLesson = _currentLesson.value ?: return
        val currentResource = _currentResource.value ?: return

        val previousResource = findPreviousResource(currentCourse, currentUnit, currentLesson, currentResource)
        if (previousResource != null) {
            updateCurrentResource(previousResource)
        }
    }

    private fun findNextResource(course: CourseInfoUiState, unit: CourseUnitUiState, lesson: LessonUiState, resource: ResourceUiState): ResourceUiState? {
        val resourceIndex = lesson.resources.indexOf(resource)
        if (resourceIndex < lesson.resources.size - 1) {
            // Next resource in the same lesson
            return lesson.resources[resourceIndex + 1]
        }

        val lessonIndex = unit.lessons.indexOf(lesson)
        if (lessonIndex < unit.lessons.size - 1) {
            // First resource of the next lesson in the same unit
            return unit.lessons[lessonIndex + 1].resources.firstOrNull()
        }

        val unitIndex = course.units.indexOf(unit)
        if (unitIndex < course.units.size - 1) {
            // First resource of the first lesson of the next unit
            return course.units[unitIndex + 1].lessons.firstOrNull()?.resources?.firstOrNull()
        }

        return null // No next resource available
    }

    private fun findPreviousResource(course: CourseInfoUiState, unit: CourseUnitUiState, lesson: LessonUiState, resource: ResourceUiState): ResourceUiState? {
        val resourceIndex = lesson.resources.indexOf(resource)
        if (resourceIndex > 0) {
            // Previous resource in the same lesson
            return lesson.resources[resourceIndex - 1]
        }

        val lessonIndex = unit.lessons.indexOf(lesson)
        if (lessonIndex > 0) {
            // Last resource of the previous lesson in the same unit
            return unit.lessons[lessonIndex - 1].resources.lastOrNull()
        }

        val unitIndex = course.units.indexOf(unit)
        if (unitIndex > 0) {
            // Last resource of the last lesson of the previous unit
            return course.units[unitIndex - 1].lessons.lastOrNull()?.resources?.lastOrNull()
        }

        return null // No previous resource available
    }

    private fun updateCurrentResource(resource: ResourceUiState) {
        _currentResource.value = resource
        updateCurrentLesson(findLessonForResource(resource))
        updateCurrentUnit(findUnitForResource(resource))
        updateNavigationState()
    }

    private fun updateNavigationState() {
        val currentCourse = _courseInfo.value ?: return
        val currentUnit = _currentUnit.value ?: return
        val currentLesson = _currentLesson.value ?: return
        val currentResource = _currentResource.value ?: return

        _hasPreviousResource.value = findPreviousResource(currentCourse, currentUnit, currentLesson, currentResource) != null
        _hasNextResource.value = findNextResource(currentCourse, currentUnit, currentLesson, currentResource) != null
    }

    private fun findLessonForResource(resource: ResourceUiState): LessonUiState? {
        return _courseInfo.value?.units?.flatMap { it.lessons }?.find { lesson ->
            lesson.resources.contains(resource)
        }
    }

    private fun findUnitForResource(resource: ResourceUiState): CourseUnitUiState? {
        return _courseInfo.value?.units?.find { unit ->
            unit.lessons.any { lesson -> lesson.resources.contains(resource) }
        }
    }

    private fun updateCurrentLesson(lesson: LessonUiState?) {
        _currentLesson.value = lesson
    }

    private fun updateCurrentUnit(unit: CourseUnitUiState?) {
        _currentUnit.value = unit
    }

    sealed class LearningUiState {
        object Loading : LearningUiState()
        object Success : LearningUiState()
        object Error : LearningUiState()
        object Empty : LearningUiState()
    }
}