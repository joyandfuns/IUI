package com.aom_ai.learning.data

import com.google.gson.annotations.SerializedName

data class CourseUnit(
    @SerializedName("course_id")
    val unitId: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("sort_number")
    val sortNumber: Int?,
    @SerializedName("activities")
    val lessons: List<Lesson?>?
)
