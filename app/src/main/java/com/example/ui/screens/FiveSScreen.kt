package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FiveSAuditRecord
import com.example.data.model.FiveSChecklistItem
import com.example.data.model.FiveSPillar
import com.example.data.model.RedTagItem
import com.example.data.repository.FiveSRepository
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavySurface
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyLight
import com.example.ui.theme.WarningAmber

@Composable
fun FiveSScreen(
    currentLanguage: AppLanguage,
    checklist: List<FiveSChecklistItem>,
    redTags: List<RedTagItem>,
    auditRecords: List<FiveSAuditRecord>,
    onToggleChecklistItem: (String) -> Unit,
    onResetChecklist: () -> Unit,
    onAddRedTag: (itemName: String, machineModel: String, category: String, reason: String, action: String, location: String, taggedBy: String) -> Unit,
    onToggleRedTagResolved: (String) -> Unit,
    onDeleteRedTag: (String) -> Unit,
    onSaveAudit: (auditorName: String, machineLine: String, sort: Int, setInOrder: Int, shine: Int, standardize: Int, sustain: Int, notes: String) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddRedTagDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialNavy)
            .testTag("five_s_screen")
    ) {
        // Header Card with Title and Navigation
        Surface(
            color = IndustrialNavyCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("fives_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.FIVES_TITLE, currentLanguage),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 20.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = MachineGreen.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen)
                            ) {
                                Text(
                                    text = "LEAN KAIZEN",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MachineGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = LocaleStrings.get(LocaleStrings.FIVES_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SteelGreyLight,
                                fontSize = 12.sp
                            ),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable Tabs
                val tabTitles = listOf(
                    LocaleStrings.get(LocaleStrings.FIVES_TAB_PILLARS, currentLanguage),
                    LocaleStrings.get(LocaleStrings.FIVES_TAB_CHECKLIST, currentLanguage),
                    LocaleStrings.get(LocaleStrings.FIVES_TAB_REDTAG, currentLanguage),
                    LocaleStrings.get(LocaleStrings.FIVES_TAB_AUDIT, currentLanguage)
                )

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = IndustrialNavySurface,
                    contentColor = ElectricBlueAccent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = ElectricBlueAccent,
                            height = 3.dp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTab == index) Color.White else SteelGreyLight,
                                        fontSize = 12.sp
                                    ),
                                    maxLines = 1
                                )
                            },
                            modifier = Modifier.testTag("fives_tab_$index")
                        )
                    }
                }
            }
        }

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedTab) {
                0 -> FiveSPillarsView(
                    currentLanguage = currentLanguage,
                    pillars = FiveSRepository.FIVE_S_PILLARS
                )
                1 -> FiveSChecklistView(
                    currentLanguage = currentLanguage,
                    checklist = checklist,
                    onToggle = onToggleChecklistItem,
                    onResetRequest = { showResetConfirmDialog = true }
                )
                2 -> FiveSRedTagView(
                    currentLanguage = currentLanguage,
                    redTags = redTags,
                    onAddRequest = { showAddRedTagDialog = true },
                    onToggleResolved = onToggleRedTagResolved,
                    onDelete = onDeleteRedTag
                )
                3 -> FiveSAuditView(
                    currentLanguage = currentLanguage,
                    auditRecords = auditRecords,
                    onSaveAudit = onSaveAudit
                )
            }
        }
    }

    // Reset Checklist Confirmation Dialog
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = {
                Text(
                    text = LocaleStrings.get(LocaleStrings.FIVES_RESET_CHECKLIST, currentLanguage),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            text = {
                Text(
                    text = if (currentLanguage == AppLanguage.SINHALA)
                        "ඔබට දෛනික 5S පිරික්සුම් ලැයිස්තුව නැවත මුල සිට පටන් ගැනීමට අවශ්‍යද?"
                    else
                        "Do you want to reset all 5S daily checklist items to unchecked?",
                    color = SteelGreyLight
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetChecklist()
                        showResetConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = WarningAmber)
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.SINHALA) "ඔව්, Reset කරන්න" else "Yes, Reset",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showResetConfirmDialog = false }) {
                    Text(
                        text = if (currentLanguage == AppLanguage.SINHALA) "අවලංගුයි" else "Cancel",
                        color = Color.White
                    )
                }
            },
            containerColor = IndustrialNavyCard
        )
    }

    // Add Red Tag Dialog
    if (showAddRedTagDialog) {
        AddRedTagDialog(
            currentLanguage = currentLanguage,
            onDismiss = { showAddRedTagDialog = false },
            onSave = { name, model, cat, reason, action, loc, by ->
                onAddRedTag(name, model, cat, reason, action, loc, by)
                showAddRedTagDialog = false
            }
        )
    }
}

/**
 * Tab 0: 5S Pillars Guide
 */
@Composable
fun FiveSPillarsView(
    currentLanguage: AppLanguage,
    pillars: List<FiveSPillar>
) {
    var expandedPillar by remember { mutableIntStateOf(1) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Intro Banner
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = ElectricBlueAccent.copy(alpha = 0.2f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = if (currentLanguage == AppLanguage.SINHALA)
                                "5S යනු කුමක්ද?"
                            else "What is 5S in Apparel Tech?",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (currentLanguage == AppLanguage.SINHALA)
                                "ජපන් කයිසෙන් (Kaizen) සංකල්පය මත පදනම් වූ, ඇඟලුම් කම්හල් හා මැහුම් යන්ත්‍ර වැඩබිම විනයගතව, පිරිසිදුව හා කාර්යක්ෂමව පවත්වා ගැනීමේ ප්‍රධාන කුළුණු 5කි."
                            else
                                "A world-class Japanese lean methodology to maintain sewing machine workstations clean, safe, and highly efficient.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SteelGreyLight,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }

        // 5 Pillars Cards
        items(pillars) { pillar ->
            val isExpanded = expandedPillar == pillar.number
            val accentColor = Color(pillar.accentColorHex)

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isExpanded) 1.5.dp else 1.dp,
                    color = if (isExpanded) accentColor else SteelGreyDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandedPillar = if (isExpanded) 0 else pillar.number
                    }
                    .testTag("fives_pillar_${pillar.number}")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1S, 2S, 3S, 4S, 5S Badge
                        Surface(
                            shape = CircleShape,
                            color = accentColor.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor),
                            modifier = Modifier.size(46.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${pillar.number}S",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = accentColor,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.SINHALA) pillar.sinhalaName else pillar.englishName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = pillar.japaneseKanji,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = accentColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA) pillar.subtitleSinhala else pillar.subtitleEnglish,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SteelGreyLight,
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = if (isExpanded) "Collapse" else "Expand",
                            tint = SteelGreyLight
                        )
                    }

                    // Expanded Section
                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 14.dp)) {
                            HorizontalDivider(color = SteelGreyDark, thickness = 1.dp)

                            Spacer(modifier = Modifier.height(12.dp))

                            // Sewing Machine Focus
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = IndustrialNavySurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, accentColor.copy(alpha = 0.4f)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Handyman,
                                            contentDescription = null,
                                            tint = accentColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (currentLanguage == AppLanguage.SINHALA)
                                                "මැහුම් යන්ත්‍ර කාර්මික අවධානය:"
                                            else "Sewing Machine Mechanic Focus:",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = accentColor
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = if (currentLanguage == AppLanguage.SINHALA)
                                            pillar.sewingMachineFocusSinhala
                                        else pillar.sewingMachineFocusEnglish,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color.White,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Key points
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA) "ප්‍රධාන ක්‍රියාමාර්ග:" else "Key Action Steps:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val keyPoints = if (currentLanguage == AppLanguage.SINHALA) pillar.keyPointsSinhala else pillar.keyPointsEnglish
                            keyPoints.forEach { point ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("• ", color = accentColor, fontWeight = FontWeight.Bold)
                                    Text(
                                        text = point,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SteelGreyLight,
                                            lineHeight = 18.sp
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Do's & Don'ts
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Do's
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MachineGreen.copy(alpha = 0.12f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen.copy(alpha = 0.3f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = "✅ කළ යුතු දේ (Do's)",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = MachineGreen,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        pillar.dosSinhala.forEach { d ->
                                            Text(
                                                text = "- $d",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Color.White,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                    }
                                }

                                // Don'ts
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFEF4444).copy(alpha = 0.12f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.3f)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(
                                            text = "❌ නොකළ යුතු දේ",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Color(0xFFEF4444),
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        pillar.dontsSinhala.forEach { d ->
                                            Text(
                                                text = "- $d",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Color.White,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 1: Daily 5S Checklist
 */
@Composable
fun FiveSChecklistView(
    currentLanguage: AppLanguage,
    checklist: List<FiveSChecklistItem>,
    onToggle: (String) -> Unit,
    onResetRequest: () -> Unit
) {
    var selectedPillarFilter by remember { mutableIntStateOf(0) } // 0 = All

    val completedCount = checklist.count { it.isCompleted }
    val totalCount = checklist.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount.toFloat() else 0f
    val animatedProgress by animateFloatAsState(targetValue = progress, label = "progress")

    val filteredList = if (selectedPillarFilter == 0) {
        checklist
    } else {
        checklist.filter { it.pillarNumber == selectedPillarFilter }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Progress Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.FIVES_PROGRESS, currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "$completedCount / $totalCount ${LocaleStrings.get(LocaleStrings.FIVES_COMPLETED, currentLanguage)} (${(progress * 100).toInt()}%)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (progress == 1f) MachineGreen else ElectricBlueAccent,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        IconButton(
                            onClick = onResetRequest,
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("btn_reset_5s_checklist")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset",
                                tint = WarningAmber
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        color = if (progress == 1f) MachineGreen else ElectricBlueAccent,
                        trackColor = IndustrialNavySurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                    )
                }
            }
        }

        // Filter Pills: All, 1S, 2S, 3S, 4S, 5S
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val filters = listOf(
                    0 to "සියල්ල (All)",
                    1 to "1S Sort",
                    2 to "2S Set",
                    3 to "3S Shine",
                    4 to "4S Standard",
                    5 to "5S Sustain"
                )
                filters.forEach { (num, label) ->
                    val isSelected = selectedPillarFilter == num
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) ElectricBlueAccent else IndustrialNavyCard,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) ElectricBlueAccent else SteelGreyDark),
                        modifier = Modifier
                            .clickable { selectedPillarFilter = num }
                            .testTag("checklist_filter_$num")
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else SteelGreyLight
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Checklist Items
        items(filteredList, key = { it.id }) { item ->
            val pillarColor = when (item.pillarNumber) {
                1 -> Color(0xFFEF4444)
                2 -> Color(0xFF3B82F6)
                3 -> Color(0xFF10B981)
                4 -> Color(0xFFF59E0B)
                else -> Color(0xFF8B5CF6)
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (item.isCompleted) IndustrialNavySurface.copy(alpha = 0.7f) else IndustrialNavyCard
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (item.isCompleted) MachineGreen.copy(alpha = 0.5f) else SteelGreyDark
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle(item.id) }
                    .testTag("checklist_item_${item.id}")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Checkbox
                    IconButton(
                        onClick = { onToggle(item.id) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (item.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = if (item.isCompleted) "Completed" else "Not completed",
                            tint = if (item.isCompleted) MachineGreen else SteelGreyLight,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = pillarColor.copy(alpha = 0.15f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, pillarColor)
                            ) {
                                Text(
                                    text = "${item.pillarNumber}S",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = pillarColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA) item.titleSinhala else item.titleEnglish,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (item.isCompleted) SteelGreyLight else Color.White,
                                    textDecoration = if (item.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (currentLanguage == AppLanguage.SINHALA) item.descriptionSinhala else item.descriptionEnglish,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SteelGreyLight.copy(alpha = 0.8f),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tab 2: Red Tag Log (රතු ටැග් ක්‍රමය)
 */
@Composable
fun FiveSRedTagView(
    currentLanguage: AppLanguage,
    redTags: List<RedTagItem>,
    onAddRequest: () -> Unit,
    onToggleResolved: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    var filterStatus by remember { mutableStateOf("ALL") } // ALL, PENDING, RESOLVED

    val filteredList = when (filterStatus) {
        "PENDING" -> redTags.filter { !it.isResolved }
        "RESOLVED" -> redTags.filter { it.isResolved }
        else -> redTags
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Red Tag Explainer & Add Button Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFEF4444).copy(alpha = 0.2f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Label,
                                    contentDescription = null,
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA)
                                    "රතු ටැග් ක්‍රමය (Red Tag Strategy)"
                                else "5S Red Tag Register",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA)
                                    "වැඩබිමේ ඇති දෝෂ සහිත හෝ අනවශ්‍ය කොටස් වෙන් කිරීමේ සටහන"
                                else "Identify & isolate defective, excess, or obsolete machine parts",
                                style = MaterialTheme.typography.bodySmall.copy(color = SteelGreyLight)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onAddRequest,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_add_red_tag")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.FIVES_ADD_REDTAG, currentLanguage),
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Filter Pills
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to "සියල්ල (${redTags.size})",
                    "PENDING" to "විසඳීමට ඇති (${redTags.count { !it.isResolved }})",
                    "RESOLVED" to "විසඳන ලද (${redTags.count { it.isResolved }})"
                ).forEach { (status, label) ->
                    val isSelected = filterStatus == status
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) ElectricBlueAccent else IndustrialNavyCard,
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) ElectricBlueAccent else SteelGreyDark),
                        modifier = Modifier
                            .weight(1f)
                            .clickable { filterStatus = status }
                            .testTag("redtag_filter_$status")
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else SteelGreyLight,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Red Tag Items List
        if (filteredList.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.SINHALA) "රතු ටැග් සටහන් කිසිවක් නැත" else "No Red Tag items found",
                        color = SteelGreyLight
                    )
                }
            }
        } else {
            items(filteredList, key = { it.id }) { tag ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                    border = androidx.compose.foundation.BorderStroke(
                        width = 1.dp,
                        color = if (tag.isResolved) MachineGreen.copy(alpha = 0.5f) else Color(0xFFEF4444).copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("redtag_card_${tag.tagNumber}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFEF4444),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text(
                                        text = tag.tagNumber,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }

                                Text(
                                    text = tag.machineModel,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = SteelGreyLight,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            // Resolved status pill
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (tag.isResolved) MachineGreen.copy(alpha = 0.2f) else WarningAmber.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (tag.isResolved) MachineGreen else WarningAmber
                                )
                            ) {
                                Text(
                                    text = if (tag.isResolved) "RESOLVED" else "PENDING",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (tag.isResolved) MachineGreen else WarningAmber,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = tag.itemName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        // Details Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "හේතුව: ${tag.reason}",
                                style = MaterialTheme.typography.bodySmall.copy(color = SteelGreyLight)
                            )
                            Text("•", color = SteelGreyDark)
                            Text(
                                text = "ක්‍රියාමාර්ගය: ${tag.action}",
                                style = MaterialTheme.typography.bodySmall.copy(color = WarningAmber)
                            )
                        }

                        if (tag.location.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "ස්ථානය: ${tag.location}  |  දින: ${tag.dateTagged}",
                                style = MaterialTheme.typography.bodySmall.copy(color = SteelGreyLight.copy(alpha = 0.7f), fontSize = 11.sp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = SteelGreyDark, thickness = 1.dp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                onClick = { onToggleResolved(tag.id) },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Icon(
                                    imageVector = if (tag.isResolved) Icons.Default.Close else Icons.Default.Check,
                                    contentDescription = null,
                                    tint = if (tag.isResolved) WarningAmber else MachineGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (tag.isResolved) "Re-open" else "Mark Resolved",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (tag.isResolved) WarningAmber else MachineGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = { onDelete(tag.id) },
                                modifier = Modifier.size(34.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFEF4444).copy(alpha = 0.8f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tab 3: 5S Audit Scorecard & Calculator
 */
@Composable
fun FiveSAuditView(
    currentLanguage: AppLanguage,
    auditRecords: List<FiveSAuditRecord>,
    onSaveAudit: (auditor: String, line: String, s1: Int, s2: Int, s3: Int, s4: Int, s5: Int, notes: String) -> Unit
) {
    var s1Score by remember { mutableIntStateOf(4) }
    var s2Score by remember { mutableIntStateOf(4) }
    var s3Score by remember { mutableIntStateOf(4) }
    var s4Score by remember { mutableIntStateOf(4) }
    var s5Score by remember { mutableIntStateOf(3) }
    var auditorName by remember { mutableStateOf("") }
    var machineLine by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var savedSuccessMessage by remember { mutableStateOf(false) }

    val totalScore = s1Score + s2Score + s3Score + s4Score + s5Score
    val percentage = ((totalScore.toFloat() / 25f) * 100).toInt()

    val gradeText = when {
        percentage >= 90 -> LocaleStrings.get(LocaleStrings.FIVES_SCORE_EXCELLENT, currentLanguage)
        percentage >= 75 -> LocaleStrings.get(LocaleStrings.FIVES_SCORE_GOOD, currentLanguage)
        else -> LocaleStrings.get(LocaleStrings.FIVES_SCORE_IMPROVE, currentLanguage)
    }

    val gradeColor = when {
        percentage >= 90 -> MachineGreen
        percentage >= 75 -> ElectricBlueAccent
        else -> WarningAmber
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Scorecard Header & Results Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, gradeColor)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA)
                                    "5S විගණන ලකුණු පුවරුව (Scorecard)"
                                else "5S Line Audit Scorecard",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = gradeText,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = gradeColor,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        // Big Score Circle
                        Surface(
                            shape = CircleShape,
                            color = gradeColor.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(2.dp, gradeColor),
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "$percentage%",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = gradeColor,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "සම්පූර්ණ ලකුණු: $totalScore / 25",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = SteelGreyLight,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // 5 S Sliders/Stars
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "කුළුණු 5 අනුව ලකුණු ලබා දෙන්න (1-5):",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    ScoreRow(label = "1S - Seiri (සෝදිසිය / Sort)", score = s1Score, onScoreChange = { s1Score = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    ScoreRow(label = "2S - Seiton (පිළිවෙල / Set In Order)", score = s2Score, onScoreChange = { s2Score = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    ScoreRow(label = "3S - Seiso (පිරිසිදුකම / Shine)", score = s3Score, onScoreChange = { s3Score = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    ScoreRow(label = "4S - Seiketsu (ප්‍රමිතිකරණය / Standardize)", score = s4Score, onScoreChange = { s4Score = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    ScoreRow(label = "5S - Shitsuke (විනය / Sustain)", score = s5Score, onScoreChange = { s5Score = it })
                }
            }
        }

        // Auditor Info Form & Save
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "විගණන තොරතුරු සටහන් කිරීම:",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = auditorName,
                        onValueChange = { auditorName = it },
                        label = { Text("විගණකගේ නම (Auditor Name)", color = SteelGreyLight) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ElectricBlueAccent,
                            unfocusedBorderColor = SteelGreyDark
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = machineLine,
                        onValueChange = { machineLine = it },
                        label = { Text("යන්ත්‍ර පේළිය / අංශය (Machine Line)", color = SteelGreyLight) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ElectricBlueAccent,
                            unfocusedBorderColor = SteelGreyDark
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("විශේෂ නිරීක්ෂණ සහ වැඩිදියුණු කළ යුතු කරුණු (Notes)", color = SteelGreyLight) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = ElectricBlueAccent,
                            unfocusedBorderColor = SteelGreyDark
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            onSaveAudit(auditorName, machineLine, s1Score, s2Score, s3Score, s4Score, s5Score, notes)
                            savedSuccessMessage = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_save_5s_audit")
                    ) {
                        Icon(imageVector = Icons.Default.Assessment, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "විගණන වාර්තාව සුරකින්න (Save Audit)",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    if (savedSuccessMessage) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "✅ විගණන ලකුණු සාර්ථකව සුරකින ලදී!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MachineGreen,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // Previous Audits History
        if (auditRecords.isNotEmpty()) {
            item {
                Text(
                    text = "පෙර විගණන වාර්තා (${auditRecords.size}):",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            items(auditRecords, key = { it.id }) { record ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = record.machineLine,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "විගණක: ${record.auditorName}  |  ${record.date}",
                                    style = MaterialTheme.typography.bodySmall.copy(color = SteelGreyLight)
                                )
                            }

                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = ElectricBlueAccent.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent)
                            ) {
                                Text(
                                    text = "${record.percentage}% (${record.totalScore}/25)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ElectricBlueAccent,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (record.notes.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = record.notes,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SteelGreyLight.copy(alpha = 0.8f),
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreRow(
    label: String,
    score: Int,
    onScoreChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.weight(1f)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            (1..5).forEach { star ->
                Icon(
                    imageVector = if (star <= score) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "$star stars",
                    tint = if (star <= score) WarningAmber else SteelGreyLight,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onScoreChange(star) }
                )
            }
        }
    }
}

/**
 * Add Red Tag Dialog
 */
@Composable
fun AddRedTagDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onSave: (itemName: String, machineModel: String, category: String, reason: String, action: String, location: String, taggedBy: String) -> Unit
) {
    var itemName by remember { mutableStateOf("") }
    var machineModel by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("PARTS") }
    var reason by remember { mutableStateOf("DAMAGED") }
    var action by remember { mutableStateOf("SCRAP") }
    var location by remember { mutableStateOf("") }
    var taggedBy by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "නව රතු ටැගයක් එක් කරන්න (New Red Tag)",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("අයිතමයේ නම (Item Name)*", color = SteelGreyLight) },
                    placeholder = { Text("උදා: Worn Rotary Hook", color = SteelGreyDark) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = ElectricBlueAccent,
                        unfocusedBorderColor = SteelGreyDark
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = machineModel,
                    onValueChange = { machineModel = it },
                    label = { Text("මැෂින් වර්ගය (Machine Model)*", color = SteelGreyLight) },
                    placeholder = { Text("උදා: Juki DDL-8700", color = SteelGreyDark) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = ElectricBlueAccent,
                        unfocusedBorderColor = SteelGreyDark
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("ස්ථානය (Location)", color = SteelGreyLight) },
                    placeholder = { Text("උදා: Line 03 - Machine 14", color = SteelGreyDark) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = ElectricBlueAccent,
                        unfocusedBorderColor = SteelGreyDark
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = taggedBy,
                    onValueChange = { taggedBy = it },
                    label = { Text("ටැග් කළ පුද්ගලයා (Tagged By)", color = SteelGreyLight) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = ElectricBlueAccent,
                        unfocusedBorderColor = SteelGreyDark
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Reason selector pills
                Text("හේතුව (Reason):", color = SteelGreyLight, style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("DAMAGED" to "දෝෂ සහිත", "EXCESS" to "අමතර", "OBSOLETE" to "පරණ").forEach { (r, l) ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (reason == r) Color(0xFFEF4444) else IndustrialNavySurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (reason == r) Color(0xFFEF4444) else SteelGreyDark),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { reason = r }
                        ) {
                            Text(
                                text = l,
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }

                // Action selector pills
                Text("ක්‍රියාමාර්ගය (Action):", color = SteelGreyLight, style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("SCRAP" to "ඉවත් කිරීම", "REPAIR" to "අලුත්වැඩියාව", "STORE" to "ගබඩා").forEach { (a, l) ->
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (action == a) WarningAmber else IndustrialNavySurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (action == a) WarningAmber else SteelGreyDark),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { action = a }
                        ) {
                            Text(
                                text = l,
                                color = if (action == a) Color.Black else Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (itemName.isNotBlank() && machineModel.isNotBlank()) {
                        onSave(itemName, machineModel, category, reason, action, location, taggedBy)
                    }
                },
                enabled = itemName.isNotBlank() && machineModel.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444))
            ) {
                Text("ටැගය සුරකින්න (Save Tag)", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("අවලංගුයි (Cancel)", color = Color.White)
            }
        },
        containerColor = IndustrialNavyCard
    )
}
