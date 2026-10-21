package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ErrorCodeItem
import com.example.data.model.MaintenanceTask
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // Technical Articles - Strictly excludes any items where title is blank or empty or null
    @Query("SELECT * FROM technical_articles WHERE titleSinhala IS NOT NULL AND TRIM(titleSinhala) != '' AND titleSinhala != 'null' AND LENGTH(TRIM(titleSinhala)) >= 3 AND titleEnglish IS NOT NULL AND TRIM(titleEnglish) != '' AND titleEnglish != 'null' AND LENGTH(TRIM(titleEnglish)) >= 3 ORDER BY id DESC")
    fun getAllArticles(): Flow<List<TechnicalArticle>>

    @Query("SELECT * FROM technical_articles WHERE isBookmarked = 1 AND titleSinhala IS NOT NULL AND TRIM(titleSinhala) != '' AND titleSinhala != 'null' AND LENGTH(TRIM(titleSinhala)) >= 3 AND titleEnglish IS NOT NULL AND TRIM(titleEnglish) != '' AND titleEnglish != 'null' AND LENGTH(TRIM(titleEnglish)) >= 3 ORDER BY id DESC")
    fun getBookmarkedArticles(): Flow<List<TechnicalArticle>>

    @Query("SELECT * FROM technical_articles WHERE id = :id")
    suspend fun getArticleById(id: Long): TechnicalArticle?

    @Query("SELECT * FROM technical_articles WHERE id = :id")
    fun getArticleFlowById(id: Long): Flow<TechnicalArticle?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: TechnicalArticle): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<TechnicalArticle>)

    @Update
    suspend fun updateArticle(article: TechnicalArticle)

    @Delete
    suspend fun deleteArticle(article: TechnicalArticle)

    @Query("UPDATE technical_articles SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun setBookmark(id: Long, isBookmarked: Boolean)

    @Query("DELETE FROM technical_articles WHERE titleSinhala IS NULL OR TRIM(titleSinhala) = '' OR titleSinhala = 'null' OR LENGTH(TRIM(titleSinhala)) < 3 OR titleEnglish IS NULL OR TRIM(titleEnglish) = '' OR titleEnglish = 'null' OR LENGTH(TRIM(titleEnglish)) < 3")
    suspend fun deleteBlankArticles()

    @Query("DELETE FROM technical_articles")
    suspend fun clearAllArticles()

    // Error Codes
    @Query("SELECT * FROM error_codes ORDER BY code ASC")
    fun getAllErrorCodes(): Flow<List<ErrorCodeItem>>

    @Query("SELECT * FROM error_codes WHERE id = :id")
    suspend fun getErrorCodeById(id: Long): ErrorCodeItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErrorCode(item: ErrorCodeItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertErrorCodes(items: List<ErrorCodeItem>)

    @Update
    suspend fun updateErrorCode(item: ErrorCodeItem)

    @Delete
    suspend fun deleteErrorCode(item: ErrorCodeItem)

    @Query("DELETE FROM error_codes")
    suspend fun clearAllErrorCodes()

    // Maintenance Tasks
    @Query("SELECT * FROM maintenance_tasks ORDER BY id ASC")
    fun getAllMaintenanceTasks(): Flow<List<MaintenanceTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMaintenanceTasks(tasks: List<MaintenanceTask>)

    @Query("UPDATE maintenance_tasks SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateMaintenanceTaskStatus(id: Long, isCompleted: Boolean)

    @Query("UPDATE maintenance_tasks SET isCompleted = 0")
    suspend fun resetAllMaintenanceTasks()

    // Technician Profile
    @Query("SELECT * FROM technician_profile WHERE id = 1")
    fun getProfile(): Flow<TechnicianProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: TechnicianProfile)

    @Update
    suspend fun updateProfile(profile: TechnicianProfile)

    @Query("UPDATE technician_profile SET quizScore = :score, membershipLevel = :level WHERE id = 1")
    suspend fun updateQuizResult(score: Int, level: String)
}
