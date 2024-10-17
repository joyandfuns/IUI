package com.aom_ai.learning.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoFile(
    /**
     * 视频类型 2-mp4 3-mp3
     */
    @SerializedName("type")
    val type: Int?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("quality")
    val quality: Int?,
    @SerializedName("audio_index")
    val audioIndex: Int?,
    @SerializedName("audio_title")
    val audioTitle: String?,
    @SerializedName("audio_language")
    val audioLanguage: String?,
    @SerializedName("duration")
    val duration: Long?
) : Parcelable
