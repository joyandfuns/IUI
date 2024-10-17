package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ResourceProgress(
    @SerializedName("id")
    val id: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("resource_id")
    val resourceId: String?,
    @SerializedName("resource_name")
    val resourceName: String?,
    @SerializedName("resource_type")
    val resourceType: String?,
    @SerializedName("topic_type")
    val topicType: String?,
    val progress: Int?,
    val status: Int?,
    @SerializedName("catalog_type")
    val catalogType: String?,
    @SerializedName("ext_info")
    val extInfo: String?,
    @SerializedName("create_time")
    val createTime: Date?,
    @SerializedName("update_time")
    val updateTime: Date?,
    @SerializedName("client_id")
    val clientId: String?,
    @SerializedName("lesson_id")
    val lessonId: String?
)