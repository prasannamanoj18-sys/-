package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.localization.AppLanguage

@Entity(tableName = "maintenance_tasks")
data class MaintenanceTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val frequency: String, // DAILY, WEEKLY, MONTHLY
    val taskSinhala: String,
    val taskEnglish: String,
    val taskTamil: String,
    val isCompleted: Boolean = false,
    val lastCompletedDate: String = ""
) {
    fun getTitle(lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.SINHALA -> taskSinhala.ifEmpty { taskEnglish }
            AppLanguage.ENGLISH -> taskEnglish.ifEmpty { taskSinhala }
            AppLanguage.TAMIL -> taskTamil.ifEmpty { taskEnglish }
        }
    }
}
