package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.ErrorCodeItem
import com.example.data.model.MaintenanceTask
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TechnicalArticle::class,
        ErrorCodeItem::class,
        MaintenanceTask::class,
        TechnicianProfile::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sewing_tech_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            try {
                // Permanently purge any blank/empty title rows from the database
                db.execSQL("DELETE FROM technical_articles WHERE titleSinhala IS NULL OR TRIM(titleSinhala) = '' OR titleSinhala = 'null' OR LENGTH(TRIM(titleSinhala)) < 3")
                db.execSQL("DELETE FROM technical_articles WHERE titleEnglish IS NULL OR TRIM(titleEnglish) = '' OR titleEnglish = 'null' OR LENGTH(TRIM(titleEnglish)) < 3")
                db.execSQL("UPDATE technical_articles SET machineType = 'General' WHERE machineType = 'Single Needle Lockstitch' OR machineType LIKE '%Lockstitch%'")
                db.execSQL("UPDATE technical_articles SET titleEnglish = 'Rotary Hook Timing Adjustment' WHERE id = 1 AND titleEnglish LIKE '%Single Needle Lockstitch%'")
            } catch (e: Exception) {
                // ignore
            }
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    try {
                        database.appDao().insertArticles(InitialData.getDefaultArticles())
                    } catch (e: Exception) {
                        // ignore
                    }
                }
            }
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateInitialData(database.appDao())
                }
            }
        }

        private suspend fun populateInitialData(dao: AppDao) {
            dao.insertArticles(InitialData.getDefaultArticles())
            dao.insertErrorCodes(InitialData.getDefaultErrorCodes())
            dao.insertMaintenanceTasks(InitialData.getDefaultMaintenanceTasks())
            dao.insertProfile(TechnicianProfile())
        }
    }
}
