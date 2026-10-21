package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TechnicalArticle
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.CategoryChip
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavySurface
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun KnowledgeListScreen(
    articles: List<TechnicalArticle>,
    initialCategory: String = "ALL",
    initialMachine: String = "ALL",
    initialSearchQuery: String = "",
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit,
    onArticleSelected: (Long) -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var selectedMachine by remember { mutableStateOf(initialMachine) }
    var searchQuery by remember { mutableStateOf(initialSearchQuery) }

    val categories = listOf(
        "ALL" to LocaleStrings.get(LocaleStrings.CAT_ALL, currentLanguage),
        "MANUALS" to LocaleStrings.get(LocaleStrings.CAT_MANUALS, currentLanguage),
        "VIDEOS" to LocaleStrings.get(LocaleStrings.CAT_VIDEOS, currentLanguage),
        "TIMING" to LocaleStrings.get(LocaleStrings.CAT_TIMING, currentLanguage),
        "TROUBLESHOOTING" to LocaleStrings.get(LocaleStrings.CAT_TROUBLESHOOTING, currentLanguage),
        "MAINTENANCE" to LocaleStrings.get(LocaleStrings.CAT_MAINTENANCE, currentLanguage),
        "BOOKMARKS" to LocaleStrings.get(LocaleStrings.HOME_STATUS_BOOKMARKS, currentLanguage)
    )

    val machineTypes = listOf(
        "ALL" to LocaleStrings.get(LocaleStrings.MACHINE_ALL, currentLanguage),
        "Juki" to "Juki (DDL/LK/LBH)",
        "Pegasus" to "Pegasus (M900/W500)",
        "Siruba" to "Siruba (747K)",
        "Yamato" to "Yamato (VC2700)",
        "Jack" to "Jack (A4/C4)",
        "Brother" to "Brother (S-7200)",
        "Overlock / Serger" to LocaleStrings.get(LocaleStrings.MACHINE_OVERLOCK, currentLanguage),
        "Flatlock / Interlock" to LocaleStrings.get(LocaleStrings.MACHINE_FLATLOCK, currentLanguage),
        "Buttonhole" to LocaleStrings.get(LocaleStrings.MACHINE_BUTTONHOLE, currentLanguage),
        "Bartack" to LocaleStrings.get(LocaleStrings.MACHINE_BARTACK, currentLanguage)
    )

    // Quick suggestion chips for machine models and essential technician keywords
    val quickModelChips = listOf(
        "DDL-8700",
        "DDL-9000",
        "Pegasus M900",
        "Siruba 747",
        "Yamato VC2700",
        "Jack A4",
        "Brother S-7200",
        "Rotary Hook",
        "Looper Timing",
        "Spreader",
        "Manual",
        "Video",
        "Oil Pump"
    )

    val filteredArticles = articles.filter { article ->
        val matchesCategory = when (selectedCategory) {
            "ALL" -> true
            "BOOKMARKS" -> article.isBookmarked
            else -> article.category.equals(selectedCategory, ignoreCase = true)
        }
        val matchesMachine = when (selectedMachine) {
            "ALL" -> true
            "Juki" -> article.titleEnglish.contains("Juki", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Juki", ignoreCase = true) ||
                    article.summaryEnglish.contains("Juki", ignoreCase = true) ||
                    article.machineType.equals("General", ignoreCase = true)
            "Pegasus" -> article.titleEnglish.contains("Pegasus", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Pegasus", ignoreCase = true) ||
                    article.machineType.contains("Overlock", ignoreCase = true) ||
                    article.machineType.contains("Flatlock", ignoreCase = true)
            "Siruba" -> article.titleEnglish.contains("Siruba", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Siruba", ignoreCase = true) ||
                    article.contentEnglish.contains("Siruba", ignoreCase = true)
            "Yamato" -> article.titleEnglish.contains("Yamato", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Yamato", ignoreCase = true) ||
                    article.contentEnglish.contains("Yamato", ignoreCase = true)
            "Jack" -> article.titleEnglish.contains("Jack", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Jack", ignoreCase = true)
            "Brother" -> article.titleEnglish.contains("Brother", ignoreCase = true) ||
                    article.keySpecsOrTiming.contains("Brother", ignoreCase = true)
            else -> article.machineType.contains(selectedMachine, ignoreCase = true) ||
                    article.machineType.equals("All Machines", ignoreCase = true) ||
                    article.titleEnglish.contains(selectedMachine, ignoreCase = true) ||
                    article.keySpecsOrTiming.contains(selectedMachine, ignoreCase = true)
        }
        val matchesSearch = if (searchQuery.isBlank()) true else {
            val terms = searchQuery.trim().split("\\s+".toRegex()).filter { it.isNotBlank() }
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
                append(article.category).append(" ")
                append(article.difficulty)
            }
            val normalizedCombined = combinedText.replace("-", "").replace("/", "").lowercase()

            terms.all { term ->
                val cleanTerm = term.lowercase()
                val normalizedTerm = cleanTerm.replace("-", "").replace("/", "")
                combinedText.contains(cleanTerm, ignoreCase = true) ||
                        normalizedCombined.contains(normalizedTerm)
            }
        }
        val hasValidTitle = article.getTitle(currentLanguage).trim().isNotEmpty() &&
                (article.titleSinhala.trim().isNotEmpty() || article.titleEnglish.trim().isNotEmpty())
        matchesCategory && matchesMachine && matchesSearch && hasValidTitle
    }

    val isFilterActive = searchQuery.isNotBlank() || selectedCategory != "ALL" || selectedMachine != "ALL"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("knowledge_list_screen")
    ) {
        // Search Input Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(top = 10.dp, bottom = 6.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.SEARCH_FILTER_HINT, currentLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp),
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
                        if (searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { searchQuery = "" },
                                modifier = Modifier.testTag("search_clear_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ElectricBlueAccent,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .testTag("knowledge_search_input")
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quick model suggestion pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickModelChips.forEach { chipText ->
                        val isChipActive = searchQuery.contains(chipText, ignoreCase = true)
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isChipActive) ElectricBlueAccent else SteelGreyDark.copy(alpha = 0.08f),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isChipActive) ElectricBlueAccent else SteelGreyMedium.copy(alpha = 0.25f)
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    searchQuery = if (isChipActive) "" else chipText
                                }
                                .testTag("quick_chip_$chipText")
                        ) {
                            Text(
                                text = chipText,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (isChipActive) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 11.sp,
                                    color = if (isChipActive) Color.White else MaterialTheme.colorScheme.onSurface
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }

        // Category Filter Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { (key, label) ->
                CategoryChip(
                    label = label,
                    isSelected = selectedCategory == key,
                    onClick = { selectedCategory = key },
                    modifier = Modifier.testTag("cat_chip_$key")
                )
            }
        }

        // Machine Model & Type Filter Chips Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            machineTypes.forEach { (key, label) ->
                CategoryChip(
                    label = label,
                    isSelected = selectedMachine == key,
                    onClick = { selectedMachine = key },
                    modifier = Modifier.testTag("machine_filter_chip_$key")
                )
            }
        }

        // Results Count & Active Filter Indicator Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterAlt,
                    contentDescription = null,
                    tint = ElectricBlueAccent,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${filteredArticles.size} ${LocaleStrings.get(LocaleStrings.SEARCH_RESULTS_COUNT, currentLanguage)}" +
                            if (searchQuery.isNotBlank()) " (\"$searchQuery\")" else "",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            if (isFilterActive) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = WarningAmber.copy(alpha = 0.15f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber.copy(alpha = 0.4f)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            searchQuery = ""
                            selectedCategory = "ALL"
                            selectedMachine = "ALL"
                        }
                        .testTag("clear_all_filters_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear Filters",
                            tint = WarningAmber,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.BTN_CLEAR, currentLanguage),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        if (filteredArticles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = IndustrialNavyCard
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyDark),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("no_search_results_card")
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(28.dp),
                            color = WarningAmber.copy(alpha = 0.2f),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = null,
                                    tint = WarningAmber,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = LocaleStrings.get(LocaleStrings.SEARCH_NO_RESULTS, currentLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (currentLanguage == AppLanguage.SINHALA)
                                "පහත ප්‍රධාන මොඩල හෝ මූල පද වලින් උත්සාහ කරන්න:"
                            else if (currentLanguage == AppLanguage.TAMIL)
                                "பின்வரும் மாதிரிகள் அல்லது முக்கிய சொற்களை முயற்சிக்கவும்:"
                            else
                                "Try searching for one of these machine models or topics:",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            ),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Quick recovery chips
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        ) {
                            listOf("DDL-8700", "Pegasus M900", "Siruba 747", "Rotary Hook", "Manuals", "Videos").forEach { recTerm ->
                                Button(
                                    onClick = {
                                        searchQuery = recTerm
                                        selectedCategory = "ALL"
                                        selectedMachine = "ALL"
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = ElectricBlueAccent.copy(alpha = 0.25f)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Text(
                                        text = recTerm,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                searchQuery = ""
                                selectedCategory = "ALL"
                                selectedMachine = "ALL"
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ElectricBlueAccent
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.BTN_RESET, currentLanguage),
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredArticles) { article ->
                    ArticleListItem(
                        article = article,
                        currentLanguage = currentLanguage,
                        onClick = { onArticleSelected(article.id) }
                    )
                }
            }
        }
    }
}
