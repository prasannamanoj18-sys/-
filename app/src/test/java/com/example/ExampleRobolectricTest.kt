package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.InitialData
import com.example.data.model.TechnicalArticle
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("කාර්මික දැනුම", appName)
    }

    @Test
    fun `verify multi language locale strings`() {
        // Sinhala
        assertEquals("කාර්මික දැනුම", LocaleStrings.get(LocaleStrings.APP_NAME, AppLanguage.SINHALA))
        assertEquals("භාෂාව / Language / மொழி", LocaleStrings.get(LocaleStrings.LANGUAGE_SELECTOR_TITLE, AppLanguage.SINHALA))

        // English
        assertEquals("Sewing Machine Technical Knowledge", LocaleStrings.get(LocaleStrings.APP_NAME, AppLanguage.ENGLISH))

        // Tamil
        assertEquals("தொழில்நுட்ப அறிவு", LocaleStrings.get(LocaleStrings.APP_NAME, AppLanguage.TAMIL))
    }

    @Test
    fun `verify trilingual article resolution`() {
        val article = TechnicalArticle(
            id = 1,
            category = "TIMING",
            machineType = "General",
            difficulty = "Intermediate",
            titleSinhala = "රොටරි හුක් ටයිමිං",
            titleEnglish = "Rotary Hook Timing",
            titleTamil = "ரோட்டரி ஹூக் டைமிங்",
            summarySinhala = "සාරාංශය",
            summaryEnglish = "Summary",
            summaryTamil = "சுருக்கம்",
            contentSinhala = "පියවර",
            contentEnglish = "Steps",
            contentTamil = "படிகள்",
            keySpecsOrTiming = "0.05mm - 0.1mm"
        )

        assertEquals("රොටරි හුක් ටයිමිං", article.getTitle(AppLanguage.SINHALA))
        assertEquals("Rotary Hook Timing", article.getTitle(AppLanguage.ENGLISH))
        assertEquals("ரோட்டரி ஹூக் டைமிங்", article.getTitle(AppLanguage.TAMIL))
    }

    @Test
    fun `verify no blank articles in default dataset`() {
        val articles = InitialData.getDefaultArticles()
        assertTrue("Articles list must not be empty", articles.isNotEmpty())
        for (a in articles) {
            assertTrue("Article titleSinhala must not be blank", a.titleSinhala.isNotBlank())
            assertTrue("Article titleEnglish must not be blank", a.titleEnglish.isNotBlank())
        }
        val measuringArticle = articles.firstOrNull { it.id == 7L }
        assertNotNull("Measuring units article must exist", measuringArticle)
        assertTrue(measuringArticle!!.titleSinhala.contains("දිග මනින මිනුම් ඒකක"))
    }

    @Test
    fun `verify Sinhala button named QR කේතය Scan කරන්න and QR strings`() {
        // Requirement 1: Sinhala button named "QR කේතය Scan කරන්න"
        val sinhalaBtn = LocaleStrings.get(LocaleStrings.BTN_QR_SCAN, AppLanguage.SINHALA)
        assertEquals("QR කේතය Scan කරන්න", sinhalaBtn)

        // Requirement 6: Display QR result in Sinhala
        val sinhalaResultTitle = LocaleStrings.get(LocaleStrings.QR_RESULT_TITLE, AppLanguage.SINHALA)
        assertEquals("ස්කෑන් කරන ලද QR ප්‍රතිඵලය", sinhalaResultTitle)

        val sinhalaInstruction = LocaleStrings.get(LocaleStrings.QR_INSTRUCTION, AppLanguage.SINHALA)
        assertTrue(sinhalaInstruction.contains("QR කේතය"))

        // Requirement 7: Confirmation before opening link
        val confirmOpen = LocaleStrings.get(LocaleStrings.QR_CONFIRM_OPEN, AppLanguage.SINHALA)
        assertTrue(confirmOpen.contains("තහවුරු කරන්න"))

        // Requirement 8: Manual entry option
        val manualTitle = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, AppLanguage.SINHALA)
        assertEquals("කේතය හෝ ලින්ක් එක අතින් ඇතුළත් කරන්න", manualTitle)

        // Requirement 10: Camera error fallback message
        val cameraError = LocaleStrings.get(LocaleStrings.QR_CAMERA_ERROR, AppLanguage.SINHALA)
        assertTrue(cameraError.contains("කැමරාව ආරම්භ කිරීමට නොහැකි විය"))
    }

    @Test
    fun `verify QR link validation and model parsing logic`() {
        val testUrl1 = "https://example.com/manuals/juki-ddl8700.pdf"
        val testUrl2 = "http://garment-factory.internal/specs/timing.pdf"
        val nonUrl = "Juki DDL-8700"

        fun isSafeUrl(text: String): Boolean {
            val t = text.trim()
            return t.startsWith("http://", ignoreCase = true) || t.startsWith("https://", ignoreCase = true)
        }

        assertTrue("https URL must be identified as URL", isSafeUrl(testUrl1))
        assertTrue("http URL must be identified as URL", isSafeUrl(testUrl2))
        assertTrue("Model name should not be identified as web URL", !isSafeUrl(nonUrl))
    }

    @Test
    fun `verify Screen navigation supports QrScanner`() {
        val screen: com.example.ui.navigation.Screen = com.example.ui.navigation.Screen.QrScanner
        assertTrue(screen is com.example.ui.navigation.Screen.QrScanner)
    }

    @Test
    fun `verify Google Drive File Item and URLs`() {
        val file = com.example.data.model.DriveFileItem(
            id = "1abc123xyz",
            name = "Juki DDL-8700 Service Manual.pdf",
            mimeType = "application/pdf",
            sizeBytes = 5242880L,
            webViewLink = "https://drive.google.com/file/d/1abc123xyz/view?usp=sharing",
            machineModel = "Juki DDL-8700"
        )

        assertTrue("File should be recognized as PDF", file.isPdf())
        assertTrue("File should not be recognized as video", !file.isVideo())
        assertEquals("5.0 MB", file.getFormattedSize())
        assertEquals("https://drive.google.com/file/d/1abc123xyz/view?usp=sharing", file.getEffectiveViewUrl())
        assertTrue(file.getEffectiveDownloadUrl().contains("1abc123xyz"))
    }

    @Test
    fun `verify Google Drive repository seeded technical documents`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = com.example.data.repository.GoogleDriveRepository.getInstance(context)
        val files = repo.driveFiles.first()

        assertTrue("Seeded Drive files should not be empty", files.isNotEmpty())
        assertTrue("Must contain Juki DDL-8700 manual", files.any { it.name.contains("8700") })
        assertTrue("Must contain Pegasus M900 manual", files.any { it.name.contains("Pegasus") })

        // Test adding newly uploaded file
        repo.addUploadedFile(
            name = "Jack A4 Direct-Drive Guide.pdf",
            driveLinkOrId = "https://drive.google.com/file/d/jack_a4_manual_id/view",
            category = "MANUAL",
            machineModel = "Jack A4",
            description = "Jack direct drive manual"
        )

        val updatedFiles = repo.driveFiles.first()
        assertTrue(updatedFiles.any { it.name.contains("Jack A4") })
    }

    @Test
    fun `verify Google Drive locale strings in Sinhala`() {
        val title = LocaleStrings.get(LocaleStrings.DRIVE_FILES_TITLE, AppLanguage.SINHALA)
        assertTrue("Title should contain Drive and ලේඛන", title.contains("Google Drive") && title.contains("ලේඛන"))

        val fetchBtn = LocaleStrings.get(LocaleStrings.DRIVE_FETCH_BUTTON, AppLanguage.SINHALA)
        assertEquals("Google Drive එකෙන් Fetch කරන්න", fetchBtn)
    }

    @Test
    fun `verify Screen navigation supports DriveFiles`() {
        val screen: com.example.ui.navigation.Screen = com.example.ui.navigation.Screen.DriveFiles
        assertTrue(screen is com.example.ui.navigation.Screen.DriveFiles)
    }

    @Test
    fun `verify App Live Web URL format and scheme resolution`() {
        val appUrl = "https://ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app"
        val parsedUri = android.net.Uri.parse(appUrl)
        assertEquals("https", parsedUri.scheme)
        assertEquals("ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app", parsedUri.host)

        fun resolveUrl(input: String): String {
            return if (!input.startsWith("http://", ignoreCase = true) && !input.startsWith("https://", ignoreCase = true)) {
                "https://$input"
            } else {
                input
            }
        }

        assertEquals(appUrl, resolveUrl(appUrl))
        assertEquals(appUrl, resolveUrl("ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app"))
    }

    @Test
    fun `verify Screen navigation supports FiveS`() {
        val screen: com.example.ui.navigation.Screen = com.example.ui.navigation.Screen.FiveS
        assertTrue(screen is com.example.ui.navigation.Screen.FiveS)
    }

    @Test
    fun `verify 5S Pillars data contains all 5 Japanese pillars`() {
        val pillars = com.example.data.repository.FiveSRepository.FIVE_S_PILLARS
        assertEquals(5, pillars.size)

        val seiri = pillars.first { it.number == 1 }
        assertTrue(seiri.sinhalaName.contains("සෝදිසිය") || seiri.sinhalaName.contains("වර්ග කිරීම"))
        assertTrue(seiri.sewingMachineFocusSinhala.isNotBlank())
        assertEquals("整理", seiri.japaneseKanji)

        val seiton = pillars.first { it.number == 2 }
        assertTrue(seiton.sinhalaName.contains("පිළිවෙල") || seiton.sinhalaName.contains("සකස් කිරීම"))
        assertEquals("整頓", seiton.japaneseKanji)

        val seiso = pillars.first { it.number == 3 }
        assertTrue(seiso.sinhalaName.contains("පිරිසිදු"))
        assertEquals("清掃", seiso.japaneseKanji)

        val seiketsu = pillars.first { it.number == 4 }
        assertTrue(seiketsu.sinhalaName.contains("ප්‍රමිතිකරණය"))
        assertEquals("清潔", seiketsu.japaneseKanji)

        val shitsuke = pillars.first { it.number == 5 }
        assertTrue(shitsuke.sinhalaName.contains("විනය"))
        assertEquals("躾", shitsuke.japaneseKanji)
    }

    @Test
    fun `verify 5S Repository checklist and red tag functionality`() = kotlinx.coroutines.runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val repo = com.example.data.repository.FiveSRepository.getInstance(context)

        val initialChecklist = repo.checklist.first()
        assertTrue("Checklist should not be empty", initialChecklist.isNotEmpty())

        val firstItem = initialChecklist.first()
        val originalState = firstItem.isCompleted
        repo.toggleChecklistItem(firstItem.id)

        val updatedChecklist = repo.checklist.first()
        val toggledItem = updatedChecklist.first { it.id == firstItem.id }
        assertEquals(!originalState, toggledItem.isCompleted)

        // Test Red Tag addition
        repo.addRedTag(
            itemName = "Broken Needle Bar (කැඩුනු බාර්)",
            machineModel = "Juki DDL-8700",
            category = "PARTS",
            reason = "DAMAGED",
            action = "SCRAP",
            location = "Line 01",
            taggedBy = "Tech Silva"
        )

        val redTags = repo.redTags.first()
        assertTrue(redTags.any { it.itemName.contains("Broken Needle Bar") })

        // Test 5S Audit Scorecard calculation
        val auditRecord = com.example.data.model.FiveSAuditRecord(
            id = "audit_1",
            date = "2026-10-21",
            auditorName = "Manoj",
            machineLine = "Line 05",
            sortScore = 5,
            setInOrderScore = 4,
            shineScore = 5,
            standardizeScore = 4,
            sustainScore = 4
        )

        assertEquals(22, auditRecord.totalScore)
        assertEquals(88, auditRecord.percentage)
        assertTrue(auditRecord.grade.contains("Good") || auditRecord.grade.contains("B"))
    }

    @Test
    fun `verify 5S Locale Strings in Sinhala`() {
        val title = LocaleStrings.get(LocaleStrings.FIVES_TITLE, AppLanguage.SINHALA)
        assertTrue("5S title should contain 5S and ක්‍රියාවලිය", title.contains("5S") && title.contains("ක්‍රියාවලිය"))

        val subtitle = LocaleStrings.get(LocaleStrings.FIVES_SUBTITLE, AppLanguage.SINHALA)
        assertTrue("5S subtitle should mention ඇඟලුම් or ජපන් 5S", subtitle.contains("5S") || subtitle.contains("ඇඟලුම්"))
    }
}
