package com.aom_ai.learning.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentExtend(
    @SerializedName("title")
    val title: String?,
    @SerializedName("display_mode")
    val displayMode: String?,
    @SerializedName("default_type")
    val defaultType: String?,
    @SerializedName("source_type")
    val sourceType: String?,
    @SerializedName("page_width")
    val pageWidth: Int?,
    @SerializedName("page_height")
    val pageHeight: Int?,
    @SerializedName("files")
    val files: List<DocumentFile?>?,
    @SerializedName("hosts")
    val hosts: List<String?>?,
    @SerializedName("front_cover_url")
    val frontCoverUrl: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("page_count")
    val pageCount: Int?
) : Parcelable
