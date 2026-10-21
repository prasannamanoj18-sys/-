package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.localization.AppLanguage

@Entity(tableName = "error_codes")
data class ErrorCodeItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val code: String, // e.g. "E-01", "E-02", "E-07"
    val system: String, // e.g. "Juki Direct Drive", "Brother Servo Box", "Jack C4 / A4"
    val titleSinhala: String,
    val titleEnglish: String,
    val titleTamil: String,
    val causeSinhala: String,
    val causeEnglish: String,
    val causeTamil: String,
    val solutionSinhala: String,
    val solutionEnglish: String,
    val solutionTamil: String
) {
    fun getTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> titleSinhala.ifEmpty { titleEnglish }
            AppLanguage.ENGLISH -> titleEnglish.ifEmpty { titleSinhala }
            AppLanguage.TAMIL -> titleTamil.ifEmpty { titleEnglish }
        }
    }

    fun getCause(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> causeSinhala.ifEmpty { causeEnglish }
            AppLanguage.ENGLISH -> causeEnglish.ifEmpty { causeSinhala }
            AppLanguage.TAMIL -> causeTamil.ifEmpty { causeEnglish }
        }
    }

    fun getSolution(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> solutionSinhala.ifEmpty { solutionEnglish }
            AppLanguage.ENGLISH -> solutionEnglish.ifEmpty { solutionSinhala }
            AppLanguage.TAMIL -> solutionTamil.ifEmpty { solutionEnglish }
        }
    }
}
