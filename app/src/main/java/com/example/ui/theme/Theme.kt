package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CyanAccent,
    onPrimary = IndustrialNavyDark,
    primaryContainer = IndustrialNavyCard,
    onPrimaryContainer = CyanAccent,
    secondary = SkyBlueLight,
    onSecondary = IndustrialNavyDark,
    secondaryContainer = IndustrialNavySurface,
    onSecondaryContainer = SkyBlueLight,
    tertiary = MachineGreen,
    onTertiary = PureWhite,
    background = IndustrialNavy,
    onBackground = TextLightPrimary,
    surface = IndustrialNavyCard,
    onSurface = TextLightPrimary,
    surfaceVariant = IndustrialNavySurface,
    onSurfaceVariant = SteelGreyLight,
    outline = IndustrialNavyBorder,
    error = DangerRed
)

private val LightColorScheme = lightColorScheme(
    primary = IndustrialNavy,
    onPrimary = PureWhite,
    primaryContainer = SkyBlueLight,
    onPrimaryContainer = IndustrialNavy,
    secondary = ElectricBlueAccent,
    onSecondary = PureWhite,
    secondaryContainer = SteelGreySurface,
    onSecondaryContainer = SteelGreyDark,
    tertiary = MachineGreen,
    onTertiary = PureWhite,
    background = SteelGreySurface,
    onBackground = TextDarkPrimary,
    surface = PureWhite,
    onSurface = TextDarkPrimary,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = TextDarkSecondary,
    outline = SteelGreySubtle,
    error = DangerRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
