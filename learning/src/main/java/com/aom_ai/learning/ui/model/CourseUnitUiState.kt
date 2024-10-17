package com.aom_ai.learning.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseUnitUiState(
    val unitId: String?,
    val title: String,
    val sortNumber: Int,
    val lessons: List<LessonUiState>,
    val status: LearningStatus,
    val totalLessonsCount: Int,
    val completedLessonCount: Int,
    var expended: Boolean = false
) : Parcelable
