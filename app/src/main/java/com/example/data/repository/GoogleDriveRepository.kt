package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.model.DriveFileItem
import com.example.data.remote.GoogleDriveApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class GoogleDriveRepository private constructor(context: Context) {

    private val prefs = context.getSharedPreferences("google_drive_prefs", Context.MODE_PRIVATE)
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val listType = Types.newParameterizedType(List::class.java, DriveFileItem::class.java)
    private val jsonAdapter = moshi.adapter<List<DriveFileItem>>(listType)

    private val _driveFiles = MutableStateFlow<List<DriveFileItem>>(emptyList())
    val driveFiles: Flow<List<DriveFileItem>> = _driveFiles.asStateFlow()

    private val apiService: GoogleDriveApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        apiService = retrofit.create(GoogleDriveApiService::class.java)

        loadCachedFiles()
    }

    companion object {
        @Volatile
        private var INSTANCE: GoogleDriveRepository? = null

        fun getInstance(context: Context): GoogleDriveRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = GoogleDriveRepository(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }

        const val DEFAULT_FOLDER_ID = "1A2B3C4D5E6F7G8H9I0J-KarmikaDanumaShared"
    }

    private fun loadCachedFiles() {
        val json = prefs.getString("cached_drive_files", null)
        val files = if (!json.isNullOrBlank()) {
            try {
                jsonAdapter.fromJson(json) ?: getSeedDriveFiles()
            } catch (e: Exception) {
                Log.e("GoogleDriveRepo", "Failed to deserialize cached drive files", e)
                getSeedDriveFiles()
            }
        } else {
            getSeedDriveFiles()
        }
        _driveFiles.value = files
        saveFilesToCache(files)
    }

    private fun saveFilesToCache(files: List<DriveFileItem>) {
        try {
            val json = jsonAdapter.toJson(files)
            prefs.edit().putString("cached_drive_files", json).apply()
        } catch (e: Exception) {
            Log.e("GoogleDriveRepo", "Failed to save drive files to cache", e)
        }
    }

    fun getFolderId(): String {
        return prefs.getString("google_drive_folder_id", DEFAULT_FOLDER_ID) ?: DEFAULT_FOLDER_ID
    }

    fun setFolderId(folderId: String) {
        prefs.edit().putString("google_drive_folder_id", folderId.trim()).apply()
    }

    fun getApiKey(): String {
        return prefs.getString("google_drive_api_key", "") ?: ""
    }

    fun setApiKey(apiKey: String) {
        prefs.edit().putString("google_drive_api_key", apiKey.trim()).apply()
    }

    suspend fun fetchFilesFromDrive(): Result<List<DriveFileItem>> = withContext(Dispatchers.IO) {
        val folderId = getFolderId()
        val apiKey = getApiKey().ifBlank { null }

        try {
            val query = "'$folderId' in parents and trashed = false"
            val response = apiService.listFiles(query = query, apiKey = apiKey)

            if (response.isSuccessful) {
                val networkFiles = response.body()?.files ?: emptyList()
                if (networkFiles.isNotEmpty()) {
                    // Enrich and merge with current files
                    val currentMap = _driveFiles.value.associateBy { it.id }.toMutableMap()
                    for (file in networkFiles) {
                        val category = when {
                            file.isPdf() -> "MANUAL"
                            file.isVideo() -> "VIDEO"
                            file.isImage() -> "IMAGE"
                            else -> "DOCUMENT"
                        }
                        val machineModel = extractMachineModel(file.name)
                        currentMap[file.id] = file.copy(category = category, machineModel = machineModel)
                    }
                    val updatedList = currentMap.values.toList()
                    _driveFiles.value = updatedList
                    saveFilesToCache(updatedList)
                    return@withContext Result.success(updatedList)
                }
            }
            // If network response was empty or folder had no direct API files, ensure cached files exist
            Result.success(_driveFiles.value)
        } catch (e: Exception) {
            Log.w("GoogleDriveRepo", "Fetch from Google Drive REST API encountered: ${e.message}. Using cached documents.")
            // Return cached files gracefully without crashing
            Result.success(_driveFiles.value)
        }
    }

    fun addUploadedFile(
        name: String,
        driveLinkOrId: String,
        category: String,
        machineModel: String,
        description: String? = null
    ) {
        val extractedId = extractDriveId(driveLinkOrId)
        val viewLink = if (driveLinkOrId.startsWith("http")) driveLinkOrId
        else "https://drive.google.com/file/d/$extractedId/view?usp=sharing"

        val downloadLink = "https://drive.google.com/uc?export=download&id=$extractedId"

        val mimeType = when (category) {
            "MANUAL" -> "application/pdf"
            "VIDEO" -> "video/mp4"
            "IMAGE" -> "image/jpeg"
            else -> "application/pdf"
        }

        val newItem = DriveFileItem(
            id = extractedId,
            name = name.trim(),
            mimeType = mimeType,
            sizeBytes = 3500000L,
            webViewLink = viewLink,
            webContentLink = downloadLink,
            createdTime = "2026-10-20",
            modifiedTime = "2026-10-20",
            description = description,
            category = category,
            machineModel = machineModel.ifBlank { "General" },
            uploaderName = "Admin / Factory Upload"
        )

        val updated = listOf(newItem) + _driveFiles.value.filter { it.id != newItem.id }
        _driveFiles.value = updated
        saveFilesToCache(updated)
    }

    fun deleteFile(fileId: String) {
        val updated = _driveFiles.value.filter { it.id != fileId }
        _driveFiles.value = updated
        saveFilesToCache(updated)
    }

    private fun extractDriveId(input: String): String {
        val trimmed = input.trim()
        return when {
            trimmed.contains("/file/d/") -> {
                trimmed.substringAfter("/file/d/").substringBefore("/")
            }
            trimmed.contains("id=") -> {
                trimmed.substringAfter("id=").substringBefore("&")
            }
            trimmed.contains("/folders/") -> {
                trimmed.substringAfter("/folders/").substringBefore("?")
            }
            else -> trimmed
        }
    }

    private fun extractMachineModel(fileName: String): String {
        return when {
            fileName.contains("8700", ignoreCase = true) -> "Juki DDL-8700"
            fileName.contains("9000", ignoreCase = true) -> "Juki DDL-9000"
            fileName.contains("M900", ignoreCase = true) -> "Pegasus M900"
            fileName.contains("VC2700", ignoreCase = true) -> "Yamato VC2700"
            fileName.contains("747", ignoreCase = true) -> "Siruba 747K"
            fileName.contains("7200", ignoreCase = true) -> "Brother S-7200C"
            fileName.contains("Jack", ignoreCase = true) || fileName.contains("A4", ignoreCase = true) -> "Jack A4"
            else -> "General Sewing Machine"
        }
    }

    private fun getSeedDriveFiles(): List<DriveFileItem> {
        return listOf(
            DriveFileItem(
                id = "1juki_ddl8700_manual_pdf_id",
                name = "Juki DDL-8700 Full Technical Service Manual.pdf",
                mimeType = "application/pdf",
                sizeBytes = 6450000L,
                webViewLink = "https://drive.google.com/file/d/1juki_ddl8700_manual_pdf_id/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1juki_ddl8700_manual_pdf_id",
                createdTime = "2026-10-18T10:00:00Z",
                modifiedTime = "2026-10-19T14:30:00Z",
                description = "Complete factory workshop manual covering rotary hook timing, needle bar height, thread trimmer mechanism.",
                category = "MANUAL",
                machineModel = "Juki DDL-8700",
                uploaderName = "Chief Engineer / Admin"
            ),
            DriveFileItem(
                id = "1pegasus_m900_overlock_pdf_id",
                name = "Pegasus M900 Series Overlock Service & Parts Manual.pdf",
                mimeType = "application/pdf",
                sizeBytes = 8200000L,
                webViewLink = "https://drive.google.com/file/d/1pegasus_m900_overlock_pdf_id/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1pegasus_m900_overlock_pdf_id",
                createdTime = "2026-10-17T08:15:00Z",
                modifiedTime = "2026-10-18T11:20:00Z",
                description = "4-Thread / 5-Thread high speed overlock timing diagram, upper and lower looper clearance specs.",
                category = "MANUAL",
                machineModel = "Pegasus M900",
                uploaderName = "Chief Engineer / Admin"
            ),
            DriveFileItem(
                id = "1yamato_vc2700_timing_video_mp4",
                name = "Yamato VC2700 Flatlock Spreader & Needle Timing Video.mp4",
                mimeType = "video/mp4",
                sizeBytes = 28500000L,
                webViewLink = "https://drive.google.com/file/d/1yamato_vc2700_timing_video_mp4/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1yamato_vc2700_timing_video_mp4",
                createdTime = "2026-10-16T14:00:00Z",
                modifiedTime = "2026-10-18T16:45:00Z",
                description = "High definition 15-minute video guide detailing front/rear spreader alignment and needle guard spacing.",
                category = "VIDEO",
                machineModel = "Yamato VC2700",
                uploaderName = "Training Instructor"
            ),
            DriveFileItem(
                id = "1brother_s7200c_direct_drive_pdf",
                name = "Brother S-7200C Electronic Direct-Drive Parts Catalog.pdf",
                mimeType = "application/pdf",
                sizeBytes = 4900000L,
                webViewLink = "https://drive.google.com/file/d/1brother_s7200c_direct_drive_pdf/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1brother_s7200c_direct_drive_pdf",
                createdTime = "2026-10-15T09:30:00Z",
                modifiedTime = "2026-10-17T12:00:00Z",
                description = "Exploded view diagrams and part numbers for direct-drive solenoid, cutter knife unit, and bobbin winder.",
                category = "MANUAL",
                machineModel = "Brother S-7200C",
                uploaderName = "Chief Engineer / Admin"
            ),
            DriveFileItem(
                id = "1jack_a4_circuit_diagram_img",
                name = "Jack A4 / A5 Mainboard Control Circuit Diagram.png",
                mimeType = "image/png",
                sizeBytes = 3100000L,
                webViewLink = "https://drive.google.com/file/d/1jack_a4_circuit_diagram_img/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1jack_a4_circuit_diagram_img",
                createdTime = "2026-10-14T11:00:00Z",
                modifiedTime = "2026-10-16T15:10:00Z",
                description = "Electronic circuit schematic for power board, encoder feedback cable pinout, and foot lifter solenoid.",
                category = "IMAGE",
                machineModel = "Jack A4",
                uploaderName = "Electronics Tech"
            ),
            DriveFileItem(
                id = "1siruba_747k_knife_replacement_video",
                name = "Siruba 747K Carbide Trimmer Blade Replacement.mp4",
                mimeType = "video/mp4",
                sizeBytes = 22100000L,
                webViewLink = "https://drive.google.com/file/d/1siruba_747k_knife_replacement_video/view?usp=sharing",
                webContentLink = "https://drive.google.com/uc?export=download&id=1siruba_747k_knife_replacement_video",
                createdTime = "2026-10-13T16:20:00Z",
                modifiedTime = "2026-10-15T18:00:00Z",
                description = "Hands-on video guide demonstrating proper knife angle setting and fabric trimming test.",
                category = "VIDEO",
                machineModel = "Siruba 747K",
                uploaderName = "Training Instructor"
            )
        )
    }
}
