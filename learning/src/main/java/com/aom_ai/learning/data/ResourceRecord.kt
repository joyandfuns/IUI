package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class ResourceRecord(
    @SerializedName("user_id")
    val userId: String?,

    @SerializedName("lesson_id")
    val lessonId: String?,

    @SerializedName("resource_id")
    val resourceId: String?,

    @SerializedName("resource_type")
    val resourceType: String?,

    @SerializedName("resource_name")
    val resourceName: String?,

    @SerializedName("status")
    val status: Int?
)
