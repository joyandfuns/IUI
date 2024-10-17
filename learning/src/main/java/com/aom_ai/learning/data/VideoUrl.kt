package com.aom_ai.learning.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoUrl(
    @SerializedName("type")
    val type: Int?,
    @SerializedName("quality")
    val quality: Int?,
    @SerializedName("audio_index")
    val audioIndex: Int?,
    @SerializedName("urls")
    val urls: List<String?>?
) : Parcelable
