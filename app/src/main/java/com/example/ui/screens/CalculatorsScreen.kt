package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.components.CategoryChip
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.WarningAmber

@Composable
fun CalculatorsScreen(
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("calculators_screen")
    ) {
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            edgePadding = 16.dp
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(LocaleStrings.get(LocaleStrings.CALC_NEEDLE_TAB, currentLanguage)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(LocaleStrings.get(LocaleStrings.CALC_THREAD_TAB, currentLanguage)) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text(LocaleStrings.get(LocaleStrings.CALC_STITCH_TAB, currentLanguage)) }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = { Text(LocaleStrings.get(LocaleStrings.CALC_SPEED_TAB, currentLanguage)) }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> NeedleSizeCalculatorTab(currentLanguage)
                1 -> ThreadMatchCalculatorTab(currentLanguage)
                2 -> StitchDensityCalculatorTab(currentLanguage)
                3 -> SpeedCalculatorTab(currentLanguage)
            }
        }
    }
}

@Composable
fun NeedleSizeCalculatorTab(currentLanguage: AppLanguage) {
    val needleData = listOf(
        NeedleItem(65, 9, "Silk, Microfiber, Delicate Knits", "DBx1, DPx5 (Ball point)"),
        NeedleItem(70, 10, "Lightweight Cottons, Poplin, Voile", "DBx1, DCx27, UY128GAS"),
        NeedleItem(75, 11, "Standard Garment, Blouse, Knit Jersey", "DBx1, DPx5, DCx27"),
        NeedleItem(80, 12, "Medium Woven, Dress Shirts, Polos", "DBx1, DPx5, DCx27"),
        NeedleItem(90, 14, "Chinos, Twill, Suiting, Light Fleece", "DBx1, DPx5, UY128GAS"),
        NeedleItem(100, 16, "Heavy Denim, Duck, Canvas, Jackets", "DPx5, DPx17 (Walking foot)"),
        NeedleItem(110, 18, "Heavy Workwear, Webbing, Leather", "DPx5, DPx17"),
        NeedleItem(120, 19, "Upholstery, Extra Heavy Bags", "DPx17, DBx1"),
        NeedleItem(130, 21, "Multi-layer Canvas, Heavy Leather", "DPx17 (Heavy gauge)")
    )

    var selectedNm by remember { mutableIntStateOf(75) }
    val currentItem = needleData.firstOrNull { it.metricNm == selectedNm } ?: needleData[2]

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = LocaleStrings.get(LocaleStrings.CALC_SELECT_METRIC, currentLanguage),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            needleData.forEach { item ->
                CategoryChip(
                    label = "Nm ${item.metricNm}",
                    isSelected = selectedNm == item.metricNm,
                    onClick = { selectedNm = item.metricNm }
                )
            }
        }

        // Result Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Metric Size",
                            style = MaterialTheme.typography.labelMedium.copy(color = ElectricBlueAccent)
                        )
                        Text(
                            text = "Nm ${currentItem.metricNm}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.CALC_RESULT_SINGER, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(color = WarningAmber)
                        )
                        Text(
                            text = "Size ${currentItem.singerSize}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = WarningAmber
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "💡 " + LocaleStrings.get(LocaleStrings.CALC_RECOMMENDED_FABRIC, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = ElectricBlueAccent,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentItem.fabric,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White.copy(alpha = 0.1f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "🔧 " + LocaleStrings.get(LocaleStrings.CALC_RESULT_SYSTEM, currentLanguage),
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MachineGreen,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = currentItem.systems,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ThreadMatchCalculatorTab(currentLanguage: AppLanguage) {
    val threads = listOf(
        ThreadMatch(
            ticket = "160",
            tex = "Tex 18",
            needleNm = "Nm 65 - 75 (Size 9 - 11)",
            usage = "Microfiber, lingerie, delicate silk, edge overlocking"
        ),
        ThreadMatch(
            ticket = "120",
            tex = "Tex 24",
            needleNm = "Nm 75 - 90 (Size 11 - 14)",
            usage = "Standard garment production, shirts, trousers, knitwear (Core Spun / Poly-Poly)"
        ),
        ThreadMatch(
            ticket = "75",
            tex = "Tex 40",
            needleNm = "Nm 90 - 100 (Size 14 - 16)",
            usage = "Chinos, heavy fleece, safety seams, workwear"
        ),
        ThreadMatch(
            ticket = "50",
            tex = "Tex 60",
            needleNm = "Nm 100 - 110 (Size 16 - 18)",
            usage = "Denim structural seam, heavy outerwear, bags"
        ),
        ThreadMatch(
            ticket = "36",
            tex = "Tex 80",
            needleNm = "Nm 110 - 130 (Size 18 - 21)",
            usage = "Denim topstitching, decorative heavy stitching, leather"
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Thread Ticket & Tex to Needle Selection Guide",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        threads.forEach { item ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = ElectricBlueAccent.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "Ticket ${item.ticket} (${item.tex})",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = ElectricBlueAccent,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = item.needleNm,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MachineGreen
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.usage,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun StitchDensityCalculatorTab(currentLanguage: AppLanguage) {
    var spiInput by remember { mutableStateOf("12") }

    val spiValue = spiInput.toDoubleOrNull() ?: 12.0
    val mmPitch = if (spiValue > 0) 25.4 / spiValue else 0.0
    val spc = if (spiValue > 0) spiValue / 2.54 else 0.0

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Stitches Per Inch (SPI) Converter",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            value = spiInput,
            onValueChange = { spiInput = it },
            label = { Text("Enter SPI (Stitches Per Inch)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Calculated Stitch Length",
                    style = MaterialTheme.typography.labelMedium.copy(color = ElectricBlueAccent)
                )
                Text(
                    text = "%.2f mm stitch pitch".format(mmPitch),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "%.2f stitches per cm (SPC)".format(spc),
                    style = MaterialTheme.typography.titleMedium.copy(color = WarningAmber)
                )
            }
        }
    }
}

@Composable
fun SpeedCalculatorTab(currentLanguage: AppLanguage) {
    var motorRpm by remember { mutableStateOf("2850") }
    var motorPulleyMm by remember { mutableStateOf("75") }
    var machinePulleyMm by remember { mutableStateOf("65") }

    val mRpm = motorRpm.toDoubleOrNull() ?: 2850.0
    val mPulley = motorPulleyMm.toDoubleOrNull() ?: 75.0
    val machPulley = machinePulleyMm.toDoubleOrNull() ?: 65.0

    val calculatedSpm = if (machPulley > 0) (mRpm * mPulley) / machPulley else 0.0

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text(
            text = "Motor Pulley to Sewing Speed (SPM) Math",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            value = motorRpm,
            onValueChange = { motorRpm = it },
            label = { Text("Motor RPM (e.g. 2850)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = motorPulleyMm,
            onValueChange = { motorPulleyMm = it },
            label = { Text("Motor Pulley Diameter (mm)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = machinePulleyMm,
            onValueChange = { machinePulleyMm = it },
            label = { Text("Machine Handwheel Pulley Diameter (mm)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Calculated Machine Speed",
                    style = MaterialTheme.typography.labelMedium.copy(color = ElectricBlueAccent)
                )
                Text(
                    text = "${calculatedSpm.toInt()} SPM",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Stitches Per Minute (Maximum rated mechanical speed)",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White.copy(alpha = 0.8f))
                )
            }
        }
    }
}

private data class NeedleItem(
    val metricNm: Int,
    val singerSize: Int,
    val fabric: String,
    val systems: String
)

private data class ThreadMatch(
    val ticket: String,
    val tex: String,
    val needleNm: String,
    val usage: String
)
