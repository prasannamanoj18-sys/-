package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DriveFileItem
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.CategoryChip
import com.example.ui.theme.DangerRed
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavyDark
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun DriveFilesScreen(
    currentLanguage: AppLanguage,
    driveFiles: List<DriveFileItem>,
    isFetching: Boolean,
    fetchMessage: String?,
    onFetchFiles: () -> Unit,
    onClearFetchMessage: () -> Unit,
    onAddUploadedFile: (name: String, driveLinkOrId: String, category: String, machineModel: String, description: String?) -> Unit,
    onDeleteFile: (String) -> Unit,
    getFolderId: () -> String,
    setFolderId: (String) -> Unit,
    getApiKey: () -> String,
    setApiKey: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    BackHandler { onNavigateBack() }

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var showAddFileDialog by remember { mutableStateOf(false) }
    var showConfigDialog by remember { mutableStateOf(false) }
    var fileToDelete by remember { mutableStateOf<DriveFileItem?>(null) }

    LaunchedEffect(fetchMessage) {
        if (fetchMessage != null) {
            val toastText = if (fetchMessage == "SUCCESS") {
                LocaleStrings.get(LocaleStrings.DRIVE_FETCH_SUCCESS, currentLanguage)
            } else {
                LocaleStrings.get(LocaleStrings.DRIVE_FETCH_ERROR, currentLanguage)
            }
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            onClearFetchMessage()
        }
    }

    val filteredFiles = remember(driveFiles, searchQuery, selectedCategoryFilter) {
        driveFiles.filter { file ->
            val matchesCategory = when (selectedCategoryFilter) {
                "ALL" -> true
                "PDF" -> file.isPdf()
                "VIDEO" -> file.isVideo()
                "IMAGE" -> file.isImage()
                else -> true
            }

            val query = searchQuery.trim().lowercase()
            val matchesSearch = query.isEmpty() ||
                    file.name.lowercase().contains(query) ||
                    file.machineModel.lowercase().contains(query) ||
                    (file.description?.lowercase()?.contains(query) == true)

            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("drive_files_screen")
    ) {
        // Top Header Info Card
        Card(
            shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FolderShared,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.DRIVE_FILES_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 11.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    IconButton(
                        onClick = { showConfigDialog = true },
                        modifier = Modifier.testTag("btn_drive_config")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Config",
                            tint = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons Row: Live Fetch & Add New Uploaded File
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onFetchFiles,
                        enabled = !isFetching,
                        colors = ButtonDefaults.buttonColors(containerColor = MachineGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(44.dp)
                            .testTag("btn_fetch_drive_files")
                    ) {
                        if (isFetching) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Fetching...",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.CloudSync,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.DRIVE_FETCH_BUTTON, currentLanguage),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    Button(
                        onClick = { showAddFileDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("btn_add_uploaded_file")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "නව File"
                                AppLanguage.TAMIL -> "புதிய கோப்பு"
                                AppLanguage.ENGLISH -> "Add File"
                            },
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Search Bar & Filter Chips
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.DRIVE_SEARCH_HINT, currentLanguage),
                        fontSize = 13.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = ElectricBlueAccent
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElectricBlueAccent,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("drive_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Category Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filterTabs = listOf(
                    "ALL" to LocaleStrings.get(LocaleStrings.DRIVE_TAB_ALL, currentLanguage),
                    "PDF" to LocaleStrings.get(LocaleStrings.DRIVE_TAB_PDF, currentLanguage),
                    "VIDEO" to LocaleStrings.get(LocaleStrings.DRIVE_TAB_VIDEO, currentLanguage),
                    "IMAGE" to LocaleStrings.get(LocaleStrings.DRIVE_TAB_IMAGE, currentLanguage)
                )

                filterTabs.forEach { (key, label) ->
                    CategoryChip(
                        label = "$label (${
                            when (key) {
                                "ALL" -> driveFiles.size
                                "PDF" -> driveFiles.count { it.isPdf() }
                                "VIDEO" -> driveFiles.count { it.isVideo() }
                                "IMAGE" -> driveFiles.count { it.isImage() }
                                else -> 0
                            }
                        })",
                        isSelected = selectedCategoryFilter == key,
                        onClick = { selectedCategoryFilter = key },
                        modifier = Modifier.testTag("drive_filter_chip_$key")
                    )
                }
            }
        }

        // Drive Files List View
        if (filteredFiles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        shape = CircleShape,
                        color = SteelGreyDark.copy(alpha = 0.2f),
                        modifier = Modifier.size(72.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                tint = SteelGreyMedium,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.DRIVE_NO_FILES, currentLanguage),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onFetchFiles,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = LocaleStrings.get(LocaleStrings.DRIVE_FETCH_BUTTON, currentLanguage))
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("drive_files_lazy_column"),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredFiles, key = { it.id }) { fileItem ->
                    DriveFileCard(
                        file = fileItem,
                        currentLanguage = currentLanguage,
                        onOpen = {
                            val url = fileItem.getEffectiveViewUrl()
                            if (url.isNotBlank()) {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not open link", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        onDownload = {
                            val url = fileItem.getEffectiveDownloadUrl()
                            if (url.isNotBlank()) {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not start download", Toast.LENGTH_SHORT).show()
                                }
                            }
                        },
                        onCopyLink = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Drive Link", fileItem.getEffectiveViewUrl())
                            clipboard.setPrimaryClip(clip)
                            Toast.makeText(
                                context,
                                LocaleStrings.get(LocaleStrings.MSG_LINK_COPIED, currentLanguage),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onShare = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, fileItem.name)
                                putExtra(Intent.EXTRA_TEXT, "${fileItem.name}\n${fileItem.getEffectiveViewUrl()}")
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Document"))
                        },
                        onDelete = { fileToDelete = fileItem }
                    )
                }
            }
        }
    }

    // Add Uploaded File Dialog
    if (showAddFileDialog) {
        AddUploadedFileDialog(
            currentLanguage = currentLanguage,
            onDismiss = { showAddFileDialog = false },
            onAdd = { name, linkOrId, category, machineModel, desc ->
                onAddUploadedFile(name, linkOrId, category, machineModel, desc)
                showAddFileDialog = false
                Toast.makeText(
                    context,
                    LocaleStrings.get(LocaleStrings.MSG_SUCCESS_SAVED, currentLanguage),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    // Google Drive Config Dialog
    if (showConfigDialog) {
        DriveFolderConfigDialog(
            currentLanguage = currentLanguage,
            initialFolderId = getFolderId(),
            initialApiKey = getApiKey(),
            onDismiss = { showConfigDialog = false },
            onSave = { folderId, apiKey ->
                setFolderId(folderId)
                setApiKey(apiKey)
                showConfigDialog = false
                onFetchFiles()
            }
        )
    }

    // Delete Confirmation Dialog
    fileToDelete?.let { file ->
        AlertDialog(
            onDismissRequest = { fileToDelete = null },
            title = {
                Text(
                    text = LocaleStrings.get(LocaleStrings.MSG_CONFIRM_DELETE, currentLanguage),
                    fontWeight = FontWeight.Bold
                )
            },
            text = { Text(file.name) },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteFile(file.id)
                        fileToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = DangerRed)
                ) {
                    Text(LocaleStrings.get(LocaleStrings.BTN_DELETE, currentLanguage))
                }
            },
            dismissButton = {
                TextButton(onClick = { fileToDelete = null }) {
                    Text(LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
                }
            }
        )
    }
}

/**
 * Material 3 Card displaying an individual Google Drive File.
 */
@Composable
fun DriveFileCard(
    file: DriveFileItem,
    currentLanguage: AppLanguage,
    onOpen: () -> Unit,
    onDownload: () -> Unit,
    onCopyLink: () -> Unit,
    onShare: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("drive_file_card_${file.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // File Type Badge Icon
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = when {
                        file.isPdf() -> DangerRed.copy(alpha = 0.15f)
                        file.isVideo() -> Color(0xFF8B5CF6).copy(alpha = 0.15f)
                        file.isImage() -> MachineGreen.copy(alpha = 0.15f)
                        else -> ElectricBlueAccent.copy(alpha = 0.15f)
                    },
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = when {
                                file.isPdf() -> Icons.Default.PictureAsPdf
                                file.isVideo() -> Icons.Default.PlayCircle
                                file.isImage() -> Icons.Default.Image
                                else -> Icons.Default.Description
                            },
                            contentDescription = null,
                            tint = when {
                                file.isPdf() -> DangerRed
                                file.isVideo() -> Color(0xFF8B5CF6)
                                file.isImage() -> MachineGreen
                                else -> ElectricBlueAccent
                            },
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = file.name,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 18.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Machine Model & Size Badges
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = ElectricBlueAccent.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = file.machineModel,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ElectricBlueAccent,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = SteelGreyDark.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = file.getFormattedSize(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = file.modifiedTime.take(10),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = DangerRed.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (!file.description.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = file.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            Spacer(modifier = Modifier.height(8.dp))

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onOpen,
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.DRIVE_OPEN_FILE, currentLanguage),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    OutlinedButton(
                        onClick = onDownload,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.DRIVE_DOWNLOAD_FILE, currentLanguage),
                            fontSize = 11.sp
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = onCopyLink,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy Link",
                            tint = SteelGreyMedium,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = onShare,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = SteelGreyMedium,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Dialog to add / link an uploaded Google Drive document.
 */
@Composable
fun AddUploadedFileDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onAdd: (name: String, driveLinkOrId: String, category: String, machineModel: String, desc: String?) -> Unit
) {
    var fileName by remember { mutableStateOf("") }
    var driveLink by remember { mutableStateOf("") }
    var machineModel by remember { mutableStateOf("Juki DDL-8700") }
    var selectedCategory by remember { mutableStateOf("MANUAL") }
    var description by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CloudDownload,
                    contentDescription = null,
                    tint = ElectricBlueAccent
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = LocaleStrings.get(LocaleStrings.DRIVE_ADD_FILE, currentLanguage),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = fileName,
                    onValueChange = {
                        fileName = it
                        errorText = null
                    },
                    label = { Text(LocaleStrings.get(LocaleStrings.DRIVE_FILE_NAME, currentLanguage)) },
                    placeholder = { Text("උදා: Juki DDL-8700 Technical Manual.pdf") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = driveLink,
                    onValueChange = {
                        driveLink = it
                        errorText = null
                    },
                    label = { Text(LocaleStrings.get(LocaleStrings.DRIVE_FILE_LINK, currentLanguage)) },
                    placeholder = { Text("https://drive.google.com/file/d/...") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = machineModel,
                    onValueChange = { machineModel = it },
                    label = { Text("මැෂින් වර්ගය (Machine Model)") },
                    placeholder = { Text("උදා: Juki, Pegasus, Yamato, Brother") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Category Selection
                Text(
                    text = "ගොනු වර්ගය (Category):",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val categories = listOf("MANUAL" to "PDF Manual", "VIDEO" to "Video", "IMAGE" to "Image / Diagram", "DOCUMENT" to "Document")
                    categories.forEach { (catKey, catLabel) ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (selectedCategory == catKey) ElectricBlueAccent else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedCategory = catKey }
                        ) {
                            Text(
                                text = catLabel,
                                color = if (selectedCategory == catKey) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("සටහන / විස්තරය (Description)") },
                    placeholder = { Text("අත්පොත හෝ වීඩියෝව පිළිබඳ කෙටි විස්තරයක්...") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorText != null) {
                    Text(
                        text = errorText ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (fileName.isBlank() || driveLink.isBlank()) {
                        errorText = "කරුණාකර ගොනුවේ නම සහ Google Drive link එක ඇතුළත් කරන්න."
                    } else {
                        onAdd(fileName, driveLink, selectedCategory, machineModel, description.ifBlank { null })
                    }
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

/**
 * Dialog to configure Google Drive Folder ID & optional API Key.
 */
@Composable
fun DriveFolderConfigDialog(
    currentLanguage: AppLanguage,
    initialFolderId: String,
    initialApiKey: String,
    onDismiss: () -> Unit,
    onSave: (folderId: String, apiKey: String) -> Unit
) {
    var folderId by remember { mutableStateOf(initialFolderId) }
    var apiKey by remember { mutableStateOf(initialApiKey) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = ElectricBlueAccent
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = LocaleStrings.get(LocaleStrings.DRIVE_FOLDER_ID_CONFIG, currentLanguage),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "ඔබගේ Google Drive Shared Folder එකේ ID එක මෙහි ඇතුළත් කරන්න (https://drive.google.com/drive/folders/...):",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )

                OutlinedTextField(
                    value = folderId,
                    onValueChange = { folderId = it },
                    label = { Text("Google Drive Folder ID") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = apiKey,
                    onValueChange = { apiKey = it },
                    label = { Text("Google API Key (විකල්ප / Optional)") },
                    placeholder = { Text("AI Studio Secrets / Cloud Console key") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(folderId, apiKey) },
                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent)
            ) {
                Text(LocaleStrings.get(LocaleStrings.BTN_CONFIRM, currentLanguage))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
            }
        }
    )
}
