package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.FiveSAuditRecord
import com.example.data.model.FiveSChecklistItem
import com.example.data.model.FiveSPillar
import com.example.data.model.RedTagItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class FiveSRepository private constructor(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("fives_prefs", Context.MODE_PRIVATE)

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val checklistType = Types.newParameterizedType(List::class.java, FiveSChecklistItem::class.java)
    private val checklistAdapter = moshi.adapter<List<FiveSChecklistItem>>(checklistType)

    private val redTagType = Types.newParameterizedType(List::class.java, RedTagItem::class.java)
    private val redTagAdapter = moshi.adapter<List<RedTagItem>>(redTagType)

    private val auditType = Types.newParameterizedType(List::class.java, FiveSAuditRecord::class.java)
    private val auditAdapter = moshi.adapter<List<FiveSAuditRecord>>(auditType)

    // In-memory state flows
    private val _checklist = MutableStateFlow<List<FiveSChecklistItem>>(emptyList())
    val checklist: StateFlow<List<FiveSChecklistItem>> = _checklist.asStateFlow()

    private val _redTags = MutableStateFlow<List<RedTagItem>>(emptyList())
    val redTags: StateFlow<List<RedTagItem>> = _redTags.asStateFlow()

    private val _auditRecords = MutableStateFlow<List<FiveSAuditRecord>>(emptyList())
    val auditRecords: StateFlow<List<FiveSAuditRecord>> = _auditRecords.asStateFlow()

    init {
        loadChecklist()
        loadRedTags()
        loadAudits()
    }

    companion object {
        @Volatile
        private var instance: FiveSRepository? = null

        fun getInstance(context: Context): FiveSRepository {
            return instance ?: synchronized(this) {
                instance ?: FiveSRepository(context.applicationContext).also { instance = it }
            }
        }

        /**
         * Comprehensive 5S Pillars data for apparel & sewing technicians.
         */
        val FIVE_S_PILLARS = listOf(
            FiveSPillar(
                number = 1,
                japaneseRomaji = "Seiri (整理)",
                japaneseKanji = "整理",
                sinhalaName = "සෝදිසිය / වර්ග කිරීම (Sort)",
                englishName = "Sort (Seiri)",
                subtitleSinhala = "අවශ්‍ය දේ පමණක් තබා අනවශ්‍ය සියලු දේ වැඩබිමෙන් ඉවත් කිරීම",
                subtitleEnglish = "Distinguish between necessary and unnecessary items and eliminate the latter",
                sewingMachineFocusSinhala = "මැෂින් මේසය මත අනවශ්‍ය කැඩුණු ඉඳිකටු, ගෙවුණු ලූපර්/හුක්, පරණ බෙල්ට්, නූල් කැබලි ඉවත් කරන්න. රතු ටැග් (Red Tag) ක්‍රමය භාවිතයෙන් අබලන් අමතර කොටස් වෙන් කරන්න.",
                sewingMachineFocusEnglish = "Clear away broken needles, worn-out loopers, old belts, and thread debris. Use the Red Tag method to isolate defective parts.",
                keyPointsSinhala = listOf(
                    "රතු ටැග් (Red Tag) ක්‍රමය මඟින් අබලන් අමතර කොටස් වෙන් කිරීම",
                    "දිනපතා අවශ්‍ය නොවන උපකරණ මැෂින් මේසයෙන් ඉවත් කිරීම",
                    "භාවිතයට නුසුදුසු නූල් බෝබින් සහ හිස් ඔයිල් කෑන් ඉවත් කිරීම"
                ),
                keyPointsEnglish = listOf(
                    "Apply Red Tagging to identify defective machine components",
                    "Remove tools and parts not needed for current production shift",
                    "Discard bent needles, damaged bobbins, and empty lubricant cans"
                ),
                dosSinhala = listOf(
                    "කැඩුණු ඉඳිකටු නියමිත ආරක්ෂිත කසළ බඳුනට දමන්න",
                    "අනවශ්‍ය බඩු සඳහා Red Tag දමා තාවකාලික රතු කලාපයක තබන්න"
                ),
                dontsSinhala = listOf(
                    "කැඩුණු හෝ ගෙවුණු කොටස් මැෂින් ඩ්‍රෝවරයේ ගොඩගසා තබන්න එපා",
                    "නූල් කැබලි බිමට හෝ මැෂින් මෝටරය තුලට වැටෙන්නට හරින්න එපා"
                ),
                accentColorHex = 0xFFEF4444 // Red accent for Seiri / Red Tag
            ),
            FiveSPillar(
                number = 2,
                japaneseRomaji = "Seiton (整頓)",
                japaneseKanji = "整頓",
                sinhalaName = "පිළිවෙල / සකස් කිරීම (Set In Order)",
                englishName = "Set In Order (Seiton)",
                subtitleSinhala = "සෑම දෙයකටම නියමිත තැනක් සහ සෑම දෙයක්ම නියමිත තැන තැබීම",
                subtitleEnglish = "A place for everything, and everything in its proper place",
                sewingMachineFocusSinhala = "යන්ත්‍ර මෙවලම් පුවරුව (Tool Shadow Board) භාවිතා කරන්න. ඇලන් කී, ස්කුරුප්පු නියන, කතුරු සහ ඔයිලර් සඳහා වර්ණ කේත (Color Coding) සහ නියමිත හැඩතල සලකුණු කරන්න.",
                sewingMachineFocusEnglish = "Implement Tool Shadow Boards. Label slots for allen keys, screwdrivers, tweezers, and oil cans for 5-second retrieval.",
                keyPointsSinhala = listOf(
                    "තත්පර 30ක් ඇතුලත ඕනෑම මෙවලමක් ගැනීමට හැකි වන සේ පිළියෙල කිරීම",
                    "ඉඳිකටු පෙට්ටි (DBx1, DPx5, DCx27) පැහැදිලිව වර්ගීකරණය කිරීම",
                    "නිතර භාවිතාවන මෙවලම් පහසුවෙන් අතපසුවන දුරින් තැබීම"
                ),
                keyPointsEnglish = listOf(
                    "Retrieve any technician tool in under 30 seconds",
                    "Organize needle packets by system code and thickness",
                    "Keep high-frequency tools closest to technician working area"
                ),
                dosSinhala = listOf(
                    "මෙවලම් ගත් පසු නැවත නියමිත Shadow Board තැනටම තබන්න",
                    "ප්‍රමාණ (10mm, 14mm) පැහැදිලිව ලේබල් කරන්න"
                ),
                dontsSinhala = listOf(
                    "මෙවලම් මැෂින් බෙඩ් එක උඩ හෝ මේසය මත අවුල් සහගතව තබන්න එපා"
                ),
                accentColorHex = 0xFF3B82F6 // Blue accent
            ),
            FiveSPillar(
                number = 3,
                japaneseRomaji = "Seiso (清掃)",
                japaneseKanji = "清掃",
                sinhalaName = "පිරිසිදු කිරීම සහ පරීක්ෂාව (Shine & Inspect)",
                englishName = "Shine & Inspect (Seiso)",
                subtitleSinhala = "පිරිසිදු කිරීම යනු පරීක්ෂා කිරීමකි (Cleaning is Inspection)",
                subtitleEnglish = "Clean your workplace thoroughly while inspecting for abnormalities",
                sewingMachineFocusSinhala = "ෆීඩ් ඩෝග් සහ රොටරි හුක් තුල රැඳෙන රෙදි කෙඳි (Lint) බ්ලෝවර් හෝ බුරුසුවෙන් ඉවත් කරන්න. පිරිසිදු කරන අතරතුර ලිහිල් වූ ස්කුරුප්පු, තෙල් කාන්දුවීම් සහ අසාමාන්‍ය ශබ්ද පරීක්ෂා කරන්න.",
                sewingMachineFocusEnglish = "Blow out lint around feed dogs and hooks daily. Inspect for loose screws, oil leakages, and abnormal vibrations during cleaning.",
                keyPointsSinhala = listOf(
                    "දිනපතා Shift අවසානයේ මැෂින් ෆීඩ් ඩෝග් සහ හුක් පිරිසිදු කිරීම",
                    "තෙල් මට්ටම (Oil Gauge) සහ තෙල් කාන්දු පරික්ෂා කිරීම",
                    "මෝටර් ෆෑන් එකේ නූල් පැටලී ඇත්දැයි බැලීම"
                ),
                keyPointsEnglish = listOf(
                    "Blow out hook area and feed dog lint at shift end",
                    "Inspect oil reservoir, sight glass, and check for leaks",
                    "Check servo motor fan to ensure no thread entanglement"
                ),
                dosSinhala = listOf(
                    "සුදුසු පිරිසිදු බුරුසුවක් හෝ සීමිත වායු පීඩනයක් භාවිතා කරන්න",
                    "පිරිසිදු කරන විට සියලු ලිහිල් බෝල්ට් පරීක්ෂා කරන්න"
                ),
                dontsSinhala = listOf(
                    "බලවත් වායු පීඩනයෙන් දූවිලි බෙයාරින් තුලට ගසන්න එපා"
                ),
                accentColorHex = 0xFF10B981 // Green accent
            ),
            FiveSPillar(
                number = 4,
                japaneseRomaji = "Seiketsu (清潔)",
                japaneseKanji = "清潔",
                sinhalaName = "ප්‍රමිතිකරණය (Standardize)",
                englishName = "Standardize (Seiketsu)",
                subtitleSinhala = "පළමු S 3 නීති සහ ප්‍රමිතිගත ක්‍රමවේද (SOP) බවට පත් කිරීම",
                subtitleEnglish = "Establish standards and schedules to maintain the first 3S practices",
                sewingMachineFocusSinhala = "මැෂින් නඩත්තු චෙක්ලිස්ට් (Maintenance Checklist), ටෙන්ෂන් ප්‍රමිති SOP සහ දෛනික තෙල් දැමීමේ වර්ණ පද්ධතියක් (Color coded lubrication schedule) සාමාන්‍යකරණය කරන්න.",
                sewingMachineFocusEnglish = "Formulate Standard Operating Procedures (SOP), visual lubrication charts, and clear PM schedules across all factory lines.",
                keyPointsSinhala = listOf(
                    "දෛනික/සතිපතා 5S චෙක්ලිස්ට් ප්‍රදර්ශනය කර පවත්වා ගැනීම",
                    "යන්ත්‍රයේ නිවැරදි තෙල් වර්ගය (No. 10 / No. 7) පැහැදිලිව නම් කිරීම",
                    "සියලු මැෂින් මේස එකම උස සහ පිහිටුමකට සම්මත කිරීම"
                ),
                keyPointsEnglish = listOf(
                    "Display visible 5S checklists and daily shift tasks",
                    "Clearly specify ISO grade 10/7 oil for high-speed machines",
                    "Standardize workspace ergonomics and machine table settings"
                ),
                dosSinhala = listOf(
                    "පැහැදිලි ඡායාරූප සහිත SOP කාඩ්පත් මැෂින් අසල අලවන්න",
                    "සියලු කාර්මිකයන්ට එකම ප්‍රමිතියක් හඳුන්වා දෙන්න"
                ),
                dontsSinhala = listOf(
                    "සම්මත නීති නොමැතිව කාර්මිකයන්ට හිතුමතේ ක්‍රියා කිරීමට ඉඩ දීම"
                ),
                accentColorHex = 0xFFF59E0B // Amber accent
            ),
            FiveSPillar(
                number = 5,
                japaneseRomaji = "Shitsuke (躾)",
                japaneseKanji = "躾",
                sinhalaName = "විනය සහ පවත්වා ගැනීම (Sustain)",
                englishName = "Sustain (Shitsuke)",
                subtitleSinhala = "5S නීති ස්වයං විනයක් සහ පුරුද්දක් බවට පත් කර නිරන්තරයෙන් රැකගැනීම",
                subtitleEnglish = "Instill self-discipline and habits to adhere to 5S rules continuously",
                sewingMachineFocusSinhala = "දිනපතා වැඩ මුරය අරඹන විට මිනිත්තු 5ක 5S පුරුද්දක් ඇති කරන්න. සතිපතා 5S විගණනයක් (5S Audit Scorecard) සිදුකර ඇඟලුම් පෙලපාලි (Lines) අතර කුසලතා අගයන්න.",
                sewingMachineFocusEnglish = "Execute a daily 5-minute 5S routine. Conduct weekly 5S audits with recognition for top-performing lines and mechanics.",
                keyPointsSinhala = listOf(
                    "දිනපතා මිනිත්තු 5ක 5S කෙටි පුරුද්ද (Daily 5-Min 5S Routine)",
                    "සතිපතා 5S විගණනය සහ ලකුණු ප්‍රදර්ශනය",
                    "කයිසෙන් (Kaizen) ක්‍රමය ඔස්සේ නිරන්තර වැඩිදියුණු කිරීම"
                ),
                keyPointsEnglish = listOf(
                    "Practice the 5-Minute 5S routine before and after each shift",
                    "Conduct weekly line audits and post scorecards",
                    "Encourage continuous improvement ideas (Kaizen) from technicians"
                ),
                dosSinhala = listOf(
                    "5S යනු එක් දිනක වැඩක් නොව දෛනික ජීවන පුරුද්දක් ලෙස දකින්න",
                    "නව කාර්මිකයින්ට මුල සිටම 5S විනය පුහුණු කරන්න"
                ),
                dontsSinhala = listOf(
                    "විගණනයක් ඇති විට පමණක් පිරිසිදු කර පසුව අමතක කර දැමීම"
                ),
                accentColorHex = 0xFF8B5CF6 // Purple accent
            )
        )
    }

    private fun loadChecklist() {
        val json = prefs.getString("fives_checklist_json", null)
        if (json != null) {
            try {
                val list = checklistAdapter.fromJson(json)
                if (!list.isNullOrEmpty()) {
                    _checklist.value = list
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Seed default daily sewing 5S checklist
        _checklist.value = getInitialChecklist()
        saveChecklist(_checklist.value)
    }

    private fun saveChecklist(items: List<FiveSChecklistItem>) {
        try {
            val json = checklistAdapter.toJson(items)
            prefs.edit().putString("fives_checklist_json", json).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleChecklistItem(id: String) {
        val updated = _checklist.value.map { item ->
            if (item.id == id) {
                item.copy(isCompleted = !item.isCompleted)
            } else {
                item
            }
        }
        _checklist.value = updated
        saveChecklist(updated)
    }

    fun resetChecklist() {
        val reset = _checklist.value.map { it.copy(isCompleted = false) }
        _checklist.value = reset
        saveChecklist(reset)
    }

    private fun loadRedTags() {
        val json = prefs.getString("fives_red_tags_json", null)
        if (json != null) {
            try {
                val list = redTagAdapter.fromJson(json)
                if (list != null) {
                    _redTags.value = list
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        // Seed initial example red tags
        val sample = listOf(
            RedTagItem(
                id = UUID.randomUUID().toString(),
                tagNumber = "RT-101",
                itemName = "Worn Rotary Hook (ගෙවුණු හුක්)",
                machineModel = "Juki DDL-8700",
                category = "PARTS",
                reason = "DAMAGED",
                action = "SCRAP",
                location = "Line 04 - Machine #12",
                taggedBy = "Mechanic Silva",
                dateTagged = "2026-10-18",
                isResolved = false
            ),
            RedTagItem(
                id = UUID.randomUUID().toString(),
                tagNumber = "RT-102",
                itemName = "Bent Overlock Looper (ඇදවූ ලූපර්)",
                machineModel = "Pegasus M900",
                category = "PARTS",
                reason = "DAMAGED",
                action = "REPAIR",
                location = "Line 02 - Overlock #05",
                taggedBy = "Tech Bandara",
                dateTagged = "2026-10-19",
                isResolved = true
            ),
            RedTagItem(
                id = UUID.randomUUID().toString(),
                tagNumber = "RT-103",
                itemName = "Excess V-Belts (අමතර බෙල්ට් 5ක්)",
                machineModel = "Siruba 747K",
                category = "PARTS",
                reason = "EXCESS",
                action = "STORE",
                location = "Tool Cabinet Shelf B",
                taggedBy = "Supervisor Kamal",
                dateTagged = "2026-10-20",
                isResolved = false
            )
        )
        _redTags.value = sample
        saveRedTags(sample)
    }

    private fun saveRedTags(items: List<RedTagItem>) {
        try {
            val json = redTagAdapter.toJson(items)
            prefs.edit().putString("fives_red_tags_json", json).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addRedTag(
        itemName: String,
        machineModel: String,
        category: String,
        reason: String,
        action: String,
        location: String,
        taggedBy: String
    ) {
        val nextNumber = "RT-${100 + _redTags.value.size + 1}"
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val newItem = RedTagItem(
            id = UUID.randomUUID().toString(),
            tagNumber = nextNumber,
            itemName = itemName.trim(),
            machineModel = machineModel.trim(),
            category = category,
            reason = reason,
            action = action,
            location = location.trim(),
            taggedBy = taggedBy.trim().ifEmpty { "Technician" },
            dateTagged = today,
            isResolved = false
        )
        val updated = listOf(newItem) + _redTags.value
        _redTags.value = updated
        saveRedTags(updated)
    }

    fun toggleRedTagResolved(id: String) {
        val updated = _redTags.value.map { tag ->
            if (tag.id == id) tag.copy(isResolved = !tag.isResolved) else tag
        }
        _redTags.value = updated
        saveRedTags(updated)
    }

    fun deleteRedTag(id: String) {
        val updated = _redTags.value.filter { it.id != id }
        _redTags.value = updated
        saveRedTags(updated)
    }

    private fun loadAudits() {
        val json = prefs.getString("fives_audits_json", null)
        if (json != null) {
            try {
                val list = auditAdapter.fromJson(json)
                if (list != null) {
                    _auditRecords.value = list
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val sampleAudits = listOf(
            FiveSAuditRecord(
                id = UUID.randomUUID().toString(),
                date = "2026-10-15",
                auditorName = "Senior Tech Perera",
                machineLine = "Line A (Single Needle)",
                sortScore = 5,
                setInOrderScore = 4,
                shineScore = 5,
                standardizeScore = 4,
                sustainScore = 4,
                notes = "All shadow boards populated. Good oil level maintenance."
            )
        )
        _auditRecords.value = sampleAudits
        saveAudits(sampleAudits)
    }

    private fun saveAudits(items: List<FiveSAuditRecord>) {
        try {
            val json = auditAdapter.toJson(items)
            prefs.edit().putString("fives_audits_json", json).apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveAudit(
        auditorName: String,
        machineLine: String,
        sortScore: Int,
        setInOrderScore: Int,
        shineScore: Int,
        standardizeScore: Int,
        sustainScore: Int,
        notes: String
    ) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val newRecord = FiveSAuditRecord(
            id = UUID.randomUUID().toString(),
            date = today,
            auditorName = auditorName.trim().ifEmpty { "Auditor" },
            machineLine = machineLine.trim().ifEmpty { "Production Floor" },
            sortScore = sortScore,
            setInOrderScore = setInOrderScore,
            shineScore = shineScore,
            standardizeScore = standardizeScore,
            sustainScore = sustainScore,
            notes = notes.trim()
        )
        val updated = listOf(newRecord) + _auditRecords.value
        _auditRecords.value = updated
        saveAudits(updated)
    }

    private fun getInitialChecklist(): List<FiveSChecklistItem> {
        return listOf(
            FiveSChecklistItem(
                id = "ck_1",
                pillarNumber = 1,
                titleSinhala = "අනවශ්‍ය සහ කැඩුණු අමතර කොටස් ඉවත් කිරීම",
                titleEnglish = "Sort: Discard broken/worn needles & parts",
                descriptionSinhala = "මැෂින් මේසය මත ඇති කැඩුණු ඉඳිකටු, ගෙවුණු හුක්, ලූපර් සහ නූල් කැබලි Red Tag කොට ඉවත් කර ඇත.",
                descriptionEnglish = "Broken needles, defective hooks, and waste thread clipped away from the sewing bed.",
                area = "MACHINE_BED"
            ),
            FiveSChecklistItem(
                id = "ck_2",
                pillarNumber = 1,
                titleSinhala = "හිස් ඔයිල් කෑන් හා අපද්‍රව්‍ය බැහැර කිරීම",
                titleEnglish = "Sort: Clear empty cans & waste fabric",
                descriptionSinhala = "හිස් ලිහිසි තෙල් බෝතල් සහ පරණ කපු රෙදි කැබලි ආරක්ෂිතව කසල බඳුනට දමා ඇත.",
                descriptionEnglish = "Empty oil containers and scrap fabric discarded into designated bins.",
                area = "WORKBENCH"
            ),
            FiveSChecklistItem(
                id = "ck_3",
                pillarNumber = 2,
                titleSinhala = "මෙවලම් Shadow Board එකේ නියමිත තැන තැබීම",
                titleEnglish = "Set In Order: Tools arranged in Shadow Board",
                descriptionSinhala = "ඇලන් කී (Allen keys), ස්කුරුප්පු නියන, සහ ට්වීසර් ඒ ඒ නියමිත ස්ථාන වල පිළිවෙලට තබා ඇත.",
                descriptionEnglish = "Screwdrivers, allen wrenches, and tweezers returned to labeled shadow board slots.",
                area = "TOOL_BOARD"
            ),
            FiveSChecklistItem(
                id = "ck_4",
                pillarNumber = 2,
                titleSinhala = "ඉඳිකටු පෙට්ටි වර්ගය සහ අංකය අනුව පෙලගැස්වීම",
                titleEnglish = "Set In Order: Needles organized by size & system",
                descriptionSinhala = "DBx1, DPx5 ඉඳිකටු ප්‍රමාණය අනුව (11, 14, 16) පිළිවෙලට පෙලගස්වා ඇත.",
                descriptionEnglish = "Needle packets organized by system code and thickness for instant access.",
                area = "STORAGE"
            ),
            FiveSChecklistItem(
                id = "ck_5",
                pillarNumber = 3,
                titleSinhala = "ෆීඩ් ඩෝග් සහ හුක් ප්‍රදේශයේ කෙඳි (Lint) පිසදැමීම",
                titleEnglish = "Shine: Clean lint from feed dog & hook area",
                descriptionSinhala = "ඉඳිකටු තහඩුව යට සහ රොටරි හුක් ආවරණයේ ඇති කෙඳි හා දූවිලි බුරුසුවකින් පිරිසිදු කර ඇත.",
                descriptionEnglish = "Lint removed from under needle plate, feed dogs, and rotary hook area.",
                area = "SEWING_MECHANISM"
            ),
            FiveSChecklistItem(
                id = "ck_6",
                pillarNumber = 3,
                titleSinhala = "මැෂින් තෙල් මට්ටම හා කාන්දුවීම් පරීක්ෂාව",
                titleEnglish = "Shine: Check oil sight glass and leakages",
                descriptionSinhala = "ඔයිල් සයිට් ග්ලාස් (Oil sight glass) එකෙන් තෙල් ගලායාම සහ ලිහිසි මට්ටම පරීක්ෂා කර ඇත.",
                descriptionEnglish = "Oil sight window inspected for pump circulation; no oil dripping on floor.",
                area = "LUBRICATION"
            ),
            FiveSChecklistItem(
                id = "ck_7",
                pillarNumber = 4,
                titleSinhala = "දෛනික ලිහිසි තෙල් කාලසටහන සටහන් කිරීම",
                titleEnglish = "Standardize: Update daily lubrication log",
                descriptionSinhala = "දෛනික තෙල් දැමීමේ සටහන් පත්‍රිකාව (Lubrication Chart) යාවත්කාලීන කර ඇත.",
                descriptionEnglish = "Daily lubrication log updated and initialed per standard operating procedure.",
                area = "STANDARDS"
            ),
            FiveSChecklistItem(
                id = "ck_8",
                pillarNumber = 4,
                titleSinhala = "ටෙන්ෂන් හා වේග සීමා SOP ප්‍රදර්ශනය",
                titleEnglish = "Standardize: Machine SOP cards visible",
                descriptionSinhala = "නූල් ටෙන්ෂන් සහ යන්ත්‍ර ක්‍රියාකාරී පිරිවිතර පත්‍රිකාව (SOP) පැහැදිලිව පෙනෙන සේ ඇත.",
                descriptionEnglish = "Tension reference chart and machine specification sheet visibly posted.",
                area = "STANDARDS"
            ),
            FiveSChecklistItem(
                id = "ck_9",
                pillarNumber = 5,
                titleSinhala = "දිනපතා 5-Minute 5S පුරුද්ද සම්පූර්ණ කිරීම",
                titleEnglish = "Sustain: Completed 5-minute shift-end 5S routine",
                descriptionSinhala = "වැඩමුරය අවසානයේ නියමිත මිනිත්තු 5ක පිරිසිදු කිරීම සහ උපකරණ ගණන් කිරීම කර ඇත.",
                descriptionEnglish = "5-minute tidy-up and tool inventory completed before leaving workstation.",
                area = "HABIT"
            ),
            FiveSChecklistItem(
                id = "ck_10",
                pillarNumber = 5,
                titleSinhala = "සතිපතා 5S ලකුණු විගණනය සම්පූර්ණ කිරීම",
                titleEnglish = "Sustain: Weekly 5S line audit completed",
                descriptionSinhala = "යන්ත්‍ර පේළියේ 5S ලකුණු පුවරුව පරීක්ෂා කර ලකුණු ලබා දී ඇත.",
                descriptionEnglish = "Weekly 5S line audit recorded and scorecard displayed on line board.",
                area = "HABIT"
            )
        )
    }
}
