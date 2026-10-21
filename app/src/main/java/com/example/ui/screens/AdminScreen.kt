package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ErrorCodeItem
import com.example.data.model.TechnicalArticle
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.CategoryChip
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.MachineTypeBadge
import com.example.ui.theme.DangerRed
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun AdminScreen(
    articles: List<TechnicalArticle>,
    errorCodes: List<ErrorCodeItem>,
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit,
    onSaveArticle: (TechnicalArticle) -> Unit,
    onDeleteArticle: (TechnicalArticle) -> Unit,
    onSaveErrorCode: (ErrorCodeItem) -> Unit,
    onDeleteErrorCode: (ErrorCodeItem) -> Unit,
    onRestoreDefaults: () -> Unit,
    onNavigateToDriveFiles: () -> Unit = {}
) {
    BackHandler {
        onNavigateBack()
    }

    var selectedAdminTab by remember { mutableIntStateOf(0) } // 0: Articles, 1: Error Codes
    var showAddArticleDialog by remember { mutableStateOf(false) }
    var showAddErrorDialog by remember { mutableStateOf(false) }
    var articleToEdit by remember { mutableStateOf<TechnicalArticle?>(null) }
    var showRestoreConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("admin_screen")
    ) {
        // Admin Header Summary
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.ADMIN_TITLE, currentLanguage),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = LocaleStrings.get(LocaleStrings.ADMIN_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(color = ElectricBlueAccent)
                        )
                    }

                    IconButton(
                        onClick = { showRestoreConfirmDialog = true },
                        modifier = Modifier.testTag("admin_restore_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restore,
                            contentDescription = "Restore Defaults",
                            tint = WarningAmber
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.1f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.ADMIN_TOTAL_ARTICLES, currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                            )
                            Text(
                                text = "${articles.size}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.1f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.ADMIN_TOTAL_ERRORS, currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                            )
                            Text(
                                text = "${errorCodes.size}",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onNavigateToDriveFiles,
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("admin_drive_files_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.FolderShared,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, currentLanguage),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }

        // Admin Section Tabs
        TabRow(
            selectedTabIndex = selectedAdminTab,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Tab(
                selected = selectedAdminTab == 0,
                onClick = { selectedAdminTab = 0 },
                text = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.NAV_GUIDES, currentLanguage),
                        fontWeight = if (selectedAdminTab == 0) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            Tab(
                selected = selectedAdminTab == 1,
                onClick = { selectedAdminTab = 1 },
                text = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.NAV_ERROR_CODES, currentLanguage),
                        fontWeight = if (selectedAdminTab == 1) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }

        // Action Button Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (selectedAdminTab == 0) "Manage Technical Guides" else "Manage Electronic Error Codes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Button(
                onClick = {
                    if (selectedAdminTab == 0) {
                        articleToEdit = null
                        showAddArticleDialog = true
                    } else {
                        showAddErrorDialog = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("admin_add_new_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = LocaleStrings.get(LocaleStrings.BTN_ADD_NEW, currentLanguage),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // List
        if (selectedAdminTab == 0) {
            val validAdminArticles = articles.filter { 
                it.getTitle(currentLanguage).trim().isNotEmpty() &&
                (it.titleSinhala.trim().isNotEmpty() || it.titleEnglish.trim().isNotEmpty())
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(validAdminArticles) { article ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    MachineTypeBadge(machineType = article.machineType)
                                    DifficultyBadge(difficulty = article.difficulty)
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = article.getTitle(currentLanguage),
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                                Text(
                                    text = "Sinhala: ${article.titleSinhala.take(25)}... | En: ${article.titleEnglish.take(25)}...",
                                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        articleToEdit = article
                                        showAddArticleDialog = true
                                    },
                                    modifier = Modifier.testTag("edit_article_${article.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = ElectricBlueAccent
                                    )
                                }
                                IconButton(
                                    onClick = { onDeleteArticle(article) },
                                    modifier = Modifier.testTag("delete_article_${article.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = DangerRed
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(errorCodes) { item ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = DangerRed.copy(alpha = 0.15f)
                                    ) {
                                        Text(
                                            text = item.code,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = DangerRed,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = item.system,
                                        style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = item.getTitle(currentLanguage),
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            IconButton(onClick = { onDeleteErrorCode(item) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = DangerRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Tri-lingual Add/Edit Article Dialog
    if (showAddArticleDialog) {
        TrilingualArticleDialog(
            article = articleToEdit,
            currentLanguage = currentLanguage,
            onDismiss = { showAddArticleDialog = false },
            onSave = { updatedArticle ->
                onSaveArticle(updatedArticle)
                showAddArticleDialog = false
            }
        )
    }

    // Add Error Code Dialog
    if (showAddErrorDialog) {
        AddErrorCodeDialog(
            currentLanguage = currentLanguage,
            onDismiss = { showAddErrorDialog = false },
            onSave = { newError ->
                onSaveErrorCode(newError)
                showAddErrorDialog = false
            }
        )
    }

    // Restore Confirm Dialog
    if (showRestoreConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showRestoreConfirmDialog = false },
            title = { Text(LocaleStrings.get(LocaleStrings.ADMIN_RESTORE_DEFAULTS, currentLanguage)) },
            text = { Text("Reset technical library and error codes back to factory defaults?") },
            confirmButton = {
                Button(
                    onClick = {
                        onRestoreDefaults()
                        showRestoreConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                ) {
                    Text(LocaleStrings.get(LocaleStrings.BTN_CONFIRM, currentLanguage))
                }
            },
            dismissButton = {
                TextButton(onClick = { showRestoreConfirmDialog = false }) {
                    Text(LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
                }
            }
        )
    }
}

@Composable
fun TrilingualArticleDialog(
    article: TechnicalArticle?,
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (TechnicalArticle) -> Unit
) {
    var category by remember { mutableStateOf(article?.category ?: "TROUBLESHOOTING") }
    var machineType by remember { mutableStateOf(article?.machineType ?: "General") }
    var difficulty by remember { mutableStateOf(article?.difficulty ?: "Intermediate") }
    var keySpecs by remember { mutableStateOf(article?.keySpecsOrTiming ?: "") }

    // Sinhala fields
    var titleSi by remember { mutableStateOf(article?.titleSinhala ?: "") }
    var summarySi by remember { mutableStateOf(article?.summarySinhala ?: "") }
    var contentSi by remember { mutableStateOf(article?.contentSinhala ?: "") }

    // English fields
    var titleEn by remember { mutableStateOf(article?.titleEnglish ?: "") }
    var summaryEn by remember { mutableStateOf(article?.summaryEnglish ?: "") }
    var contentEn by remember { mutableStateOf(article?.contentEnglish ?: "") }

    // Tamil fields
    var titleTa by remember { mutableStateOf(article?.titleTamil ?: "") }
    var summaryTa by remember { mutableStateOf(article?.summaryTamil ?: "") }
    var contentTa by remember { mutableStateOf(article?.contentTamil ?: "") }

    var formTab by remember { mutableIntStateOf(0) } // 0: Sinhala, 1: English, 2: Tamil, 3: Specs

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("trilingual_article_dialog"),
        title = {
            Text(
                text = if (article == null) LocaleStrings.get(LocaleStrings.ADMIN_ADD_ARTICLE, currentLanguage)
                else LocaleStrings.get(LocaleStrings.BTN_EDIT, currentLanguage),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Tab to switch language fields
                TabRow(selectedTabIndex = formTab) {
                    Tab(
                        selected = formTab == 0,
                        onClick = { formTab = 0 },
                        text = { Text("සිංහල") }
                    )
                    Tab(
                        selected = formTab == 1,
                        onClick = { formTab = 1 },
                        text = { Text("English") }
                    )
                    Tab(
                        selected = formTab == 2,
                        onClick = { formTab = 2 },
                        text = { Text("தமிழ்") }
                    )
                    Tab(
                        selected = formTab == 3,
                        onClick = { formTab = 3 },
                        text = { Text("Specs") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (formTab) {
                    0 -> {
                        Text(
                            text = "🇱🇰 " + LocaleStrings.get(LocaleStrings.ADMIN_LANGUAGE_SECTION_SINHALA, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ElectricBlueAccent
                            )
                        )
                        OutlinedTextField(
                            value = titleSi,
                            onValueChange = { titleSi = it },
                            label = { Text("සිංහල මාතෘකාව (Sinhala Title)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_si")
                        )
                        OutlinedTextField(
                            value = summarySi,
                            onValueChange = { summarySi = it },
                            label = { Text("සිංහල සාරාංශය (Sinhala Summary)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_summary_si")
                        )
                        OutlinedTextField(
                            value = contentSi,
                            onValueChange = { contentSi = it },
                            label = { Text("විසඳුම් පියවර (Sinhala Steps)") },
                            minLines = 4,
                            modifier = Modifier.fillMaxWidth().testTag("input_content_si")
                        )
                    }
                    1 -> {
                        Text(
                            text = "🇬🇧 " + LocaleStrings.get(LocaleStrings.ADMIN_LANGUAGE_SECTION_ENGLISH, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MachineGreen
                            )
                        )
                        OutlinedTextField(
                            value = titleEn,
                            onValueChange = { titleEn = it },
                            label = { Text("English Title") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_en")
                        )
                        OutlinedTextField(
                            value = summaryEn,
                            onValueChange = { summaryEn = it },
                            label = { Text("English Summary") },
                            modifier = Modifier.fillMaxWidth().testTag("input_summary_en")
                        )
                        OutlinedTextField(
                            value = contentEn,
                            onValueChange = { contentEn = it },
                            label = { Text("Detailed Steps (English)") },
                            minLines = 4,
                            modifier = Modifier.fillMaxWidth().testTag("input_content_en")
                        )
                    }
                    2 -> {
                        Text(
                            text = "🇮🇳 " + LocaleStrings.get(LocaleStrings.ADMIN_LANGUAGE_SECTION_TAMIL, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber
                            )
                        )
                        OutlinedTextField(
                            value = titleTa,
                            onValueChange = { titleTa = it },
                            label = { Text("தமிழ் தலைப்பு (Tamil Title)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_title_ta")
                        )
                        OutlinedTextField(
                            value = summaryTa,
                            onValueChange = { summaryTa = it },
                            label = { Text("தமிழ் சுருக்கம் (Tamil Summary)") },
                            modifier = Modifier.fillMaxWidth().testTag("input_summary_ta")
                        )
                        OutlinedTextField(
                            value = contentTa,
                            onValueChange = { contentTa = it },
                            label = { Text("படிமுறைகள் (Tamil Steps)") },
                            minLines = 4,
                            modifier = Modifier.fillMaxWidth().testTag("input_content_ta")
                        )
                    }
                    3 -> {
                        Text(
                            text = "⚙️ Technical Specifications & Classification",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Category (TROUBLESHOOTING, TIMING, MAINTENANCE)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = machineType,
                            onValueChange = { machineType = it },
                            label = { Text("Machine (Lockstitch, Overlock, Flatlock, etc.)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = difficulty,
                            onValueChange = { difficulty = it },
                            label = { Text("Difficulty (Basic, Intermediate, Advanced)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = keySpecs,
                            onValueChange = { keySpecs = it },
                            label = { Text("Timing Clearance / Specs (e.g. 0.05mm - 0.1mm)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val resolvedSi = titleSi.trim()
                    val resolvedEn = titleEn.trim()
                    val resolvedTa = titleTa.trim()
                    if (resolvedSi.isEmpty() && resolvedEn.isEmpty() && resolvedTa.isEmpty()) {
                        return@Button
                    }
                    val finalArticle = TechnicalArticle(
                        id = article?.id ?: 0,
                        category = category,
                        machineType = machineType,
                        difficulty = difficulty,
                        titleSinhala = resolvedSi.ifBlank { resolvedEn },
                        titleEnglish = resolvedEn.ifBlank { resolvedSi },
                        titleTamil = resolvedTa.ifBlank { resolvedEn.ifBlank { resolvedSi } },
                        summarySinhala = summarySi.ifBlank { summaryEn },
                        summaryEnglish = summaryEn.ifBlank { summarySi },
                        summaryTamil = summaryTa.ifBlank { summaryEn },
                        contentSinhala = contentSi.ifBlank { contentEn },
                        contentEnglish = contentEn.ifBlank { contentSi },
                        contentTamil = contentTa.ifBlank { contentEn },
                        keySpecsOrTiming = keySpecs,
                        isBookmarked = article?.isBookmarked ?: false
                    )
                    onSave(finalArticle)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                modifier = Modifier.testTag("save_article_button")
            ) {
                Text(LocaleStrings.get(LocaleStrings.BTN_SAVE, currentLanguage))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
            }
        }
    )
}

@Composable
fun AddErrorCodeDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (ErrorCodeItem) -> Unit
) {
    var code by remember { mutableStateOf("E-09") }
    var system by remember { mutableStateOf("Direct Drive Box") }
    var titleSi by remember { mutableStateOf("") }
    var titleEn by remember { mutableStateOf("") }
    var titleTa by remember { mutableStateOf("") }
    var causeSi by remember { mutableStateOf("") }
    var causeEn by remember { mutableStateOf("") }
    var causeTa by remember { mutableStateOf("") }
    var solSi by remember { mutableStateOf("") }
    var solEn by remember { mutableStateOf("") }
    var solTa by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(LocaleStrings.get(LocaleStrings.ADMIN_ADD_ERROR, currentLanguage)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it },
                    label = { Text("Code (e.g. E-09)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = system,
                    onValueChange = { system = it },
                    label = { Text("System (e.g. Juki / Brother)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = titleSi,
                    onValueChange = { titleSi = it },
                    label = { Text("Title (Sinhala)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = titleEn,
                    onValueChange = { titleEn = it },
                    label = { Text("Title (English)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = titleTa,
                    onValueChange = { titleTa = it },
                    label = { Text("Title (Tamil)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = causeSi,
                    onValueChange = { causeSi = it },
                    label = { Text("Cause (Sinhala)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = causeEn,
                    onValueChange = { causeEn = it },
                    label = { Text("Cause (English)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = solSi,
                    onValueChange = { solSi = it },
                    label = { Text("Solution (Sinhala)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = solEn,
                    onValueChange = { solEn = it },
                    label = { Text("Solution (English)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val item = ErrorCodeItem(
                        code = code,
                        system = system,
                        titleSinhala = titleSi.ifBlank { titleEn },
                        titleEnglish = titleEn.ifBlank { titleSi },
                        titleTamil = titleTa.ifBlank { titleEn },
                        causeSinhala = causeSi.ifBlank { causeEn },
                        causeEnglish = causeEn.ifBlank { causeSi },
                        causeTamil = causeTa.ifBlank { causeEn },
                        solutionSinhala = solSi.ifBlank { solEn },
                        solutionEnglish = solEn.ifBlank { solSi },
                        solutionTamil = solTa.ifBlank { solEn }
                    )
                    onSave(item)
                },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent)
            ) {
                Text(LocaleStrings.get(LocaleStrings.BTN_SAVE, currentLanguage))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
            }
        }
    )
}
