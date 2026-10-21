package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PrecisionManufacturing
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavyDark
import com.example.ui.theme.IndustrialNavySurface
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.SteelGreySurface
import com.example.ui.theme.WarningAmber

@Composable
fun MeasuringUnitsScreen(
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit
) {
    // Hardware and system back button returns to Home
    BackHandler {
        onNavigateBack()
    }

    val fractionItems = remember {
        listOf(
            FractionData(
                fraction = "1/16\"",
                decimal = "0.0625\"",
                mm = "1.59 mm",
                nameSi = "අඟලෙන් 1/16 (කෙටිම ඉර)",
                nameEn = "One-Sixteenth Inch",
                nameTa = "பதினாறில் ஒன்று",
                usageSi = "Edge stitching, 1/16\" W/S (Wire stitch), කොලර් සහ පොකට් මුදුන් සියුම් දාර මැහුම් සඳහා.",
                usageEn = "Precision edge stitching, 1/16\" wire stitch (W/S) gauge, narrow margin topstitch.",
                usageTa = "விளிம்பு தையல் (Edge stitch) மற்றும் நுணுக்கமான தையல்களுக்குப் பயன்படுகிறது.",
                tickRatio = 0.28f,
                stepIndex = 1
            ),
            FractionData(
                fraction = "1/8\"",
                decimal = "0.125\"",
                mm = "3.18 mm",
                nameSi = "අටෙන් එක (1/8\")",
                nameEn = "One-Eighth Inch",
                nameTa = "எட்டில் ஒன்று",
                usageSi = "කමිස කොලර්, කෆ් සහ පොකට් ෆ්ලැප් සම්මත Topstitch මැහුම් පරතරය.",
                usageEn = "Standard garment collar/cuff edge topstitching distance.",
                usageTa = "காலர் மற்றும் கஃப் நிலையான விளிம்பு தையல் இடைவெளி.",
                tickRatio = 0.45f,
                stepIndex = 2
            ),
            FractionData(
                fraction = "3/16\"",
                decimal = "0.1875\"",
                mm = "4.76 mm",
                nameSi = "අඟලෙන් 3/16",
                nameEn = "Three-Sixteenths Inch",
                nameTa = "பதினாறில் மூன்று",
                usageSi = "Twin-needle topstitch ගේජ් සහ ඩෙනිම් අලංකාර මැහුම් සඳහා.",
                usageEn = "Twin needle gauge distance and decorative denim stitching.",
                usageTa = "இரட்டை ஊசி தையல் மற்றும் ஜீன்ஸ் அலங்கார தையல்.",
                tickRatio = 0.28f,
                stepIndex = 3
            ),
            FractionData(
                fraction = "1/4\"",
                decimal = "0.250\"",
                mm = "6.35 mm",
                nameSi = "කාල් අඟල (1/4\")",
                nameEn = "One-Quarter Inch",
                nameTa = "கால் அங்குலம் (1/4\")",
                usageSi = "මහන මැෂින් පීඩන පාදමේ (Presser foot) සම්මත පළල, ප්‍රධාන මැහුම් වාටිය (Seam allowance).",
                usageEn = "Universal presser foot width and standard garment joining seam allowance.",
                usageTa = "பிரஸ்ஸர் ஃபுட் அகலம் மற்றும் நிலையான தையல் இடைவெளி (Seam allowance).",
                tickRatio = 0.65f,
                stepIndex = 4
            ),
            FractionData(
                fraction = "5/16\"",
                decimal = "0.3125\"",
                mm = "7.94 mm",
                nameSi = "අඟලෙන් 5/16",
                nameEn = "Five-Sixteenths Inch",
                nameTa = "பதினாறில் ஐந்து",
                usageSi = "කලිසම් සාක්කු සහ ලයිනිං සවි කිරීමේ වාටි සඳහා.",
                usageEn = "Trouser pocket bag construction and lining join seams.",
                usageTa = "பாக்கெட் மற்றும் லைனிங் தையல் இடைவெளி.",
                tickRatio = 0.28f,
                stepIndex = 5
            ),
            FractionData(
                fraction = "3/8\"",
                decimal = "0.375\"",
                mm = "9.53 mm",
                nameSi = "අටෙන් තුන (3/8\")",
                nameEn = "Three-Eighths Inch",
                nameTa = "எட்டில் மூன்று (3/8\")",
                usageSi = "ඕවර්ලොක් (Overlock 4-thread / 5-thread) ආරක්ෂිත මැහුම් වාටිය (Overlock seam).",
                usageEn = "Standard 4-thread & 5-thread overlock seam trimming and joining allowance.",
                usageTa = "4 மற்றும் 5-நூல் ஓவர்லாக் தையல் வெட்டு இடைவெளி.",
                tickRatio = 0.45f,
                stepIndex = 6
            ),
            FractionData(
                fraction = "7/16\"",
                decimal = "0.4375\"",
                mm = "11.11 mm",
                nameSi = "අඟලෙන් 7/16",
                nameEn = "Seven-Sixteenths Inch",
                nameTa = "பதினாறில் ஏழு",
                usageSi = "බොත්තම් කාස (Buttonhole) පරතරය සහ ෆෝල්ඩර් ගයිඩ් සැකසුම්.",
                usageEn = "Buttonhole margins and automated folder attachment margins.",
                usageTa = "பட்டன்ஹோல் இடைவெளி மற்றும் ஃபோல்டர் அமைப்புகள்.",
                tickRatio = 0.28f,
                stepIndex = 7
            ),
            FractionData(
                fraction = "1/2\"",
                decimal = "0.500\"",
                mm = "12.70 mm",
                nameSi = "බාග අඟල (1/2\" - මැද දිගම ඉර)",
                nameEn = "Half Inch (Center Long Mark)",
                nameTa = "அரை அங்குலம் (1/2\")",
                usageSi = "කමිස සහ කලිසම් යටි වාටි (Bottom hem fold allowance), සිපර් පටි සවි කිරීම.",
                usageEn = "Shirt & trouser bottom hem allowance fold, zipper tape attachment margin.",
                usageTa = "கீழ் மடிப்பு (Hem allowance) மற்றும் ஜிப்பர் பொருத்துதல்.",
                tickRatio = 0.85f,
                stepIndex = 8
            ),
            FractionData(
                fraction = "5/8\"",
                decimal = "0.625\"",
                mm = "15.88 mm",
                nameSi = "අටෙන් පහ (5/8\")",
                nameEn = "Five-Eighths Inch",
                nameTa = "எட்டில் ஐந்து",
                usageSi = "ජැකට්, බ්ලේසර් සහ බර රෙදි මැහුම් වාටි (Heavy apparel seam allowance).",
                usageEn = "Outerwear, blazer, and heavy garment construction seam allowances.",
                usageTa = "கோட் மற்றும் கனரக ஆடை தையல் இடைவெளி.",
                tickRatio = 0.45f,
                stepIndex = 10
            ),
            FractionData(
                fraction = "3/4\"",
                decimal = "0.750\"",
                mm = "19.05 mm",
                nameSi = "මුක්කාල (3/4\")",
                nameEn = "Three-Quarters Inch",
                nameTa = "முக்கால் அங்குலம் (3/4\")",
                usageSi = "ඉණ පටි (Waistband band), කෆ් පළල සහ පළල් වාටි.",
                usageEn = "Waistband band width, cuff facing, and wide hem finishes.",
                usageTa = "இடுப்பு பட்டை (Waistband) மற்றும் அகலமான மடிப்புகள்.",
                tickRatio = 0.65f,
                stepIndex = 12
            ),
            FractionData(
                fraction = "7/8\"",
                decimal = "0.875\"",
                mm = "22.23 mm",
                nameSi = "අටෙන් හත (7/8\")",
                nameEn = "Seven-Eighths Inch",
                nameTa = "எட்டில் ஏழு",
                usageSi = "විශේෂිත ෆෝල්ඩර් සහ බෙල්ට් ලූප් පළල.",
                usageEn = "Folder attachments and wide belt loop specifications.",
                usageTa = "பெல்ட் லூப் மற்றும் சிறப்பு ஃபோல்டர்கள்.",
                tickRatio = 0.45f,
                stepIndex = 14
            ),
            FractionData(
                fraction = "1\"",
                decimal = "1.000\"",
                mm = "25.40 mm",
                nameSi = "සම්පූර්ණ අඟල (1 Inch = 25.4mm)",
                nameEn = "One Full Inch (25.4 mm)",
                nameTa = "முழு அங்குலம் (25.4 மிமீ)",
                usageSi = "කොටස් 16ක එකතුව. SPI (Stitches Per Inch - අඟලකට මැහුම් ගණන) නිවැරදිව මැනීම.",
                usageEn = "Complete 16-division inch. Fundamental window for checking SPI (Stitches Per Inch).",
                usageTa = "16 பிரிவுகளின் கூட்டுத்தொகை. தையல் அடர்த்தியை (SPI) அளவிடும் அளவு.",
                tickRatio = 1.0f,
                stepIndex = 16
            )
        )
    }

    var selectedFractionIndex by remember { mutableIntStateOf(3) } // default 1/4"
    val activeItem = fractionItems[selectedFractionIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .testTag("measuring_units_screen")
    ) {
        // Top Card with Back Navigation to "මුල් පිටුව"
        Card(
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
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
                    // Prominent Back Button to Return to Home
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = ElectricBlueAccent.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { onNavigateBack() }
                            .testTag("measuring_back_to_home_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "මුල් පිටුව"
                                    AppLanguage.TAMIL -> "முகப்பு"
                                    AppLanguage.ENGLISH -> "Home"
                                },
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = WarningAmber.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarningAmber)
                    ) {
                        Text(
                            text = "1\" = 25.4 mm",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = when (currentLanguage) {
                        AppLanguage.SINHALA -> "දිග මනින මිනුම් ඒකක සහ මිනුම් උපකරණ"
                        AppLanguage.TAMIL -> "நீளத்தை அளவிடும் அலகுகள் மற்றும் அளவீட்டு கருவிகள்"
                        AppLanguage.ENGLISH -> "Length Measuring Units & Measuring Tools"
                    },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = when (currentLanguage) {
                        AppLanguage.SINHALA -> "ඇඟලුම් කර්මාන්තයේ අඟල් සලකුණු (1/16\", 1/8\", 1/4\", 1/2\") සහ මිනුම් පටි කියවීම"
                        AppLanguage.TAMIL -> "ஆடைத் துறையில் அங்குல அளவீடுகள் (1/16, 1/8, 1/4, 1/2) மற்றும் அளவீட்டு கருவிகள்"
                        AppLanguage.ENGLISH -> "Garment industry tape & ruler markings (1/16\", 1/8\", 1/4\", 1/2\") and precision measurement"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = ElectricBlueAccent,
                        lineHeight = 18.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Visual Interactive Inch Ruler
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .testTag("interactive_inch_ruler_card")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Straighten,
                            contentDescription = null,
                            tint = ElectricBlueAccent,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Visual Inch Ruler (1 Inch = 16 Divisions)",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = "16ths",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = ElectricBlueAccent,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Custom Graphic Ruler Canvas (1 Inch Scale with 16 Tick Marks)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .background(Color(0xFFFFFBEB), RoundedCornerShape(8.dp))
                        .border(1.5.dp, Color(0xFFD97706), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val rulerWidth = size.width
                        val rulerHeight = size.height
                        val stepWidth = rulerWidth / 16f

                        // Top Base Line
                        drawLine(
                            color = Color(0xFF78350F),
                            start = Offset(0f, 0f),
                            end = Offset(rulerWidth, 0f),
                            strokeWidth = 3f
                        )

                        // 17 Tick Marks (0 to 16)
                        for (i in 0..16) {
                            val x = i * stepWidth
                            val isHighlighted = i == activeItem.stepIndex

                            // Height calculation based on fraction denominator
                            val tickHeight = when {
                                i == 0 || i == 16 -> rulerHeight * 0.90f // 0 and 1" (Full)
                                i == 8 -> rulerHeight * 0.75f           // 1/2"
                                i % 4 == 0 -> rulerHeight * 0.55f       // 1/4", 3/4"
                                i % 2 == 0 -> rulerHeight * 0.40f       // 1/8", 3/8", 5/8", 7/8"
                                else -> rulerHeight * 0.25f             // 1/16", 3/16", 5/16", 7/16", etc.
                            }

                            val lineColor = if (isHighlighted) Color(0xFFEF4444) else Color(0xFF78350F)
                            val lineWidth = if (isHighlighted) 5f else if (i % 4 == 0) 3f else 2f

                            drawLine(
                                color = lineColor,
                                start = Offset(x, 0f),
                                end = Offset(x, tickHeight),
                                strokeWidth = lineWidth
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Tap Fraction Buttons Row
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.SINHALA -> "අඟලේ සලකුණ තෝරා විස්තර බලන්න:"
                        AppLanguage.TAMIL -> "விவரங்களை காண அளவீட்டைத் தேர்ந்தெடுக்கவும்:"
                        AppLanguage.ENGLISH -> "Select an Inch Marking to Inspect:"
                    },
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    fractionItems.forEachIndexed { index, item ->
                        val isSelected = index == selectedFractionIndex
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) ElectricBlueAccent else SteelGreySurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) ElectricBlueAccent else SteelGreyMedium.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedFractionIndex = index }
                                .testTag("fraction_btn_${item.fraction.replace("/", "_").replace("\"", "")}")
                        ) {
                            Text(
                                text = item.fraction,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Highlighted Fraction Detail Box
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = ElectricBlueAccent.copy(alpha = 0.10f),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = activeItem.fraction,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ElectricBlueAccent,
                                        fontSize = 26.sp
                                    )
                                )
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> activeItem.nameSi
                                        AppLanguage.TAMIL -> activeItem.nameTa
                                        AppLanguage.ENGLISH -> activeItem.nameEn
                                    },
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MachineGreen
                                ) {
                                    Text(
                                        text = activeItem.mm,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Decimal: ${activeItem.decimal}",
                                    style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = ElectricBlueAccent.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.PrecisionManufacturing,
                                contentDescription = null,
                                tint = ElectricBlueAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> "ඇඟලුම් නිෂ්පාදන භාවිතය (Garment Application):"
                                        AppLanguage.TAMIL -> "ஆடை பயன்பாடு (Garment Application):"
                                        AppLanguage.ENGLISH -> "Garment Factory Application:"
                                    },
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ElectricBlueAccent
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> activeItem.usageSi
                                        AppLanguage.TAMIL -> activeItem.usageTa
                                        AppLanguage.ENGLISH -> activeItem.usageEn
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        lineHeight = 20.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Complete Inch to Millimeter Conversion Table
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FormatListNumbered,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SINHALA -> "අඟල් සහ මිලිමීටර් පරිවර්තන වගුව (Conversion Table)"
                            AppLanguage.TAMIL -> "அங்குலம் மற்றும் மில்லிமீட்டர் மாற்ற அட்டவணை"
                            AppLanguage.ENGLISH -> "Inch Fraction to Metric Conversion Chart"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SteelGreyDark, RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Fraction (අඟල්)",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Millimeters (mm)",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Decimal Inch",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                fractionItems.forEachIndexed { idx, item ->
                    val bg = if (idx % 2 == 0) MaterialTheme.colorScheme.surface else SteelGreySurface.copy(alpha = 0.5f)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(bg, RoundedCornerShape(4.dp))
                            .clickable { selectedFractionIndex = idx }
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.fraction,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = ElectricBlueAccent),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = item.mm,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = item.decimal,
                            style = MaterialTheme.typography.bodySmall.copy(color = SteelGreyMedium),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Industrial Measuring Tools (මිනුම් උපකරණ)
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.SquareFoot,
                        contentDescription = null,
                        tint = MachineGreen,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SINHALA -> "කාර්මික මිනුම් උපකරණ (Industrial Measuring Tools)"
                            AppLanguage.TAMIL -> "தொழில்துறை அளவீட்டு கருவிகள்"
                            AppLanguage.ENGLISH -> "Industrial Sewing Measuring Instruments"
                        },
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                val tools = listOf(
                    MeasuringToolItem(
                        nameSi = "1. මිනුම් පටිය (Fiberglass Tailor's Tape)",
                        nameEn = "1. Flexible Fiberglass Tailor Tape",
                        nameTa = "1. தையல் அளவீட்டு நாடா",
                        descSi = "ඇදෙන්නේ නැති ෆයිබර්ග්ලාස් පටියකි. අඟල් 60 (150 cm) දිගකින් යුක්ත වන අතර රෙදි සහ ඇඟලුම් මිනුම් සඳහා මූලික වේ.",
                        descEn = "Non-stretch fiberglass tape, 60\" / 150cm length for fabric, pattern, and apparel measurements."
                    ),
                    MeasuringToolItem(
                        nameSi = "2. ඇක්‍රිලික් විනිවිද පෙනෙන රූල (Clear Acrylic Grid Ruler)",
                        nameEn = "2. 18-Inch Clear Acrylic Grid Ruler",
                        nameTa = "2. அக்ரிலிக் ரூலர்",
                        descSi = "අඟල් 1/8 සහ 1/16 කොටස් පැහැදිලිව පෙනෙන රූලකි. වාටි සහ මැහුම් රේඛා සමාන්තරව මැනීමට භාවිතා කරයි.",
                        descEn = "Clear 1/8\" grid ruler used by pattern makers and mechanics to check seam parallel alignment."
                    ),
                    MeasuringToolItem(
                        nameSi = "3. මැහුම් ගේජය (Sliding Metal Seam Gauge)",
                        nameEn = "3. Metal Sliding Seam Gauge",
                        nameTa = "3. மெட்டல் சீம் கேஜ்",
                        descSi = "ස්ලයිඩරයක් සහිත ලෝහ අඟල් 6 රූලකි. වාටි (Hem) සහ බොත්තම් කාස පරතරය නිරවද්‍යව මනියි.",
                        descEn = "6-inch metal gauge with adjustable slider for repeat hem folds and buttonhole spacing."
                    ),
                    MeasuringToolItem(
                        nameSi = "4. පිලර් ගේජය (Precision Feeler Gauge)",
                        nameEn = "4. Precision Feeler Gauge Set",
                        nameTa = "4. ஃபீலர் கேஜ் (0.05 மிமீ)",
                        descSi = "රොටරි හුක් සහ ලූපර් ක්ලියරන්ස් (0.05mm - 0.10mm) නිරවද්‍යව මැනීමට කාර්මික ශිල්පියාට අත්‍යවශ්‍යම මෙවලමයි.",
                        descEn = "Essential mechanic tool for setting hook-to-needle clearance (0.05mm to 0.10mm tolerance)."
                    ),
                    MeasuringToolItem(
                        nameSi = "5. ඩිජිටල් වර්නියර් කැලිපරය (Digital Vernier Caliper)",
                        nameEn = "5. Digital Vernier Caliper",
                        nameTa = "5. டிஜிட்டல் வெர்னியர் காலிபர்",
                        descSi = "ඉඳිකටු බාර් උස (Needle bar height) සහ ෂාෆ්ට් ඝනකම 0.01mm නිරවද්‍යතාවයෙන් මනිනු ලබයි.",
                        descEn = "Measures needle bar height, pully diameters, and shaft clearances to 0.01mm accuracy."
                    )
                )

                tools.forEach { tool ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SteelGreySurface.copy(alpha = 0.5f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA) tool.nameSi else tool.nameEn,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = IndustrialNavy
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (currentLanguage == AppLanguage.SINHALA) tool.descSi else tool.descEn,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bottom Direct Return Button to Home
        Button(
            onClick = onNavigateBack,
            colors = ButtonDefaults.buttonColors(containerColor = IndustrialNavy),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(48.dp)
                .testTag("bottom_back_to_home_button")
        ) {
            Icon(imageVector = Icons.Default.Home, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = when (currentLanguage) {
                    AppLanguage.SINHALA -> "ආපසු මුල් පිටුවට (Back to Home)"
                    AppLanguage.TAMIL -> "மீண்டும் முகப்பிற்கு (Back to Home)"
                    AppLanguage.ENGLISH -> "Back to Home Page"
                },
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

private data class FractionData(
    val fraction: String,
    val decimal: String,
    val mm: String,
    val nameSi: String,
    val nameEn: String,
    val nameTa: String,
    val usageSi: String,
    val usageEn: String,
    val usageTa: String,
    val tickRatio: Float,
    val stepIndex: Int
)

private data class MeasuringToolItem(
    val nameSi: String,
    val nameEn: String,
    val nameTa: String,
    val descSi: String,
    val descEn: String
)
