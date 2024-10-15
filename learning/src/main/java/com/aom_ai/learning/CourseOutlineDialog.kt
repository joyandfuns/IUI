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
            duration = "10:30",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource2 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = "15:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource3 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = "8:45",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val lesson1 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource1, resource2, resource3)
        )
        val resource4 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Understanding Variables",
            duration = "12:20",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource5 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Reading: Introduction to If Statements",
            duration = "5:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val resource6 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Coding Exercise: Implementing If-Else Statements",
            duration = "20:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val lesson2 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "2.1 Variables and Data Types",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource4, resource5, resource6)
        )
        val unit1 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 1: Introduction to Programming",
            completedLessons = 5,
            totalLessons = 10,
            lessons = listOf(lesson1, lesson2)
        )
        val resource7 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = "10:30",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource8 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = "15:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource9 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = "8:45",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val lesson3 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource7, resource8, resource9)
        )
        val resource10 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Understanding Variables",
            duration = "12:20",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource11 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Reading: Introduction to If Statements",
            duration = "5:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val resource12 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Coding Exercise: Implementing If-Else Statements",
            duration = "20:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val lesson4 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "2.1 Variables and Data Types",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource10, resource11, resource12)
        )
        val unit2 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 2: Basic Programming Constructs",
            completedLessons = 3,
            totalLessons = 8,
            lessons = listOf(lesson3, lesson4)
        )
        val resource13 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = "10:30",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource14 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = "15:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource15 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Installing a Code Editor",
            duration = "8:45",
            lessonStatus = CourseOutlineAdapter.LearningStatus.NOT_STARTED,
            status = CourseOutlineAdapter.LearningStatus.NOT_STARTED
        )
        val lesson5 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource13, resource14, resource15)
        )
        val unit3 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 3: Advanced Programming Concepts",
            completedLessons = 5,
            totalLessons = 10,
            lessons = listOf(lesson5)
        )
        val resource16 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Video: Introduction to Programming Concepts",
            duration = "10:30",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val resource17 = CourseOutlineAdapter.CourseItem.Resource(
            title = "Quiz: Basic Programming Concepts",
            duration = "15:00",
            lessonStatus = CourseOutlineAdapter.LearningStatus.COMPLETED,
            status = CourseOutlineAdapter.LearningStatus.COMPLETED
        )
        val lesson6 = CourseOutlineAdapter.CourseItem.Lesson(
            title = "1.1 What is Programming?",
            status = CourseOutlineAdapter.LearningStatus.COMPLETED,
            resources = listOf(resource16, resource17)
        )
        val unit4 = CourseOutlineAdapter.CourseItem.Unit(
            title = "Unit 4: Advanced Programming Constructs",
            completedLessons = 2,
            totalLessons = 5,
            lessons = listOf(lesson6)
        )
        val header = CourseOutlineAdapter.CourseItem.Header(completedLessons = 152, totalLessons = 334)
        return listOf(header, unit1, unit2, unit3, unit4)
    }
}
