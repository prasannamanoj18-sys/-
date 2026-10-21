package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * 5S Pillar specification and practical guidance for apparel technicians.
 */
data class FiveSPillar(
    val number: Int,
    val japaneseRomaji: String,
    val japaneseKanji: String,
    val sinhalaName: String,
    val englishName: String,
    val subtitleSinhala: String,
    val subtitleEnglish: String,
    val sewingMachineFocusSinhala: String,
    val sewingMachineFocusEnglish: String,
    val keyPointsSinhala: List<String>,
    val keyPointsEnglish: List<String>,
    val dosSinhala: List<String>,
    val dontsSinhala: List<String>,
    val accentColorHex: Long
)

/**
 * Interactive checklist item for daily/shift 5S routine.
 */
@JsonClass(generateAdapter = true)
data class FiveSChecklistItem(
    @Json(name = "id") val id: String,
    @Json(name = "pillarNumber") val pillarNumber: Int,
    @Json(name = "titleSinhala") val titleSinhala: String,
    @Json(name = "titleEnglish") val titleEnglish: String,
    @Json(name = "descriptionSinhala") val descriptionSinhala: String,
    @Json(name = "descriptionEnglish") val descriptionEnglish: String,
    @Json(name = "area") val area: String,
    @Json(name = "isCompleted") val isCompleted: Boolean = false
)

/**
 * Red Tag Item (රතු ටැගය) - Essential for Step 1: Seiri (Sort).
 */
@JsonClass(generateAdapter = true)
data class RedTagItem(
    @Json(name = "id") val id: String,
    @Json(name = "tagNumber") val tagNumber: String,
    @Json(name = "itemName") val itemName: String,
    @Json(name = "machineModel") val machineModel: String,
    @Json(name = "category") val category: String, // "PARTS", "TOOLS", "MATERIAL", "ATTACHMENTS"
    @Json(name = "reason") val reason: String, // "DAMAGED", "EXCESS", "OBSOLETE", "UNIDENTIFIED"
    @Json(name = "action") val action: String, // "SCRAP", "REPAIR", "STORE", "RECYCLE"
    @Json(name = "location") val location: String,
    @Json(name = "taggedBy") val taggedBy: String,
    @Json(name = "dateTagged") val dateTagged: String,
    @Json(name = "isResolved") val isResolved: Boolean = false
)

/**
 * 5S Audit Scorecard item.
 */
@JsonClass(generateAdapter = true)
data class FiveSAuditRecord(
    @Json(name = "id") val id: String,
    @Json(name = "date") val date: String,
    @Json(name = "auditorName") val auditorName: String,
    @Json(name = "machineLine") val machineLine: String,
    @Json(name = "sortScore") val sortScore: Int, // 1..5
    @Json(name = "setInOrderScore") val setInOrderScore: Int, // 1..5
    @Json(name = "shineScore") val shineScore: Int, // 1..5
    @Json(name = "standardizeScore") val standardizeScore: Int, // 1..5
    @Json(name = "sustainScore") val sustainScore: Int, // 1..5
    @Json(name = "notes") val notes: String = ""
) {
    val totalScore: Int get() = sortScore + setInOrderScore + shineScore + standardizeScore + sustainScore
    val percentage: Int get() = ((totalScore.toFloat() / 25f) * 100).toInt()

    val grade: String
        get() = when {
            percentage >= 90 -> "A (විශිෂ්ටයි - Excellent)"
            percentage >= 75 -> "B (යහපත් - Good)"
            percentage >= 60 -> "C (සාමාන්‍ය - Average)"
            else -> "D (වැඩිදියුණු විය යුතුයි - Action Needed)"
        }
}
