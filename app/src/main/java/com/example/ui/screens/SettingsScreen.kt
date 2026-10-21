package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TechnicianProfile
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun SettingsScreen(
    currentLanguage: AppLanguage,
    profile: TechnicianProfile?,
    isDarkTheme: Boolean,
    onLanguageSelected: (AppLanguage) -> Unit,
    onToggleTheme: (Boolean) -> Unit,
    onUpdateProfile: (TechnicianProfile) -> Unit,
    onRestoreDatabase: () -> Unit,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val techProfile = profile ?: TechnicianProfile()
    var nameInput by remember(techProfile) { mutableStateOf(techProfile.name) }
    var roleInput by remember(techProfile) { mutableStateOf(techProfile.role) }
    var factoryInput by remember(techProfile) { mutableStateOf(techProfile.factoryName) }
    var experienceInput by remember(techProfile) { mutableIntStateOf(techProfile.experienceYears) }
    var showProfileSavedBanner by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("settings_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Required Language Selector Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_language_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Language,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.LANGUAGE_SELECTOR_TITLE, currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = LocaleStrings.get(LocaleStrings.SETTINGS_LANGUAGE_DESC, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                Spacer(modifier = Modifier.height(14.dp))

                // Options:
                // • සිංහල
                // • English
                // • தமிழ்
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AppLanguage.entries.forEach { lang ->
                        val isSelected = lang == currentLanguage
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) ElectricBlueAccent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) ElectricBlueAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable { onLanguageSelected(lang) }
                                .testTag("settings_lang_option_${lang.code}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onLanguageSelected(lang) },
                                    colors = RadioButtonDefaults.colors(selectedColor = ElectricBlueAccent)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "• ${lang.nativeName}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            fontSize = 17.sp
                                        )
                                    )
                                    Text(
                                        text = when (lang) {
                                            AppLanguage.SINHALA -> "ප්‍රාථමික භාෂාව (Primary)"
                                            AppLanguage.ENGLISH -> "Secondary (International Standard)"
                                            AppLanguage.TAMIL -> "இரண்டாம் நிலை (Secondary)"
                                        },
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // User Account & Profile Settings Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.ACCOUNT_TITLE, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text(LocaleStrings.get(LocaleStrings.ACCOUNT_NAME_LABEL, currentLanguage)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = roleInput,
                    onValueChange = { roleInput = it },
                    label = { Text(LocaleStrings.get(LocaleStrings.ACCOUNT_ROLE_LABEL, currentLanguage)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = factoryInput,
                    onValueChange = { factoryInput = it },
                    label = { Text(LocaleStrings.get(LocaleStrings.ACCOUNT_FACTORY_LABEL, currentLanguage)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val updated = techProfile.copy(
                            name = nameInput,
                            role = roleInput,
                            factoryName = factoryInput,
                            experienceYears = experienceInput
                        )
                        onUpdateProfile(updated)
                        showProfileSavedBanner = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("save_profile_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(LocaleStrings.get(LocaleStrings.BTN_SAVE, currentLanguage))
                }

                if (showProfileSavedBanner) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✅ " + LocaleStrings.get(LocaleStrings.ACCOUNT_UPDATE_SUCCESS, currentLanguage),
                        style = MaterialTheme.typography.bodySmall.copy(color = ElectricBlueAccent)
                    )
                }
            }
        }

        // Theme Toggle Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DarkMode,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.SETTINGS_THEME_TITLE, currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = if (isDarkTheme) LocaleStrings.get(LocaleStrings.SETTINGS_THEME_DARK, currentLanguage)
                            else LocaleStrings.get(LocaleStrings.SETTINGS_THEME_LIGHT, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { onToggleTheme(it) },
                    modifier = Modifier.testTag("theme_toggle_switch")
                )
            }
        }

        // Factory Reset Data
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Restore,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.ADMIN_RESTORE_DEFAULTS, currentLanguage),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "Reset all knowledge base guides and error codes",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                Button(
                    onClick = onRestoreDatabase,
                    colors = ButtonDefaults.buttonColors(containerColor = SteelGreyDark),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(LocaleStrings.get(LocaleStrings.BTN_RESET, currentLanguage), fontSize = 12.sp)
                }
            }
        }

        // Share Application Link Card
        val context = LocalContext.current
        val clipboardManager = LocalClipboardManager.current
        val shareUrl = "https://ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app"
        var linkCopiedMessageVisible by remember { mutableStateOf(false) }

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("settings_share_app_card")
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.SETTINGS_SHARE_TITLE, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = LocaleStrings.get(LocaleStrings.SETTINGS_SHARE_DESC, currentLanguage),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Link Display Container
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SteelGreyDark,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SteelGreyMedium),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = ElectricBlueAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = shareUrl,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Open in Browser Button
                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(shareUrl)).apply {
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
                            .weight(1.1f)
                            .testTag("open_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.OpenInBrowser,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "විවෘත කරන්න"
                                AppLanguage.TAMIL -> "திறக்க"
                                AppLanguage.ENGLISH -> "Open"
                            },
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Copy Link Button
                    Button(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(shareUrl))
                            linkCopiedMessageVisible = true
                            Toast.makeText(
                                context,
                                LocaleStrings.get(LocaleStrings.MSG_LINK_COPIED, currentLanguage),
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SteelGreyDark),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("copy_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null,
                            tint = ElectricBlueAccent,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.BTN_COPY_LINK, currentLanguage),
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }

                    // Share Link Button
                    Button(
                        onClick = {
                            val shareMessage = when (currentLanguage) {
                                AppLanguage.SINHALA -> "කාර්මික මහන මැෂින් තාක්ෂණික අත්වැල (Sewing Tech Knowledge App) භාවිතා කිරීමට හෝ ඔබගේ දුරකථනයට එක් කරගැනීමට මෙම Link එකට පිවිසෙන්න:\n$shareUrl"
                                AppLanguage.TAMIL -> "ஆடை தொழிற்சாலை தையல் தொழில்நுட்ப வழிகாட்டி செயலி (Sewing Tech Knowledge App):\n$shareUrl"
                                AppLanguage.ENGLISH -> "Access or install the Sewing Machine Technical Knowledge App:\n$shareUrl"
                            }
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Sewing Tech App Link")
                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share App"))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("share_link_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.BTN_SHARE_APP, currentLanguage),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (linkCopiedMessageVisible) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✅ " + LocaleStrings.get(LocaleStrings.MSG_LINK_COPIED, currentLanguage),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = ElectricBlueAccent,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // About Application Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.SETTINGS_ABOUT_TITLE, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = LocaleStrings.get(LocaleStrings.SETTINGS_ABOUT_DESC, currentLanguage),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = LocaleStrings.get(LocaleStrings.SETTINGS_VERSION, currentLanguage),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = ElectricBlueAccent,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
