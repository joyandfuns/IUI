package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class CourseInfo(
    @SerializedName("train_id")
    val courseId: String?,
    @SerializedName("lessons_count")
    val lessonsCount: Int?,
    @SerializedName("modules_count")
    val modulesCount: Int?,
    @SerializedName("courses")
    val units: List<CourseUnit?>?
)
