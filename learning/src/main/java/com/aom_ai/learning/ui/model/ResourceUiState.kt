package com.aom_ai.learning.ui.model

import android.os.Parcelable
import com.aom_ai.learning.data.DocumentExtend
import com.aom_ai.learning.data.VideoExtend
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourceUiState(
    val id: Long?,
    val title: String,
    val duration: Int,
    val resourceId: String?,
    val resourceType: ResourceType,
    val studyTime: Int,
    val documentExtend: DocumentExtend?,
    val videoExtend: VideoExtend?,
    val lessonStatus: LearningStatus,
    val status: LearningStatus,
    val isPlaying: Boolean
) : Parcelable
