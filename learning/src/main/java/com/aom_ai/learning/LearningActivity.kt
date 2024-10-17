package com.aom_ai.learning

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aom_ai.learning.databinding.ActivityLlpLearningBinding
import com.aom_ai.learning.dialog.CourseOutlineDialog
import com.aom_ai.learning.ui.model.CourseInfoUiState
import com.aom_ai.learning.viewmodel.LearningViewModel
import com.aom_ai.learning.viewmodel.LearningViewModel.CourseUiState
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

        binding.tvCourseOutline.setOnClickListener {
            CourseOutlineDialog.newInstance().show(supportFragmentManager, CourseOutlineDialog::class.java.simpleName)
        }

        // 开始加载数据
        viewModel.loadCourseInfo("1", "2")

        // 观察 UI 状态
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collectLatest { state ->
                when (state) {
                    is CourseUiState.Loading -> showLoading()
                    is CourseUiState.Success -> showCourseInfo(state.data)
                    is CourseUiState.Error -> showError()
                    is CourseUiState.Empty -> showEmptyState()
                }
            }
        }
    }

    private fun showLoading() {
        // 显示加载中的 UI
    }

    private fun showCourseInfo(courseInfo: CourseInfoUiState) {
        // 显示课程信息
    }

    private fun showError() {
        // 显示错误信息，可能是一个对话框或者错误页面
    }

    private fun showEmptyState() {
        // 显示空页面状态
    }

}