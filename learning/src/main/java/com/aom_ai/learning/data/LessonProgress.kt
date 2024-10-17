package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class LessonProgress(
    @SerializedName("id")
    val id: String?,
    @SerializedName("user_id")
    val userId: String?,
    @SerializedName("lesson_id")
    val lessonId: String?,
    @SerializedName("course_id")
    val courseId: String?,
    @SerializedName("status")
    val status: Int?
)