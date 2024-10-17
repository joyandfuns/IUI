package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class CourseRecord(
    @SerializedName("user_id")
    val userId: String?,

    @SerializedName("course_id")
    val courseId: String?,
)
