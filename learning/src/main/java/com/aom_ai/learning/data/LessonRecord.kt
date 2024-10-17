package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class LessonRecord(
    @SerializedName("user_id")
    val userId: String?,

    @SerializedName("lesson_id")
    val lessonId: String?,

    @SerializedName("course_id")
    val courseId: String?,

    /**
     * 0: not started
     * 1: in progress
     * 2: completed
     */
    @SerializedName("status")
    val status: Int?,

    @SerializedName("next_lesson_id")
    val nextLessonId: String?,
)
