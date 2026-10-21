package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.IndustrialTopBar
import com.example.ui.components.LanguageSelectorDialog
import com.example.ui.navigation.Screen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.CalculatorsScreen
import com.example.ui.screens.DriveFilesScreen
import com.example.ui.screens.ErrorCodeScreen
import com.example.ui.screens.FiveSScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.KnowledgeListScreen
import com.example.ui.screens.MaintenanceScreen
import com.example.ui.screens.MeasuringUnitsScreen
import com.example.ui.screens.MembershipScreen
import com.example.ui.screens.QrScannerScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()

            MyApplicationTheme(darkTheme = isDarkTheme) {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: MainViewModel) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val showLanguageDialog by viewModel.showLanguageDialog.collectAsState()

    val articles by viewModel.articles.collectAsState()
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsState()
    val errorCodes by viewModel.errorCodes.collectAsState()
    val maintenanceTasks by viewModel.maintenanceTasks.collectAsState()
    val technicianProfile by viewModel.technicianProfile.collectAsState()

    val canGoBack = currentScreen !is Screen.Home

    BackHandler(enabled = canGoBack) {
        viewModel.navigateBack()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            IndustrialTopBar(
                title = when (currentScreen) {
                    is Screen.Home -> LocaleStrings.get(LocaleStrings.APP_NAME, currentLanguage)
                    is Screen.KnowledgeList -> LocaleStrings.get(LocaleStrings.NAV_GUIDES, currentLanguage)
                    is Screen.ArticleDetail -> LocaleStrings.get(LocaleStrings.NAV_GUIDES, currentLanguage)
                    is Screen.MeasuringUnits -> when (currentLanguage) {
                        AppLanguage.SINHALA -> "මිනුම් උපකරණ"
                        AppLanguage.TAMIL -> "அளவீட்டு கருவிகள்"
                        AppLanguage.ENGLISH -> "Measuring Tools"
                    }
                    is Screen.QrScanner -> LocaleStrings.get(LocaleStrings.QR_SCANNER_TITLE, currentLanguage)
                    is Screen.DriveFiles -> LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, currentLanguage)
                    is Screen.FiveS -> LocaleStrings.get(LocaleStrings.FIVES_TITLE, currentLanguage)
                    is Screen.ErrorCodes -> LocaleStrings.get(LocaleStrings.NAV_ERROR_CODES, currentLanguage)
                    is Screen.Maintenance -> LocaleStrings.get(LocaleStrings.NAV_MAINTENANCE, currentLanguage)
                    is Screen.Calculators -> LocaleStrings.get(LocaleStrings.NAV_CALCULATORS, currentLanguage)
                    is Screen.Membership -> LocaleStrings.get(LocaleStrings.NAV_MEMBERSHIP, currentLanguage)
                    is Screen.Admin -> LocaleStrings.get(LocaleStrings.NAV_ADMIN, currentLanguage)
                    is Screen.Settings -> LocaleStrings.get(LocaleStrings.NAV_SETTINGS, currentLanguage)
                },
                currentLanguage = currentLanguage,
                canNavigateBack = canGoBack,
                onNavigateBack = { viewModel.navigateBack() },
                onOpenLanguageSelector = { viewModel.openLanguageDialog() },
                onOpenSettings = { viewModel.navigateTo(Screen.Settings) },
                onOpenQrScanner = if (currentScreen !is Screen.QrScanner) {
                    { viewModel.navigateTo(Screen.QrScanner) }
                } else null
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = IndustrialNavyCard,
                contentColor = Color.White
            ) {
                NavigationBarItem(
                    selected = currentScreen is Screen.Home,
                    onClick = { viewModel.navigateTo(Screen.Home) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.NAV_HOME, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = if (currentScreen is Screen.Home) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlueAccent,
                        selectedTextColor = ElectricBlueAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = IndustrialNavy
                    ),
                    modifier = Modifier.testTag("nav_item_home")
                )

                NavigationBarItem(
                    selected = currentScreen is Screen.KnowledgeList || currentScreen is Screen.ArticleDetail,
                    onClick = { viewModel.navigateTo(Screen.KnowledgeList()) },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "Guides") },
                    label = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.NAV_GUIDES, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = if (currentScreen is Screen.KnowledgeList) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlueAccent,
                        selectedTextColor = ElectricBlueAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = IndustrialNavy
                    ),
                    modifier = Modifier.testTag("nav_item_guides")
                )

                NavigationBarItem(
                    selected = currentScreen is Screen.ErrorCodes,
                    onClick = { viewModel.navigateTo(Screen.ErrorCodes) },
                    icon = { Icon(Icons.Default.ErrorOutline, contentDescription = "Error Codes") },
                    label = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.NAV_ERROR_CODES, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = if (currentScreen is Screen.ErrorCodes) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlueAccent,
                        selectedTextColor = ElectricBlueAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = IndustrialNavy
                    ),
                    modifier = Modifier.testTag("nav_item_errors")
                )

                NavigationBarItem(
                    selected = currentScreen is Screen.Maintenance,
                    onClick = { viewModel.navigateTo(Screen.Maintenance) },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = "Maintenance") },
                    label = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.NAV_MAINTENANCE, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = if (currentScreen is Screen.Maintenance) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlueAccent,
                        selectedTextColor = ElectricBlueAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = IndustrialNavy
                    ),
                    modifier = Modifier.testTag("nav_item_maintenance")
                )

                NavigationBarItem(
                    selected = currentScreen is Screen.Settings,
                    onClick = { viewModel.navigateTo(Screen.Settings) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.NAV_SETTINGS, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = if (currentScreen is Screen.Settings) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ElectricBlueAccent,
                        selectedTextColor = ElectricBlueAccent,
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f),
                        indicatorColor = IndustrialNavy
                    ),
                    modifier = Modifier.testTag("nav_item_settings")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        currentLanguage = currentLanguage,
                        profile = technicianProfile,
                        recentArticles = articles,
                        bookmarkedCount = bookmarkedArticles.size,
                        completedPmCount = maintenanceTasks.count { it.isCompleted },
                        onLanguageSelected = { viewModel.setLanguage(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        onArticleSelected = { articleId ->
                            if (articleId == 7L) {
                                viewModel.navigateTo(Screen.MeasuringUnits)
                            } else {
                                viewModel.navigateTo(Screen.ArticleDetail(articleId))
                            }
                        }
                    )
                }
                is Screen.KnowledgeList -> {
                    KnowledgeListScreen(
                        articles = articles,
                        initialCategory = screen.categoryFilter,
                        initialMachine = screen.machineFilter,
                        initialSearchQuery = screen.searchQuery,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() },
                        onArticleSelected = { articleId ->
                            if (articleId == 7L) {
                                viewModel.navigateTo(Screen.MeasuringUnits)
                            } else {
                                viewModel.navigateTo(Screen.ArticleDetail(articleId))
                            }
                        }
                    )
                }
                is Screen.MeasuringUnits -> {
                    MeasuringUnitsScreen(
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is Screen.QrScanner -> {
                    QrScannerScreen(
                        currentLanguage = currentLanguage,
                        articles = articles,
                        onNavigateBack = { viewModel.navigateBack() },
                        onNavigate = { viewModel.navigateTo(it) },
                        onArticleSelected = { articleId ->
                            if (articleId == 7L) {
                                viewModel.navigateTo(Screen.MeasuringUnits)
                            } else {
                                viewModel.navigateTo(Screen.ArticleDetail(articleId))
                            }
                        }
                    )
                }
                is Screen.DriveFiles -> {
                    val driveFiles by viewModel.driveFiles.collectAsState()
                    val isFetching by viewModel.isDriveFetching.collectAsState()
                    val fetchMessage by viewModel.driveFetchMessage.collectAsState()
                    DriveFilesScreen(
                        currentLanguage = currentLanguage,
                        driveFiles = driveFiles,
                        isFetching = isFetching,
                        fetchMessage = fetchMessage,
                        onFetchFiles = { viewModel.fetchDriveFiles() },
                        onClearFetchMessage = { viewModel.clearDriveFetchMessage() },
                        onAddUploadedFile = { name, linkOrId, cat, model, desc ->
                            viewModel.addUploadedDriveFile(name, linkOrId, cat, model, desc)
                        },
                        onDeleteFile = { viewModel.deleteDriveFile(it) },
                        getFolderId = { viewModel.getDriveFolderId() },
                        setFolderId = { viewModel.setDriveFolderId(it) },
                        getApiKey = { viewModel.getDriveApiKey() },
                        setApiKey = { viewModel.setDriveApiKey(it) },
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is Screen.FiveS -> {
                    val fiveSChecklist by viewModel.fiveSChecklist.collectAsState()
                    val fiveSRedTags by viewModel.fiveSRedTags.collectAsState()
                    val fiveSAuditRecords by viewModel.fiveSAuditRecords.collectAsState()
                    FiveSScreen(
                        currentLanguage = currentLanguage,
                        checklist = fiveSChecklist,
                        redTags = fiveSRedTags,
                        auditRecords = fiveSAuditRecords,
                        onToggleChecklistItem = { viewModel.toggleFiveSChecklistItem(it) },
                        onResetChecklist = { viewModel.resetFiveSChecklist() },
                        onAddRedTag = { name, model, cat, reason, action, loc, by ->
                            viewModel.addFiveSRedTag(name, model, cat, reason, action, loc, by)
                        },
                        onToggleRedTagResolved = { viewModel.toggleFiveSRedTagResolved(it) },
                        onDeleteRedTag = { viewModel.deleteFiveSRedTag(it) },
                        onSaveAudit = { auditor, line, s1, s2, s3, s4, s5, notes ->
                            viewModel.saveFiveSAudit(auditor, line, s1, s2, s3, s4, s5, notes)
                        },
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is Screen.ArticleDetail -> {
                    val currentArticle = articles.firstOrNull { it.id == screen.articleId }
                    ArticleDetailScreen(
                        article = currentArticle,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() },
                        onToggleBookmark = { id, current ->
                            viewModel.toggleBookmark(id, current)
                        }
                    )
                }
                is Screen.ErrorCodes -> {
                    ErrorCodeScreen(
                        errorCodes = errorCodes,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is Screen.Maintenance -> {
                    MaintenanceScreen(
                        tasks = maintenanceTasks,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() },
                        onToggleTask = { id, current ->
                            viewModel.toggleMaintenanceTask(id, current)
                        },
                        onResetChecklist = { viewModel.resetMaintenanceChecklist() }
                    )
                }
                is Screen.Calculators -> {
                    CalculatorsScreen(
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is Screen.Membership -> {
                    MembershipScreen(
                        profile = technicianProfile,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() },
                        onUpdateQuizScore = { score, level ->
                            viewModel.updateQuizScore(score, level)
                        }
                    )
                }
                is Screen.Admin -> {
                    AdminScreen(
                        articles = articles,
                        errorCodes = errorCodes,
                        currentLanguage = currentLanguage,
                        onNavigateBack = { viewModel.navigateBack() },
                        onSaveArticle = { viewModel.saveArticle(it) },
                        onDeleteArticle = { viewModel.deleteArticle(it) },
                        onSaveErrorCode = { viewModel.saveErrorCode(it) },
                        onDeleteErrorCode = { viewModel.deleteErrorCode(it) },
                        onRestoreDefaults = { viewModel.restoreDefaults() },
                        onNavigateToDriveFiles = { viewModel.navigateTo(Screen.DriveFiles) }
                    )
                }
                is Screen.Settings -> {
                    SettingsScreen(
                        currentLanguage = currentLanguage,
                        profile = technicianProfile,
                        isDarkTheme = isDarkTheme,
                        onLanguageSelected = { viewModel.setLanguage(it) },
                        onToggleTheme = { viewModel.setDarkTheme(it) },
                        onUpdateProfile = { viewModel.updateProfile(it) },
                        onRestoreDatabase = { viewModel.restoreDefaults() },
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
            }
        }
    }

    if (showLanguageDialog) {
        LanguageSelectorDialog(
            currentLanguage = currentLanguage,
            onLanguageSelected = { viewModel.setLanguage(it) },
            onDismissRequest = { viewModel.closeLanguageDialog() }
        )
    }
}
