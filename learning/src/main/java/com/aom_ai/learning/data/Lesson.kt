package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("activity_id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("remark")
    val remark: String?,
    @SerializedName("order_no")
    val orderNo: Int?,
    @SerializedName("activity_resources")
    val resources: List<Resource?>?
)
