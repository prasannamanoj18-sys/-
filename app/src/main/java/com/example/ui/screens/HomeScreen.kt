package com.example.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assessment
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TechnicalArticle
import com.example.data.model.TechnicianProfile
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.CategoryChip
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.MachineTypeBadge
import com.example.ui.navigation.Screen
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavyDark
import com.example.ui.theme.IndustrialNavySurface
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyLight
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.SteelGreySurface
import com.example.ui.theme.WarningAmber

@Composable
fun HomeScreen(
    currentLanguage: AppLanguage,
    profile: TechnicianProfile?,
    recentArticles: List<TechnicalArticle>,
    bookmarkedCount: Int,
    completedPmCount: Int,
    onLanguageSelected: (AppLanguage) -> Unit,
    onNavigate: (Screen) -> Unit,
    onArticleSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    val homeMatchingArticles: List<TechnicalArticle> = remember(searchQuery, recentArticles) {
        if (searchQuery.isBlank()) emptyList()
        else {
            val terms = searchQuery.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
            recentArticles.filter { article ->
                val combinedText = buildString {
                    append(article.titleSinhala).append(" ")
                    append(article.titleEnglish).append(" ")
                    append(article.titleTamil).append(" ")
                    append(article.summarySinhala).append(" ")
                    append(article.summaryEnglish).append(" ")
                    append(article.summaryTamil).append(" ")
                    append(article.contentSinhala).append(" ")
                    append(article.contentEnglish).append(" ")
                    append(article.contentTamil).append(" ")
                    append(article.keySpecsOrTiming).append(" ")
                    append(article.machineType).append(" ")
                    append(article.category)
                }
                val normalizedCombined = combinedText.replace("-", "").replace("/", "").lowercase()
                terms.all { term ->
                    val cleanTerm = term.lowercase()
                    val normalizedTerm = cleanTerm.replace("-", "").replace("/", "")
                    combinedText.contains(cleanTerm, ignoreCase = true) || normalizedCombined.contains(normalizedTerm)
                }
            }
        }
    }
    val machineTypes = listOf(
        "ALL" to LocaleStrings.get(LocaleStrings.MACHINE_ALL, currentLanguage),
        "Overlock / Serger" to LocaleStrings.get(LocaleStrings.MACHINE_OVERLOCK, currentLanguage),
        "Flatlock / Interlock" to LocaleStrings.get(LocaleStrings.MACHINE_FLATLOCK, currentLanguage),
        "Buttonhole" to LocaleStrings.get(LocaleStrings.MACHINE_BUTTONHOLE, currentLanguage),
        "Bartack" to LocaleStrings.get(LocaleStrings.MACHINE_BARTACK, currentLanguage)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_screen_lazy_column"),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Hero Banner with Dark Navy & Blue Accents
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(IndustrialNavyDark, IndustrialNavy, IndustrialNavyCard)
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.APP_NAME, currentLanguage),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 28.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.APP_SUBTITLE, currentLanguage),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = ElectricBlueAccent,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }

                        // App Badge Icon
                        Surface(
                            shape = CircleShape,
                            color = ElectricBlueAccent.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                            modifier = Modifier.size(52.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Handyman,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Tri-lingual Language Quick Selector Pills
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = IndustrialNavySurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AppLanguage.entries.forEach { lang ->
                                val isSelected = lang == currentLanguage
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) ElectricBlueAccent else Color.Transparent,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onLanguageSelected(lang) }
                                        .testTag("home_lang_pill_${lang.code}")
                                ) {
                                    Text(
                                        text = lang.nativeName,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f),
                                            fontSize = 14.sp
                                        ),
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Search bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.SEARCH_FILTER_HINT, currentLanguage),
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 13.sp,
                                maxLines = 1
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
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(
                                        onClick = { searchQuery = "" },
                                        modifier = Modifier.size(32.dp).testTag("home_search_clear_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear",
                                            tint = Color.White.copy(alpha = 0.7f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Button(
                                        onClick = {
                                            onNavigate(
                                                Screen.KnowledgeList(
                                                    categoryFilter = "ALL",
                                                    machineFilter = "ALL",
                                                    searchQuery = searchQuery.trim()
                                                )
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = ElectricBlueAccent
                                        ),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("home_search_submit_button")
                                    ) {
                                        Text(
                                            text = LocaleStrings.get(LocaleStrings.BTN_SEARCH, currentLanguage),
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = IndustrialNavySurface,
                            unfocusedContainerColor = IndustrialNavySurface,
                            focusedBorderColor = ElectricBlueAccent,
                            unfocusedBorderColor = SteelGreyDark,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("home_search_input")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Machine Model Quick Search Suggestion Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("DDL-8700", "Pegasus M900", "Siruba 747", "Yamato VC", "Manuals", "Videos", "Timing").forEach { chipTerm ->
                            val isChipActive = searchQuery.contains(chipTerm, ignoreCase = true)
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = if (isChipActive) ElectricBlueAccent else IndustrialNavySurface,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isChipActive) ElectricBlueAccent else SteelGreyDark
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        searchQuery = if (isChipActive) "" else chipTerm
                                    }
                                    .testTag("home_quick_chip_$chipTerm")
                            ) {
                                Text(
                                    text = chipTerm,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isChipActive) Color.White else Color.White.copy(alpha = 0.8f),
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 11.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dedicated Sinhala button (Requirement 1: "Add a Sinhala button named 'QR කේතය Scan කරන්න'")
                    Button(
                        onClick = { onNavigate(Screen.QrScanner) },
                        colors = ButtonDefaults.buttonColors(containerColor = MachineGreen),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("home_hero_btn_qr_scan")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "QR කේතය Scan කරන්න",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color.Black.copy(alpha = 0.25f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FlashOn,
                                        contentDescription = "Torch",
                                        tint = WarningAmber,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "TORCH",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = WarningAmber,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Live Search Results Dropdown/Card on Home Screen
        if (searchQuery.isNotBlank()) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("home_live_search_card")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🔍 ${homeMatchingArticles.size} ${LocaleStrings.get(LocaleStrings.SEARCH_RESULTS_COUNT, currentLanguage)}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ElectricBlueAccent
                                )
                            )
                            IconButton(
                                onClick = { searchQuery = "" },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.White.copy(alpha = 0.7f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        if (homeMatchingArticles.isEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.SEARCH_NO_RESULTS, currentLanguage),
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.7f))
                            )
                        } else {
                            for (matchArticle in homeMatchingArticles.take(3)) {
                                ArticleListItem(
                                    article = matchArticle,
                                    currentLanguage = currentLanguage,
                                    onClick = { onArticleSelected(matchArticle.id) }
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Button(
                                onClick = {
                                    onNavigate(
                                        Screen.KnowledgeList(
                                            categoryFilter = "ALL",
                                            machineFilter = "ALL",
                                            searchQuery = searchQuery.trim()
                                        )
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = LocaleStrings.get(LocaleStrings.BTN_VIEW_ALL, currentLanguage) + " (${homeMatchingArticles.size})",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Status & Stats Row
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = LocaleStrings.get(LocaleStrings.HOME_STATUS_BOOKMARKS, currentLanguage),
                    value = "$bookmarkedCount",
                    icon = Icons.Default.Bookmark,
                    iconColor = WarningAmber,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate(Screen.KnowledgeList(categoryFilter = "BOOKMARKS", machineFilter = "ALL"))
                }

                StatCard(
                    title = LocaleStrings.get(LocaleStrings.HOME_STATUS_MAINTENANCE, currentLanguage),
                    value = "$completedPmCount",
                    icon = Icons.Default.CheckCircle,
                    iconColor = MachineGreen,
                    modifier = Modifier.weight(1f)
                ) {
                    onNavigate(Screen.Maintenance)
                }

                StatCard(
                    title = LocaleStrings.get(LocaleStrings.HOME_STATUS_TECH_LEVEL, currentLanguage),
                    value = profile?.membershipLevel ?: "CERTIFIED",
                    icon = Icons.Default.Verified,
                    iconColor = ElectricBlueAccent,
                    isSmallValue = true,
                    modifier = Modifier.weight(1.2f)
                ) {
                    onNavigate(Screen.Membership)
                }
            }
        }

        // Daily Technician Tip
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = IndustrialNavyCard
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("daily_tip_card")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = WarningAmber.copy(alpha = 0.2f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = WarningAmber,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.HOME_FEATURED_TIP, currentLanguage),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.White,
                            lineHeight = 20.sp
                        )
                    )
                }
            }
        }

        // Machine Type Quick Filter
        item {
            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text(
                    text = LocaleStrings.get(LocaleStrings.HOME_MACHINES_TITLE, currentLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    machineTypes.forEach { (key, label) ->
                        CategoryChip(
                            label = label,
                            isSelected = false,
                            onClick = {
                                onNavigate(Screen.KnowledgeList(categoryFilter = "ALL", machineFilter = key))
                            },
                            modifier = Modifier.testTag("machine_filter_chip_$key")
                        )
                    }
                }
            }
        }

        // Main Technical Sections (6 Large Touch Target Cards)
        item {
            Text(
                text = LocaleStrings.get(LocaleStrings.HOME_QUICK_ACTIONS, currentLanguage),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp)
            )
        }

        // Grid of 6 Action Cards
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_TROUBLESHOOT_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_TROUBLESHOOT_DESC, currentLanguage),
                        icon = Icons.Default.Build,
                        accentColor = ElectricBlueAccent,
                        testTag = "home_card_troubleshoot",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.KnowledgeList(categoryFilter = "TROUBLESHOOTING", machineFilter = "ALL"))
                    }

                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_MANUALS_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_MANUALS_DESC, currentLanguage),
                        icon = Icons.Default.MenuBook,
                        accentColor = WarningAmber,
                        testTag = "home_card_timing",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.KnowledgeList(categoryFilter = "MANUALS", machineFilter = "ALL"))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_ERRORS_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_ERRORS_DESC, currentLanguage),
                        icon = Icons.Default.ErrorOutline,
                        accentColor = Color(0xFFEF4444),
                        testTag = "home_card_errors",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.ErrorCodes)
                    }

                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_MAINTENANCE_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_MAINTENANCE_DESC, currentLanguage),
                        icon = Icons.Default.CheckCircle,
                        accentColor = MachineGreen,
                        testTag = "home_card_maintenance",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.Maintenance)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_CALCULATORS_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_CALCULATORS_DESC, currentLanguage),
                        icon = Icons.Default.Calculate,
                        accentColor = ElectricBlueAccent,
                        testTag = "home_card_calculators",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.Calculators)
                    }

                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CARD_MEMBERSHIP_TITLE, currentLanguage),
                        desc = LocaleStrings.get(LocaleStrings.CARD_MEMBERSHIP_DESC, currentLanguage),
                        icon = Icons.Default.Verified,
                        accentColor = Color(0xFF8B5CF6),
                        testTag = "home_card_membership",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.Membership)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CAT_VIDEOS, currentLanguage),
                        desc = if (currentLanguage == AppLanguage.SINHALA) "කාර්මික පුහුණු සහ ටයිමිං වීඩියෝ"
                        else if (currentLanguage == AppLanguage.TAMIL) "பயிற்சி மற்றும் டைமிங் வீடியோக்கள்"
                        else "Technician training & timing videos",
                        icon = Icons.Default.PlayCircle,
                        accentColor = Color(0xFFEF4444),
                        testTag = "home_card_videos",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.KnowledgeList(categoryFilter = "VIDEOS", machineFilter = "ALL"))
                    }

                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.CAT_TIMING, currentLanguage),
                        desc = if (currentLanguage == AppLanguage.SINHALA) "හුක් සහ ලූපර් ටයිමිං පිරිවිතර"
                        else if (currentLanguage == AppLanguage.TAMIL) "ஹூக் மற்றும் லூப்பர் டைமிங்"
                        else "Hook & looper timing specs",
                        icon = Icons.Default.Speed,
                        accentColor = ElectricBlueAccent,
                        testTag = "home_card_timing_specs",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.KnowledgeList(categoryFilter = "TIMING", machineFilter = "ALL"))
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.FIVES_TITLE, currentLanguage),
                        desc = if (currentLanguage == AppLanguage.SINHALA) "ජපන් 5S කාර්මික ප්‍රමිතිය සහ පිරික්සුම"
                        else if (currentLanguage == AppLanguage.TAMIL) "5S ஜப்பானிய தரநிலைகள் மற்றும் சரிபார்ப்பு"
                        else "Japanese 5S workplace & machine checklist",
                        icon = Icons.Default.Assessment,
                        accentColor = MachineGreen,
                        testTag = "home_card_five_s_grid",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.FiveS)
                    }

                    MainActionCard(
                        title = LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, currentLanguage),
                        desc = if (currentLanguage == AppLanguage.SINHALA) "ක්ලවුඩ් සේවා අත්පොත් සහ PDF"
                        else if (currentLanguage == AppLanguage.TAMIL) "கிளவுட் கையேடுகள் மற்றும் PDF"
                        else "Cloud service manuals & PDF guides",
                        icon = Icons.Default.FolderShared,
                        accentColor = ElectricBlueAccent,
                        testTag = "home_card_drive_files_grid",
                        modifier = Modifier.weight(1f)
                    ) {
                        onNavigate(Screen.DriveFiles)
                    }
                }
            }
        }

        // Dedicated Card for Measuring Units & Tools (දිග මනින මිනුම් ඒකක සහ මිනුම් උපකරණ)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onNavigate(Screen.MeasuringUnits) }
                    .testTag("home_card_measuring_units")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ElectricBlueAccent.copy(alpha = 0.15f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Straighten,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "දිග මනින මිනුම් ඒකක"
                                    AppLanguage.TAMIL -> "நீள அளவீட்டு அலகுகள்"
                                    AppLanguage.ENGLISH -> "Length Measuring Units"
                                },
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = WarningAmber.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "1/16\" - 1/2\"",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = WarningAmber,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "මිනුම් උපකරණ, අඟල් සලකුණු (1/16\", 1/8\", 1/4\", 1/2\") සහ මිනුම් පටි කියවීම"
                                AppLanguage.TAMIL -> "அளவீட்டு கருவிகள் மற்றும் அங்குல அளவீடுகள் (1/16\", 1/8\", 1/4\", 1/2\")"
                                AppLanguage.ENGLISH -> "Measuring tools, inch markings (1/16\", 1/8\", 1/4\", 1/2\") & tape reading"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Dedicated Card for In-App QR Code Scanner (Requirement 1 & 2)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MachineGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onNavigate(Screen.QrScanner) }
                    .testTag("home_card_qr_scanner")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MachineGreen.copy(alpha = 0.15f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = null,
                                tint = MachineGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "QR කේතය Scan කරන්න",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MachineGreen.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "SCANNER",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MachineGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "මැෂින් අත්පොත්, මාර්ගෝපදේශ සහ ලින්ක් කැමරාව මඟින් ක්ෂණිකව Scan කරන්න"
                                AppLanguage.TAMIL -> "இயந்திர கையேடுகள் மற்றும் இணைப்புகளை உடனடியாக ஸ்கேன் செய்க"
                                AppLanguage.ENGLISH -> "Scan sewing machine manuals, technical guides & links via camera"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Dedicated Card for Google Drive Documents & Cloud Files
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onNavigate(Screen.DriveFiles) }
                    .testTag("home_card_drive_files")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = ElectricBlueAccent.copy(alpha = 0.15f),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.FolderShared,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = ElectricBlueAccent.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = "DRIVE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = ElectricBlueAccent,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.DRIVE_FILES_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Dedicated Card for 5S Lean Methodology (5S ක්‍රියාවලිය)
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = IndustrialNavyCard
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MachineGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .clickable { onNavigate(Screen.FiveS) }
                    .testTag("home_card_five_s_featured")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MachineGreen.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen),
                        modifier = Modifier.size(50.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Assessment,
                                contentDescription = null,
                                tint = MachineGreen,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.FIVES_TITLE, currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = MachineGreen.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen)
                            ) {
                                Text(
                                    text = "5S LEAN",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MachineGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "සෝදිසිය, පිළිවෙල, පිරිසිදුකම, ප්‍රමිතිකරණය සහ විනය (Pillars, Checklist, Red Tag, Audit Scorecard)"
                                AppLanguage.TAMIL -> "வகைப்படுத்துதல், ஒழுங்குபடுத்துதல், பிரகாசித்தல், தரப்படுத்துதல், நிலைநிறுத்துதல்"
                                AppLanguage.ENGLISH -> "Sort, Set in order, Shine, Standardize, Sustain (Checklists, Red Tags & Audits)"
                            },
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SteelGreyLight,
                                lineHeight = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Dedicated Card for Online App Web URL / Live Access Link
        item {
            val appLiveUrl = "https://ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app"
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = IndustrialNavyCard
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("home_card_app_url")
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = ElectricBlueAccent.copy(alpha = 0.2f),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Link,
                                        contentDescription = null,
                                        tint = ElectricBlueAccent,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> "ඇප් එකේ වෙබ් URL එක (App Web Link)"
                                        AppLanguage.TAMIL -> "செயலி இணைய இணைப்பு (App Web Link)"
                                        AppLanguage.ENGLISH -> "Online Web Access URL"
                                    },
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> "ඕනෑම Browser එකකින් හෝ Phone එකකින් පිවිසෙන්න"
                                        AppLanguage.TAMIL -> "எந்த உலாவியிலும் அணுகலாம்"
                                        AppLanguage.ENGLISH -> "Access instantly from any phone or browser"
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SteelGreyMedium,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        // Live status badge
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MachineGreen.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(MachineGreen)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "LIVE",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MachineGreen,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // URL Box Container
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SteelGreyDark,
                        border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyMedium.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("App URL", appLiveUrl)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(
                                    context,
                                    if (currentLanguage == AppLanguage.SINHALA) "URL එක සාර්ථකව පිටපත් කරන ලදී!" else "URL copied to clipboard!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = appLiveUrl,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontFamily = FontFamily.Monospace,
                                    color = ElectricBlueAccent,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy",
                                tint = SteelGreyMedium,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Open in Browser Button
                        Button(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(appLiveUrl)).apply {
                                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not open browser", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1.2f)
                                .height(38.dp)
                                .testTag("btn_open_app_url")
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "විවෘත කරන්න"
                                    AppLanguage.TAMIL -> "திறக்க"
                                    AppLanguage.ENGLISH -> "Open URL"
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }

                        // Copy URL Button
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("App URL", appLiveUrl)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(
                                    context,
                                    if (currentLanguage == AppLanguage.SINHALA) "URL එක පිටපත් කරන ලදී!" else "URL copied!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SteelGreyDark),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .testTag("btn_copy_app_url")
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "Copy"
                                    AppLanguage.TAMIL -> "நகலெடு"
                                    AppLanguage.ENGLISH -> "Copy"
                                },
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }

                        // Share URL Button
                        Button(
                            onClick = {
                                val shareText = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "කාර්මික මහන මැෂින් තාක්ෂණික අත්වැල (Karmika Danuma) Online ඇප් URL එක:\n$appLiveUrl"
                                    AppLanguage.TAMIL -> "தையல் தொழில்நுட்ப வழிகாட்டி:\n$appLiveUrl"
                                    AppLanguage.ENGLISH -> "Sewing Machine Technical Knowledge Online App:\n$appLiveUrl"
                                }
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, "Karmika Danuma App URL")
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share App URL"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MachineGreen),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .testTag("btn_share_app_url")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "බෙදාගන්න"
                                    AppLanguage.TAMIL -> "பகிர்"
                                    AppLanguage.ENGLISH -> "Share"
                                },
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        // Quick Navigation to Admin Dashboard
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = IndustrialNavyCard
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clickable { onNavigate(Screen.Admin) }
                    .testTag("home_admin_shortcut_card")
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = ElectricBlueAccent.copy(alpha = 0.2f),
                        modifier = Modifier.size(42.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.ADMIN_TITLE, currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = LocaleStrings.get(LocaleStrings.ADMIN_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SteelGreyMedium
                            )
                        )
                    }
                }
            }
        }

        // Recent Knowledge Guides - Strictly filter out any blank or empty cards
        val validRecentArticles = recentArticles.filter { article ->
            val title = article.getTitle(currentLanguage).trim()
            val si = article.titleSinhala.trim()
            val en = article.titleEnglish.trim()
            title.length >= 3 && si.length >= 3 && en.length >= 3 &&
                    !title.contains("Single Needle Lockstitch", ignoreCase = true) &&
                    !title.equals("null", ignoreCase = true) &&
                    article.id != 7L
        }

        if (validRecentArticles.isNotEmpty()) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.NAV_GUIDES, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = LocaleStrings.get(LocaleStrings.BTN_VIEW_ALL, currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = ElectricBlueAccent,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            onNavigate(Screen.KnowledgeList(categoryFilter = "ALL", machineFilter = "ALL"))
                        }
                    )
                }
            }

            items(validRecentArticles.take(4)) { article ->
                ArticleListItem(
                    article = article,
                    currentLanguage = currentLanguage,
                    onClick = {
                        if (article.id == 7L) {
                            onNavigate(Screen.MeasuringUnits)
                        } else {
                            onArticleSelected(article.id)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun MainActionCard(
    title: String,
    desc: String,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = modifier
            .height(160.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = accentColor.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    ),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    ),
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    isSmallValue: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = value,
                style = if (isSmallValue) MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                else MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 10.sp
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun ArticleListItem(
    article: TechnicalArticle,
    currentLanguage: AppLanguage,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = article.getTitle(currentLanguage).trim()
    if (title.length < 3 || title.contains("Single Needle Lockstitch", ignoreCase = true) || title.equals("null", ignoreCase = true)) return

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .testTag("article_item_${article.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (article.category.uppercase()) {
                        "MANUALS" -> {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = WarningAmber.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber.copy(alpha = 0.6f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MenuBook,
                                        contentDescription = null,
                                        tint = WarningAmber,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = LocaleStrings.get(LocaleStrings.BADGE_MANUAL, currentLanguage),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = WarningAmber,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                        "VIDEOS" -> {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFEF4444).copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f))
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayCircle,
                                        contentDescription = null,
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = LocaleStrings.get(LocaleStrings.BADGE_VIDEO, currentLanguage),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color(0xFFEF4444),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    if (article.machineType.isNotBlank() &&
                        !article.machineType.contains("Single Needle", ignoreCase = true) &&
                        !article.machineType.contains("Lockstitch", ignoreCase = true) &&
                        !article.machineType.contains("signal", ignoreCase = true) &&
                        !article.machineType.equals("General", ignoreCase = true)) {
                        MachineTypeBadge(machineType = article.machineType)
                    }
                }
                DifficultyBadge(difficulty = article.difficulty)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.getTitle(currentLanguage),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = article.getSummary(currentLanguage),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                ),
                maxLines = 2
            )

            if (article.keySpecsOrTiming.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = ElectricBlueAccent.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "⚙️ ${article.keySpecsOrTiming}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ElectricBlueAccent,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
