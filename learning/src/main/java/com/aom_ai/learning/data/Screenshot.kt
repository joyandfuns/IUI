package com.aom_ai.learning.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screenshot(
    @SerializedName("interval")
    val interval: Long?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?
) : Parcelable
