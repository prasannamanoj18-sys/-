package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.local.InitialData
import com.example.data.model.ErrorCodeItem
import com.example.data.model.MaintenanceTask
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile
import kotlinx.coroutines.flow.Flow

class SewingTechRepository(private val dao: AppDao) {

    // Technical Articles
    val allArticles: Flow<List<TechnicalArticle>> = dao.getAllArticles()
    val bookmarkedArticles: Flow<List<TechnicalArticle>> = dao.getBookmarkedArticles()

    suspend fun getArticleById(id: Long): TechnicalArticle? = dao.getArticleById(id)

    fun getArticleFlowById(id: Long): Flow<TechnicalArticle?> = dao.getArticleFlowById(id)

    suspend fun insertArticle(article: TechnicalArticle): Long = dao.insertArticle(article)

    suspend fun updateArticle(article: TechnicalArticle) = dao.updateArticle(article)

    suspend fun deleteArticle(article: TechnicalArticle) = dao.deleteArticle(article)

    suspend fun toggleBookmark(id: Long, currentStatus: Boolean) {
        dao.setBookmark(id, !currentStatus)
    }

    // Error Codes
    val allErrorCodes: Flow<List<ErrorCodeItem>> = dao.getAllErrorCodes()

    suspend fun getErrorCodeById(id: Long): ErrorCodeItem? = dao.getErrorCodeById(id)

    suspend fun insertErrorCode(item: ErrorCodeItem): Long = dao.insertErrorCode(item)

    suspend fun updateErrorCode(item: ErrorCodeItem) = dao.updateErrorCode(item)

    suspend fun deleteErrorCode(item: ErrorCodeItem) = dao.deleteErrorCode(item)

    // Maintenance Tasks
    val allMaintenanceTasks: Flow<List<MaintenanceTask>> = dao.getAllMaintenanceTasks()

    suspend fun updateTaskStatus(id: Long, isCompleted: Boolean) {
        dao.updateMaintenanceTaskStatus(id, isCompleted)
    }

    suspend fun resetChecklist() {
        dao.resetAllMaintenanceTasks()
    }

    // Technician Profile
    val profile: Flow<TechnicianProfile?> = dao.getProfile()

    suspend fun updateProfile(profile: TechnicianProfile) {
        dao.updateProfile(profile)
    }

    suspend fun updateQuizResult(score: Int, level: String) {
        dao.updateQuizResult(score, level)
    }

    // Restore Factory Default Data
    suspend fun restoreFactoryDefaults() {
        dao.clearAllArticles()
        dao.clearAllErrorCodes()
        dao.insertArticles(InitialData.getDefaultArticles())
        dao.insertErrorCodes(InitialData.getDefaultErrorCodes())
        dao.insertMaintenanceTasks(InitialData.getDefaultMaintenanceTasks())
        dao.insertProfile(TechnicianProfile())
    }

    suspend fun ensureInitialDataLoaded() {
        // Purge any blank/empty articles completely from the database
        dao.deleteBlankArticles()
        val initialProfile = TechnicianProfile()
        dao.insertProfile(initialProfile)
        // Ensure standard library articles including measuring units are inserted
        dao.insertArticles(InitialData.getDefaultArticles())
    }
}
