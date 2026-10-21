package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DriveFileItem(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "mimeType") val mimeType: String = "application/pdf",
    @Json(name = "size") val sizeBytes: Long = 0L,
    @Json(name = "webViewLink") val webViewLink: String = "",
    @Json(name = "webContentLink") val webContentLink: String = "",
    @Json(name = "iconLink") val iconLink: String? = null,
    @Json(name = "thumbnailLink") val thumbnailLink: String? = null,
    @Json(name = "createdTime") val createdTime: String = "",
    @Json(name = "modifiedTime") val modifiedTime: String = "",
    @Json(name = "description") val description: String? = null,
    val category: String = "MANUAL", // MANUAL, VIDEO, IMAGE, DOCUMENT, CIRCUIT
    val machineModel: String = "General",
    val uploaderName: String = "Admin / Factory Tech",
    val localCachedPath: String? = null
) {
    fun getFormattedSize(): String {
        if (sizeBytes <= 0) return "1.5 MB"
        val kb = sizeBytes / 1024.0
        val mb = kb / 1024.0
        val gb = mb / 1024.0
        return when {
            gb >= 1.0 -> String.format("%.1f GB", gb)
            mb >= 1.0 -> String.format("%.1f MB", mb)
            else -> String.format("%.0f KB", kb)
        }
    }

    fun isPdf(): Boolean = mimeType.contains("pdf", ignoreCase = true) || name.endsWith(".pdf", ignoreCase = true)

    fun isVideo(): Boolean = mimeType.contains("video", ignoreCase = true) ||
            name.endsWith(".mp4", ignoreCase = true) ||
            name.endsWith(".mkv", ignoreCase = true) ||
            name.endsWith(".avi", ignoreCase = true)

    fun isImage(): Boolean = mimeType.contains("image", ignoreCase = true) ||
            name.endsWith(".jpg", ignoreCase = true) ||
            name.endsWith(".jpeg", ignoreCase = true) ||
            name.endsWith(".png", ignoreCase = true)

    fun getEffectiveViewUrl(): String {
        return when {
            webViewLink.isNotBlank() -> webViewLink
            id.isNotBlank() && !id.startsWith("http") -> "https://drive.google.com/file/d/$id/view?usp=sharing"
            else -> webViewLink
        }
    }

    fun getEffectiveDownloadUrl(): String {
        return when {
            webContentLink.isNotBlank() -> webContentLink
            id.isNotBlank() && !id.startsWith("http") -> "https://drive.google.com/uc?export=download&id=$id"
            webViewLink.isNotBlank() -> webViewLink
            else -> ""
        }
    }
}

@JsonClass(generateAdapter = true)
data class GoogleDriveFileListResponse(
    @Json(name = "kind") val kind: String? = null,
    @Json(name = "nextPageToken") val nextPageToken: String? = null,
    @Json(name = "files") val files: List<DriveFileItem> = emptyList()
)
