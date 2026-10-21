package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.DriveFileItem
import com.example.data.model.ErrorCodeItem
import com.example.data.model.FiveSAuditRecord
import com.example.data.model.FiveSChecklistItem
import com.example.data.model.MaintenanceTask
import com.example.data.model.RedTagItem
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile
import com.example.data.repository.FiveSRepository
import com.example.data.repository.GoogleDriveRepository
import com.example.data.repository.SewingTechRepository
import com.example.localization.AppLanguage
import com.example.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SewingTechRepository
    private val driveRepository = GoogleDriveRepository.getInstance(application)
    private val fiveSRepository = FiveSRepository.getInstance(application)

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = SewingTechRepository(database.appDao())
        viewModelScope.launch {
            repository.ensureInitialDataLoaded()
        }
    }

    // Language state - Sinhala is Primary as requested
    private val _currentLanguage = MutableStateFlow(AppLanguage.SINHALA)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Theme state
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    // Navigation Stack
    private val screenStack = mutableListOf<Screen>(Screen.Home)
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Language Dialog state
    private val _showLanguageDialog = MutableStateFlow(false)
    val showLanguageDialog: StateFlow<Boolean> = _showLanguageDialog.asStateFlow()

    // Data streams from Room
    val articles: StateFlow<List<TechnicalArticle>> = repository.allArticles.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val bookmarkedArticles: StateFlow<List<TechnicalArticle>> = repository.bookmarkedArticles.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val errorCodes: StateFlow<List<ErrorCodeItem>> = repository.allErrorCodes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val maintenanceTasks: StateFlow<List<MaintenanceTask>> = repository.allMaintenanceTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val technicianProfile: StateFlow<TechnicianProfile?> = repository.profile.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val driveFiles: StateFlow<List<DriveFileItem>> = driveRepository.driveFiles.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _isDriveFetching = MutableStateFlow(false)
    val isDriveFetching: StateFlow<Boolean> = _isDriveFetching.asStateFlow()

    private val _driveFetchMessage = MutableStateFlow<String?>(null)
    val driveFetchMessage: StateFlow<String?> = _driveFetchMessage.asStateFlow()

    // 5S Methodology State Flows
    val fiveSChecklist: StateFlow<List<FiveSChecklistItem>> = fiveSRepository.checklist.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val fiveSRedTags: StateFlow<List<RedTagItem>> = fiveSRepository.redTags.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val fiveSAuditRecords: StateFlow<List<FiveSAuditRecord>> = fiveSRepository.auditRecords.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    fun openLanguageDialog() {
        _showLanguageDialog.value = true
    }

    fun closeLanguageDialog() {
        _showLanguageDialog.value = false
    }

    fun navigateTo(screen: Screen) {
        screenStack.add(screen)
        _currentScreen.value = screen
    }

    fun navigateBack(): Boolean {
        if (screenStack.size > 1) {
            screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = screenStack.last()
            return true
        }
        return false
    }

    fun toggleBookmark(articleId: Long, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleBookmark(articleId, currentStatus)
        }
    }

    fun saveArticle(article: TechnicalArticle) {
        viewModelScope.launch {
            if (article.id == 0L) {
                repository.insertArticle(article)
            } else {
                repository.updateArticle(article)
            }
        }
    }

    fun deleteArticle(article: TechnicalArticle) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }

    fun saveErrorCode(item: ErrorCodeItem) {
        viewModelScope.launch {
            if (item.id == 0L) {
                repository.insertErrorCode(item)
            } else {
                repository.updateErrorCode(item)
            }
        }
    }

    fun deleteErrorCode(item: ErrorCodeItem) {
        viewModelScope.launch {
            repository.deleteErrorCode(item)
        }
    }

    fun toggleMaintenanceTask(id: Long, currentStatus: Boolean) {
        viewModelScope.launch {
            repository.updateTaskStatus(id, !currentStatus)
        }
    }

    fun resetMaintenanceChecklist() {
        viewModelScope.launch {
            repository.resetChecklist()
        }
    }

    fun updateProfile(profile: TechnicianProfile) {
        viewModelScope.launch {
            repository.updateProfile(profile)
        }
    }

    fun updateQuizScore(score: Int, level: String) {
        viewModelScope.launch {
            repository.updateQuizResult(score, level)
        }
    }

    fun restoreDefaults() {
        viewModelScope.launch {
            repository.restoreFactoryDefaults()
        }
    }

    // Google Drive File Operations
    fun fetchDriveFiles() {
        viewModelScope.launch {
            _isDriveFetching.value = true
            _driveFetchMessage.value = null
            val result = driveRepository.fetchFilesFromDrive()
            _isDriveFetching.value = false
            if (result.isSuccess) {
                _driveFetchMessage.value = "SUCCESS"
            } else {
                _driveFetchMessage.value = "ERROR"
            }
        }
    }

    fun clearDriveFetchMessage() {
        _driveFetchMessage.value = null
    }

    fun addUploadedDriveFile(
        name: String,
        driveLinkOrId: String,
        category: String,
        machineModel: String,
        description: String? = null
    ) {
        viewModelScope.launch {
            driveRepository.addUploadedFile(
                name = name,
                driveLinkOrId = driveLinkOrId,
                category = category,
                machineModel = machineModel,
                description = description
            )
        }
    }

    fun deleteDriveFile(fileId: String) {
        viewModelScope.launch {
            driveRepository.deleteFile(fileId)
        }
    }

    fun getDriveFolderId(): String = driveRepository.getFolderId()
    fun setDriveFolderId(id: String) = driveRepository.setFolderId(id)

    fun getDriveApiKey(): String = driveRepository.getApiKey()
    fun setDriveApiKey(key: String) = driveRepository.setApiKey(key)

    // 5S Process Operations
    fun toggleFiveSChecklistItem(id: String) {
        fiveSRepository.toggleChecklistItem(id)
    }

    fun resetFiveSChecklist() {
        fiveSRepository.resetChecklist()
    }

    fun addFiveSRedTag(
        itemName: String,
        machineModel: String,
        category: String,
        reason: String,
        action: String,
        location: String,
        taggedBy: String
    ) {
        fiveSRepository.addRedTag(
            itemName = itemName,
            machineModel = machineModel,
            category = category,
            reason = reason,
            action = action,
            location = location,
            taggedBy = taggedBy
        )
    }

    fun toggleFiveSRedTagResolved(id: String) {
        fiveSRepository.toggleRedTagResolved(id)
    }

    fun deleteFiveSRedTag(id: String) {
        fiveSRepository.deleteRedTag(id)
    }

    fun saveFiveSAudit(
        auditorName: String,
        machineLine: String,
        sortScore: Int,
        setInOrderScore: Int,
        shineScore: Int,
        standardizeScore: Int,
        sustainScore: Int,
        notes: String
    ) {
        fiveSRepository.saveAudit(
            auditorName = auditorName,
            machineLine = machineLine,
            sortScore = sortScore,
            setInOrderScore = setInOrderScore,
            shineScore = shineScore,
            standardizeScore = standardizeScore,
            sustainScore = sustainScore,
            notes = notes
        )
    }
}
