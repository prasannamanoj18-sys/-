package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MaintenanceTask
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark

@Composable
fun MaintenanceScreen(
    tasks: List<MaintenanceTask>,
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit,
    onToggleTask: (Long, Boolean) -> Unit,
    onResetChecklist: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val frequencies = listOf("DAILY", "WEEKLY", "MONTHLY")

    val currentFrequency = frequencies[selectedTabIndex]
    val filteredTasks = tasks.filter { it.frequency.equals(currentFrequency, ignoreCase = true) }
    val totalTasks = tasks.size
    val completedTotal = tasks.count { it.isCompleted }
    val progress = if (totalTasks > 0) completedTotal.toFloat() / totalTasks.toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("maintenance_screen")
    ) {
        // Progress Card
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = IndustrialNavyCard
            ),
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
                            text = LocaleStrings.get(LocaleStrings.PM_COMPLETED_PROGRESS, currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Text(
                            text = "$completedTotal / $totalTasks tasks finished",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = ElectricBlueAccent
                            )
                        )
                    }

                    Button(
                        onClick = onResetChecklist,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SteelGreyDark
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("maintenance_reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.BTN_RESET, currentLanguage),
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MachineGreen,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
            }
        }

        // Frequency Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.PM_DAILY, currentLanguage),
                        fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.PM_WEEKLY, currentLanguage),
                        fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.PM_MONTHLY, currentLanguage),
                        fontWeight = if (selectedTabIndex == 2) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(filteredTasks) { task ->
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (task.isCompleted) MachineGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surface
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (task.isCompleted) MachineGreen.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleTask(task.id, task.isCompleted) }
                        .testTag("task_item_${task.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { onToggleTask(task.id, task.isCompleted) },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MachineGreen
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = task.getTitle(currentLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Medium,
                                textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                                color = if (task.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
