package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TechnicianProfile
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavyDark
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.WarningAmber

@Composable
fun MembershipScreen(
    profile: TechnicianProfile?,
    currentLanguage: AppLanguage,
    onNavigateBack: () -> Unit,
    onUpdateQuizScore: (Int, String) -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val techProfile = profile ?: TechnicianProfile()

    // 3 Technical Quiz Questions for competency check
    val questions = remember(currentLanguage) {
        listOf(
            QuizQuestion(
                question = when (currentLanguage) {
                    AppLanguage.SINHALA -> "රොටරි හුක් තුඩ සහ ඉඳිකටු ස්කාෆ් අතර සම්මත හිඩැස (Clearance) කොපමණද?"
                    AppLanguage.ENGLISH -> "What is the standard clearance between the rotary hook point and needle scarf?"
                    AppLanguage.TAMIL -> "ரோட்டரி ஹூக் முனைக்கும் ஊசிக்கும் இடையிலான நிலையான இடைவெளி எவ்வளவு?"
                },
                options = listOf("0.5mm - 1.0mm", "0.05mm - 0.10mm", "0.2mm - 0.3mm", "1.5mm - 2.0mm"),
                correctIndex = 1
            ),
            QuizQuestion(
                question = when (currentLanguage) {
                    AppLanguage.SINHALA -> "තනි ඉඳිකටු මහන මැෂිමක BDC සිට ඉඳිකටු බාර් එක ඉහළට එසවිය යුතු සම්මත උස (Needle Rise) කොපමණද?"
                    AppLanguage.ENGLISH -> "What is the standard needle rise from BDC for lockstitch hook timing?"
                    AppLanguage.TAMIL -> "லாக்ஸ்டிட்ச் ஹூக் டைமிங்கிற்கு BDC இலிருந்து ஊசி பார் எவ்வளவு உயர வேண்டும்?"
                },
                options = listOf("0.5mm - 0.8mm", "1.8mm - 2.0mm", "3.5mm - 4.0mm", "5.0mm"),
                correctIndex = 1
            ),
            QuizQuestion(
                question = when (currentLanguage) {
                    AppLanguage.SINHALA -> "කාර්මික මහන මැෂින් ලිහිසි කිරීමට භාවිතා කරන තෙල් වර්ගය කුමක්ද?"
                    AppLanguage.ENGLISH -> "Which oil grade is standard for industrial garment sewing machine pumps?"
                    AppLanguage.TAMIL -> "தொழில்துறை தையல் இயந்திர பம்புகளுக்கு எந்த எண்ணெய் தரம் சிறந்தது?"
                },
                options = listOf("ISO VG 10 White Mineral Oil", "Engine Oil 20W-50", "Heavy Gear Oil", "Brake Fluid"),
                correctIndex = 0
            )
        )
    }

    var selectedAnswers by remember { mutableStateOf(mapOf<Int, Int>()) }
    var quizSubmitted by remember { mutableStateOf(false) }
    var calculatedScore by remember { mutableIntStateOf(techProfile.quizScore) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("membership_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Digital Technician Credential Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialNavyCard),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, ElectricBlueAccent),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("tech_badge_card")
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(IndustrialNavyDark, IndustrialNavyCard)
                        )
                    )
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = ElectricBlueAccent.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent),
                                modifier = Modifier.size(44.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Badge,
                                        contentDescription = null,
                                        tint = ElectricBlueAccent,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = LocaleStrings.get(LocaleStrings.MEMBER_CARD_TITLE, currentLanguage),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                )
                                Text(
                                    text = "ID: LK-TECH-${techProfile.id}089",
                                    style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MachineGreen.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen)
                        ) {
                            Text(
                                text = "VERIFIED",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MachineGreen
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = techProfile.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 22.sp
                        )
                    )
                    Text(
                        text = techProfile.role,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = ElectricBlueAccent,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = SteelGreyDark)
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.MEMBER_FACTORY_NAME, currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                            )
                            Text(
                                text = techProfile.factoryName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.MEMBER_BADGE_LEVEL, currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(color = SteelGreyMedium)
                            )
                            Text(
                                text = techProfile.membershipLevel,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = WarningAmber
                                )
                            )
                        }
                    }
                }
            }
        }

        // Quiz Section
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = LocaleStrings.get(LocaleStrings.MEMBER_QUIZ_TITLE, currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = LocaleStrings.get(LocaleStrings.MEMBER_QUIZ_SUBTITLE, currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                questions.forEachIndexed { qIdx, question ->
                    Text(
                        text = "${qIdx + 1}. ${question.question}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    question.options.forEachIndexed { oIdx, option ->
                        val isChosen = selectedAnswers[qIdx] == oIdx
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isChosen) ElectricBlueAccent.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isChosen) ElectricBlueAccent else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .testTag("quiz_q${qIdx}_o${oIdx}")
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isChosen,
                                    onClick = {
                                        if (!quizSubmitted) {
                                            selectedAnswers = selectedAnswers + (qIdx to oIdx)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (!quizSubmitted) {
                    Button(
                        onClick = {
                            var correct = 0
                            questions.forEachIndexed { idx, q ->
                                if (selectedAnswers[idx] == q.correctIndex) correct++
                            }
                            val scorePercent = ((correct.toFloat() / questions.size.toFloat()) * 100).toInt()
                            calculatedScore = scorePercent
                            val newLevel = when {
                                scorePercent >= 90 -> "MASTER"
                                scorePercent >= 60 -> "CERTIFIED"
                                else -> "APPRENTICE"
                            }
                            quizSubmitted = true
                            onUpdateQuizScore(scorePercent, newLevel)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_quiz_button")
                    ) {
                        Text(
                            text = "Submit Answers & Evaluate",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = MachineGreen.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "🎉 " + LocaleStrings.get(LocaleStrings.MEMBER_QUIZ_SCORE, currentLanguage) + ": $calculatedScore%",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MachineGreen
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Certification Level: ${techProfile.membershipLevel}",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)
