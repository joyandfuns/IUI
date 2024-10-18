package com.aom_ai.learning.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LessonUiState(
    val id: String?,
    val title: String,
    val remark: String,
    val orderNo: Int,
    val resources: List<ResourceUiState>,
    val status: LearningStatus,
    var expended: Boolean = false
) : Parcelable
