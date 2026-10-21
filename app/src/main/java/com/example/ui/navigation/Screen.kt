package com.example.ui.navigation

sealed class Screen {
    data object Home : Screen()
    data class KnowledgeList(
        val categoryFilter: String = "ALL",
        val machineFilter: String = "ALL",
        val searchQuery: String = ""
    ) : Screen()
    data class ArticleDetail(val articleId: Long) : Screen()
    data object MeasuringUnits : Screen() // Dedicated page for "මිනුම් උපකරණ" / Measuring Units
    data object QrScanner : Screen()
    data object DriveFiles : Screen()
    data object FiveS : Screen() // Dedicated 5S Process (5S ක්‍රියාවලිය) screen
    data object ErrorCodes : Screen()
    data object Maintenance : Screen()
    data object Calculators : Screen()
    data object Membership : Screen()
    data object Admin : Screen()
    data object Settings : Screen()
}
