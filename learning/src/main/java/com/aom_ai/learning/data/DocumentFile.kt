package com.aom_ai.learning.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentFile(
    @SerializedName("type")
    val type: String?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("file_urls")
    val fileUrls: List<String?>?
) : Parcelable
