package com.aom_ai.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aom_ai.learning.databinding.LlpCourseOutlineDialogBinding
import com.aom_ai.uc_component.dialog.BaseDialogFragment
import com.aom_ai.uc_component.utils.dpiToPixels
import com.aom_ai.uc_component.utils.getScreenDimension

class CourseOutlineDialog(
    private val onProceed: (() -> Unit)? = null
) : BaseDialogFragment() {

    companion object {

        @JvmStatic
        fun newInstance(
            onProceed: (() -> Unit)? = null
        ): CourseOutlineDialog {
            return CourseOutlineDialog(onProceed)
        }

    }

    private var binding: LlpCourseOutlineDialogBinding? = null
    private var adapter: CourseOutlineAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LlpCourseOutlineDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.ivClose?.setOnClickListener {
            dismissAllowingStateLoss()
        }

        adapter = CourseOutlineAdapter()
        binding?.rvCourseOutline?.adapter = adapter

        // 模拟数据
        val courseItems = createCourseOutlineData()
        adapter?.setItems(courseItems)
    }

    override fun onStart() {
        super.onStart()
        val ctx = context ?: return
        val screen = getScreenDimension(activity)
        val dialogWidth = screen.x - dpiToPixels(ctx, 48F)
        val dialogHeight = screen.y * 0.85
        dialog?.window?.setLayout(dialogWidth.toInt(), dialogHeight.toInt())
        dialog?.window?.setBackgroundDrawableResource(R.drawable.llp_bg_gray_dialog)
    }

    private fun createCourseOutlineData(): List<CourseOutlineAdapter.CourseItem> {
        val resource1 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = 30,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.VIDEO
        )
        val resource2 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = 15,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.AUDIO
        )
        val resource3 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = 45,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson1 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource1, resource2, resource3)
        )
        val resource4 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Understanding Variables",
            duration = 12,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.VIDEO
        )
        val resource5 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Reading: Introduction to If Statements",
            duration = 5,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.AUDIO
        )
        val resource6 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Coding Exercise: Implementing If-Else Statements",
            duration = 20,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson2 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "2.1 Variables and Data Types",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource4, resource5, resource6)
        )
        val unit1 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 1: Introduction to Programming",
            completedLessons = 2,
            totalLessons = 2,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            lessons = listOf(lesson1, lesson2)
        )
        val resource7 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = 30,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.AUDIO
        )
        val resource8 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = 15,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.VIDEO
        )
        val resource9 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = 45,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson3 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            isExpanded = true,
            resources = listOf(resource7, resource8, resource9)
        )
        val resource10 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Understanding Variables",
            duration = 12,
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val resource11 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Reading: Introduction to If Statements",
            duration = 20,
            lessonStatus = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            status = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val resource12 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Coding Exercise: Implementing If-Else Statements",
            duration = 20,
            lessonStatus = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            status = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            isPlaying = true,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson4 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "2.1 Variables and Data Types",
            status = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            isExpanded = true,
            resources = listOf(resource10, resource11, resource12)
        )
        val unit2 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 2: Basic Programming Constructs",
            completedLessons = 1,
            totalLessons = 2,
            status = CourseOutlineAdapter.LearningStatus.IN_PROGRESS,
            isExpanded = true,
            lessons = listOf(lesson3, lesson4)
        )
        val resource13 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = 30,
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val resource14 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = 30,
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val resource15 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = 45,
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson5 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            resources = listOf(resource13, resource14, resource15)
        )
        val unit3 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 3: Advanced Programming Concepts",
            completedLessons = 0,
            totalLessons = 1,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            lessons = listOf(lesson5)
        )
        val resource16 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = 10,
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            type = CourseOutlineAdapter.ResourceType.VIDEO
        )
        val resource17 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = 15,
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            type = CourseOutlineAdapter.ResourceType.DOCUMENT
        )
        val lesson6 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            resources = listOf(resource16, resource17)
        )
        val unit4 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 4: Advanced Programming Constructs",
            completedLessons = 0,
            totalLessons = 1,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            lessons = listOf(lesson6)
        )
        val header = CourseOutlineAdapter.CourseItem.Header(completedLessons = 3, totalLessons = 5)
        val items = mutableListOf<CourseOutlineAdapter.CourseItem>()
        items.add(header)
        addUnit(items, listOf(unit1, unit2, unit3, unit4))
        return items
    }

    private fun addUnit(items: MutableList<CourseOutlineAdapter.CourseItem>, unitList: List<CourseOutlineAdapter.CourseItem.Unit>) {
        unitList.forEach {
            items.add(it)
            if (it.isExpanded) {
                it.lessons.forEach { lesson ->
                    items.add(lesson)
                    if (lesson.isExpanded) {
                        lesson.resources.forEach { resource ->
                            items.add(resource)
                        }
                    }
                }
            }
        }
    }
}
