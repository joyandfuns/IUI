package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class Resource(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("resource_id")
    val resourceId: String?,
    @SerializedName("resource_type")
    val resourceType: String?,
    @SerializedName("study_time")
    val studyTime: Int?,
    @SerializedName("document_extend")
    val documentExtend: DocumentExtend?,
    @SerializedName("video_extend")
    val videoExtend: VideoExtend?
)
