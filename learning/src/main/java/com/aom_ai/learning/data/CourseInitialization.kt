package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class CourseInitialization(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("course_id")
    val courseId: String,
    @SerializedName("lesson_ids")
    val lessonIds: List<String>
)