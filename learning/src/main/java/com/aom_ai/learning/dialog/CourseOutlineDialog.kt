package com.aom_ai.learning.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.aom_ai.learning.R
import com.aom_ai.learning.adapter.CourseOutlineAdapter
import com.aom_ai.learning.databinding.LlpCourseOutlineDialogBinding
import com.aom_ai.learning.ui.model.CourseInfoUiState
import com.aom_ai.learning.viewmodel.LearningViewModel
import com.aom_ai.learning.viewmodel.LearningViewModelFactory
import com.aom_ai.uc_component.dialog.BaseDialogFragment
import com.aom_ai.uc_component.utils.dpiToPixels
import com.aom_ai.uc_component.utils.getScreenDimension

class CourseOutlineDialog : BaseDialogFragment() {

    companion object {

        @JvmStatic
        fun newInstance(): CourseOutlineDialog {
            return CourseOutlineDialog()
        }

    }

    private val viewModel: LearningViewModel by activityViewModels {
        LearningViewModelFactory(requireContext())
    }
    private var binding: LlpCourseOutlineDialogBinding? = null
    private var adapter: CourseOutlineAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LlpCourseOutlineDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseInfo = viewModel.getLatestCourseInfo()

        binding?.ivClose?.setOnClickListener {
            dismissAllowingStateLoss()
        }

        courseInfo?.let {
            initHeaderData(it)
            adapter = CourseOutlineAdapter()
            binding?.rvCourseOutline?.adapter = adapter
            adapter?.setItems(it.units)
        }


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

    private fun initHeaderData(courseInfo: CourseInfoUiState) {
        binding?.progressBar?.max = courseInfo.totalLessonsCount
        binding?.progressBar?.progress = courseInfo.completedLessonCount
        binding?.tvProgress?.text = getString(R.string.llp_lesson_complete_progress, courseInfo.completedLessonCount, courseInfo.totalLessonsCount)
        binding?.tvPercentage?.text = getString(R.string.llp_lesson_complete_percentage, (courseInfo.completedLessonCount.toFloat() / courseInfo.totalLessonsCount * 100).toInt())
    }

}
