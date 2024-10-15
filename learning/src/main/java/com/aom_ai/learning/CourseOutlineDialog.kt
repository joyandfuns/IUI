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
        return listOf(
            CourseOutlineAdapter.CourseItem.Header(completedLessons = 15, totalLessons = 50),
            CourseOutlineAdapter.CourseItem.Unit(
                title = "Unit 1: Introduction to Programming",
                completedLessons = 5,
                totalLessons = 10
            ),
            CourseOutlineAdapter.CourseItem.Lesson(
                title = "1.1 What is Programming?",
                isCompleted = true
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Video: Introduction to Programming Concepts",
                duration = "10:30",
                isCompleted = true
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Quiz: Basic Programming Concepts",
                duration = "15:00",
                isCompleted = true
            ),
            CourseOutlineAdapter.CourseItem.Lesson(
                title = "1.2 Setting Up Your Development Environment",
                isCompleted = false
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Video: Installing a Code Editor",
                duration = "8:45",
                isCompleted = false
            ),
            CourseOutlineAdapter.CourseItem.Unit(
                title = "Unit 2: Basic Programming Constructs",
                completedLessons = 3,
                totalLessons = 8
            ),
            CourseOutlineAdapter.CourseItem.Lesson(
                title = "2.1 Variables and Data Types",
                isCompleted = true
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Video: Understanding Variables",
                duration = "12:20",
                isCompleted = true
            ),
            CourseOutlineAdapter.CourseItem.Lesson(
                title = "2.2 Control Structures",
                isCompleted = false
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Reading: Introduction to If Statements",
                duration = "5:00",
                isCompleted = false
            ),
            CourseOutlineAdapter.CourseItem.SubItem(
                title = "Coding Exercise: Implementing If-Else Statements",
                duration = "20:00",
                isCompleted = false
            )
        )
    }
}
