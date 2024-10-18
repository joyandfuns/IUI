package com.aom_ai.learning

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aom_ai.learning.databinding.ActivityLlpLearningBinding
import com.aom_ai.learning.dialog.CourseOutlineDialog
import com.aom_ai.learning.ui.model.CourseInfoUiState
import com.aom_ai.learning.ui.model.LessonUiState
import com.aom_ai.learning.ui.model.ResourceUiState
import com.aom_ai.learning.viewmodel.LearningViewModel
import com.aom_ai.learning.viewmodel.LearningViewModel.LearningUiState
import com.aom_ai.learning.viewmodel.LearningViewModelFactory
import com.aom_ai.uc_component.utils.StatusBarCompat
import kotlinx.coroutines.flow.collectLatest

class LearningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLlpLearningBinding
    private val viewModel: LearningViewModel by viewModels {
        LearningViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val statusBarColor = getColor(R.color.llp_background_1)
        StatusBarCompat.setStatusBarStyle(this, statusBarColor, true)

        binding = ActivityLlpLearningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefreshLayout.setColorSchemeColors(getColor(com.aom_ai.uc_component.R.color.llp_primary_teal))
        binding.tvCourseOutline.setOnClickListener {
            CourseOutlineDialog.newInstance().show(supportFragmentManager, CourseOutlineDialog::class.java.simpleName)
        }
        binding.llPrevious.setOnClickListener { viewModel.switchToPreviousResource() }
        binding.llNext.setOnClickListener { viewModel.switchToNextResource() }
        binding.btnRefresh.setOnClickListener { refreshData() }
        binding.swipeRefreshLayout.setOnRefreshListener { refreshData() }

        refreshData()

        // 观察 UI 状态
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is LearningUiState.Loading -> showLoading()
                    is LearningUiState.Success -> {
                        hideLoading()
                        showNormal()
                    }
                    is LearningUiState.Error -> {
                        hideLoading()
                        showError()
                    }
                    is LearningUiState.Empty -> {
                        hideLoading()
                        showEmptyState()
                    }
                }
            }
            viewModel.courseInfo.collectLatest { courseInfo ->
                showCourseInfo(courseInfo)
            }
            viewModel.currentLesson.collectLatest { lesson ->
                showLessonInfo(lesson)
            }
            viewModel.currentResource.collectLatest { resource ->
                showResourceInfo(resource)
            }
            viewModel.hasPreviousResource.collectLatest { hasPrevious ->
                updatePreviousButtonState(hasPrevious)
            }
            viewModel.hasNextResource.collectLatest { hasNext ->
                updateNextButtonState(hasNext)
            }
        }
    }

    private fun refreshData() {
        viewModel.loadCourseInfo("1", "2")
    }

    private fun showLoading() {
        if (binding.swipeRefreshLayout.isRefreshing) {
            return
        }
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.loading.visibility = View.GONE
    }

    private fun showCourseInfo(courseInfo: CourseInfoUiState?) {
        val courseTitle = courseInfo?.courseId ?: ""
        if (courseTitle.isEmpty()) {
            binding.tvToolbarTitle.text = getString(com.aom_ai.uc_component.R.string.llp_back)
        } else {
            binding.tvToolbarTitle.text = getString(R.string.llp_back_to, courseTitle)
        }
        binding.tvCourseTitle.text = courseTitle
    }

    private fun showLessonInfo(lesson: LessonUiState?) {
        binding.tvResourceTitle.text = lesson?.title
        binding.tvIntroduce.text = lesson?.remark ?: ""
        binding.tvKeyLearningPoints.text = lesson?.remark ?: ""
    }

    private fun showResourceInfo(resource: ResourceUiState?) {
        binding.tvResourceTitle.text = resource?.title
    }

    private fun updatePreviousButtonState(hasPrevious: Boolean) {
        if (hasPrevious) {
            binding.llPrevious.isEnabled = true
            binding.ivPrevious.setImageResource(com.aom_ai.uc_component.R.drawable.llp_ic_back_black)
            binding.tvPrevious.setTextColor(getColor(com.aom_ai.uc_component.R.color.llp_black))
        } else {
            binding.llPrevious.isEnabled = false
            binding.ivPrevious.setImageResource(R.drawable.llp_ic_back_grey)
            binding.tvPrevious.setTextColor(getColor(com.aom_ai.uc_component.R.color.llp_disable_text))
        }
    }

    private fun updateNextButtonState(hasNext: Boolean) {
        if (hasNext) {
            binding.llNext.isEnabled = true
            binding.ivNext.setImageResource(R.drawable.llp_ic_next_black)
            binding.tvNext.setTextColor(getColor(com.aom_ai.uc_component.R.color.llp_black))
        } else {
            binding.llNext.isEnabled = false
            binding.ivNext.setImageResource(R.drawable.llp_ic_next_grey)
            binding.tvNext.setTextColor(getColor(com.aom_ai.uc_component.R.color.llp_disable_text))
        }
    }

    private fun showNormal() {
        binding.llNormal.visibility = View.VISIBLE
        binding.llError.visibility = View.GONE
    }

    private fun showError() {
        binding.llNormal.visibility = View.GONE
        binding.llError.visibility = View.VISIBLE
        binding.tvErrorHint.text = getString(R.string.llp_error_hint)
    }

    private fun showEmptyState() {
        binding.llNormal.visibility = View.GONE
        binding.llError.visibility = View.VISIBLE
        binding.tvErrorHint.text = getString(R.string.llp_empty_hint)
    }

}