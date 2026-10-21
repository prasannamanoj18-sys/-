package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.localization.AppLanguage

@Entity(tableName = "technical_articles")
data class TechnicalArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String, // TROUBLESHOOTING, TIMING, MAINTENANCE, MANUALS
    val machineType: String, // Lockstitch, Overlock, Flatlock, Buttonhole, Bartack, All
    val difficulty: String, // Basic, Intermediate, Advanced
    val titleSinhala: String,
    val titleEnglish: String,
    val titleTamil: String,
    val summarySinhala: String,
    val summaryEnglish: String,
    val summaryTamil: String,
    val contentSinhala: String,
    val contentEnglish: String,
    val contentTamil: String,
    val keySpecsOrTiming: String,
    val isBookmarked: Boolean = false,
    val viewCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> titleSinhala.ifEmpty { titleEnglish }
            AppLanguage.ENGLISH -> titleEnglish.ifEmpty { titleSinhala }
            AppLanguage.TAMIL -> titleTamil.ifEmpty { titleEnglish }
        }
    }

    fun getSummary(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> summarySinhala.ifEmpty { summaryEnglish }
            AppLanguage.ENGLISH -> summaryEnglish.ifEmpty { summarySinhala }
            AppLanguage.TAMIL -> summaryTamil.ifEmpty { summaryEnglish }
        }
    }

    fun getContent(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> contentSinhala.ifEmpty { contentEnglish }
            AppLanguage.ENGLISH -> contentEnglish.ifEmpty { contentSinhala }
            AppLanguage.TAMIL -> contentTamil.ifEmpty { contentEnglish }
        }
    }
}
