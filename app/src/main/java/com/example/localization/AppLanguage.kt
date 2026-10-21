package com.example.localization

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val localizedLabel: String
) {
    SINHALA(
        code = "si",
        nativeName = "සිංහල",
        localizedLabel = "භාෂාව"
    ),
    ENGLISH(
        code = "en",
        nativeName = "English",
        localizedLabel = "Language"
    ),
    TAMIL(
        code = "ta",
        nativeName = "தமிழ்",
        localizedLabel = "மொழி"
    );

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: SINHALA
        }
    }
}
