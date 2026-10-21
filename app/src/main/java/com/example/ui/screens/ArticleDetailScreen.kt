package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TechnicalArticle
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.DifficultyBadge
import com.example.ui.components.MachineTypeBadge
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavySurface
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun ArticleDetailScreen(
    article: TechnicalArticle?,
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit,
    onToggleBookmark: (Long, Boolean) -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    if (article == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Guide not found")
        }
        return
    }

    // Language view tab within the technical content interface
    var selectedLanguageTab by remember {
        mutableStateOf(currentLanguage)
    }

    var showCompareAll by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .testTag("article_detail_screen")
    ) {
        // Header Card
        Card(
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = IndustrialNavyCard
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (article.category.uppercase()) {
                            "MANUALS" -> {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = WarningAmber.copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MenuBook,
                                            contentDescription = null,
                                            tint = WarningAmber,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = LocaleStrings.get(LocaleStrings.BADGE_MANUAL, currentLanguage),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = WarningAmber,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                            "VIDEOS" -> {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color(0xFFEF4444).copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444))
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PlayCircle,
                                            contentDescription = null,
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = LocaleStrings.get(LocaleStrings.BADGE_VIDEO, currentLanguage),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Color(0xFFEF4444),
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        MachineTypeBadge(machineType = article.machineType)
                        DifficultyBadge(difficulty = article.difficulty)
                    }

                    IconButton(
                        onClick = { onToggleBookmark(article.id, article.isBookmarked) },
                        modifier = Modifier.testTag("detail_bookmark_toggle")
                    ) {
                        Icon(
                            imageVector = if (article.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (article.isBookmarked) WarningAmber else Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title in active tab language
                Text(
                    text = article.getTitle(selectedLanguageTab),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp,
                        lineHeight = 28.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Summary in active tab language
                Text(
                    text = article.getSummary(selectedLanguageTab),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 20.sp
                    )
                )
            }
        }

        // Technical Content Interface Language Switcher
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = LocaleStrings.get(LocaleStrings.CONTENT_VIEW_LANGUAGE, currentLanguage),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppLanguage.entries.forEach { lang ->
                        val isSelected = (!showCompareAll) && (selectedLanguageTab == lang)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) ElectricBlueAccent else SteelGreyDark.copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) ElectricBlueAccent else SteelGreyMedium.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    selectedLanguageTab = lang
                                    showCompareAll = false
                                }
                                .testTag("article_lang_tab_${lang.code}")
                        ) {
                            Text(
                                text = lang.nativeName,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }

                    // Compare All Button
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (showCompareAll) WarningAmber else SteelGreyDark.copy(alpha = 0.1f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (showCompareAll) WarningAmber else SteelGreyMedium.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .weight(1.1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { showCompareAll = true }
                            .testTag("article_lang_tab_compare")
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.SINHALA) "සංසන්දනය"
                            else if (currentLanguage == AppLanguage.TAMIL) "ஒப்பீடு"
                            else "Compare",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (showCompareAll) FontWeight.Bold else FontWeight.Medium,
                                color = if (showCompareAll) Color.White else MaterialTheme.colorScheme.onSurface
                            ),
                            modifier = Modifier.padding(vertical = 10.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

        // Key Specs Banner
        if (article.keySpecsOrTiming.isNotEmpty()) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = ElectricBlueAccent.copy(alpha = 0.12f)
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent.copy(alpha = 0.7f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PrecisionManufacturing,
                            contentDescription = null,
                            tint = ElectricBlueAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.CONTENT_SPEC_TITLE, currentLanguage),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = ElectricBlueAccent
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.keySpecsOrTiming,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }

        // Content Body
        if (showCompareAll) {
            // Compare All Multi-Language View
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Sinhala Box
                LanguageContentBox(
                    langLabel = "සිංහල (Sinhala)",
                    title = article.titleSinhala,
                    summary = article.summarySinhala,
                    content = article.contentSinhala,
                    accentColor = ElectricBlueAccent
                )

                // English Box
                LanguageContentBox(
                    langLabel = "English",
                    title = article.titleEnglish,
                    summary = article.summaryEnglish,
                    content = article.contentEnglish,
                    accentColor = MachineGreen
                )

                // Tamil Box
                LanguageContentBox(
                    langLabel = "தமிழ் (Tamil)",
                    title = article.titleTamil,
                    summary = article.summaryTamil,
                    content = article.contentTamil,
                    accentColor = WarningAmber
                )
            }
        } else {
            // Single Selected Language Content with Step by Step Display
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = LocaleStrings.get(LocaleStrings.CONTENT_STEPS_TITLE, currentLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                val steps = article.getContent(selectedLanguageTab).split("\n").filter { it.isNotBlank() }
                steps.forEachIndexed { index, step ->
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = ElectricBlueAccent,
                                modifier = Modifier.size(28.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = step.replace(Regex("^\\d+\\.\\s*"), ""),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 22.sp
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun LanguageContentBox(
    langLabel: String,
    title: String,
    summary: String,
    content: String,
    accentColor: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor.copy(alpha = 0.6f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = accentColor.copy(alpha = 0.2f),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = langLabel,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = accentColor,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Text(
                text = title.ifEmpty { "—" },
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            )

            if (summary.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = summary,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = content.ifEmpty { "—" },
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 22.sp
                )
            )
        }
    }
}
