package com.example.data.remote

import com.example.data.model.GoogleDriveFileListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleDriveApiService {

    @GET("drive/v3/files")
    suspend fun listFiles(
        @Query("q") query: String,
        @Query("fields") fields: String = "nextPageToken, files(id, name, mimeType, size, webViewLink, webContentLink, iconLink, thumbnailLink, createdTime, modifiedTime, description)",
        @Query("pageSize") pageSize: Int = 50,
        @Query("key") apiKey: String? = null
    ): Response<GoogleDriveFileListResponse>
}
