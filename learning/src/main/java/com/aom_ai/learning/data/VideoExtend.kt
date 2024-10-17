package com.aom_ai.learning.data

import android.os.Parcelable
import com.aom_ai.learning.data.Screenshot
import com.aom_ai.learning.data.VideoFile
import com.aom_ai.learning.data.VideoUrl
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoExtend(
    @SerializedName("title")
    val title: String?,
    @SerializedName("front_cover_url")
    val frontCoverUrl: String?,
    @SerializedName("type")
    val type: Int?,
    @SerializedName("screenshot")
    val screenshot: Screenshot?,
    @SerializedName("files")
    val files: List<VideoFile?>?,
    @SerializedName("urls")
    val urls: List<VideoUrl?>?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("duration")
    val duration: Int?
) : Parcelable
