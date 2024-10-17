package com.aom_ai.learning.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aom_ai.learning.repository.LearningRepository
import com.aom_ai.learning.ui.model.CourseInfoUiState
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

    private val _uiState = MutableStateFlow<CourseUiState>(CourseUiState.Loading)
    val uiState: StateFlow<CourseUiState> = _uiState.asStateFlow()

    private var latestCourseInfo: CourseInfoUiState? = null

    private fun updateState(state: CourseUiState) {
        _uiState.value = state
        if (state is CourseUiState.Success) {
            latestCourseInfo = state.data
        }
    }

    fun getLatestCourseInfo(): CourseInfoUiState? = latestCourseInfo

    fun loadCourseInfo(courseId: String, userId: String) {
        viewModelScope.launch {
            updateState(CourseUiState.Loading)
            try {
                repository.getCourseInfoUiState(courseId, userId)
                    .collectLatest { courseInfoUiState ->
                        if (courseInfoUiState.units.isEmpty()) {
                            updateState(CourseUiState.Empty)
                        } else {
                            updateState(CourseUiState.Success(courseInfoUiState))
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
                updateState(CourseUiState.Error)
            }
        }
    }

    sealed class CourseUiState {
        object Loading : CourseUiState()
        data class Success(val data: CourseInfoUiState) : CourseUiState()
        object Error : CourseUiState()
        object Empty : CourseUiState()
    }
}