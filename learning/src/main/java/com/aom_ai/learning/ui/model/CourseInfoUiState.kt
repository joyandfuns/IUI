package com.aom_ai.learning.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseInfoUiState(
    val courseId: String?,
    val totalLessonsCount: Int,
    val completedLessonCount: Int,
    val modulesCount: Int,
    val units: List<CourseUnitUiState>
) : Parcelable

fun CourseInfoUiState.findPlayingResource(): ResourceUiState? {
    return units.asSequence()
        .flatMap { it.lessons.asSequence() }
        .flatMap { it.resources.asSequence() }
        .find { it.isPlaying }
}
