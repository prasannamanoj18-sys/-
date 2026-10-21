package com.example.ui.screens

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.data.model.TechnicalArticle
import com.example.localization.AppLanguage
import com.example.localization.LocaleStrings
import com.example.ui.navigation.Screen
import com.example.ui.theme.ElectricBlueAccent
import com.example.ui.theme.IndustrialNavy
import com.example.ui.theme.IndustrialNavyCard
import com.example.ui.theme.IndustrialNavyDark
import com.example.ui.theme.MachineGreen
import com.example.ui.theme.SteelGreyDark
import com.example.ui.theme.SteelGreyMedium
import com.example.ui.theme.SteelGreySurface
import com.example.ui.theme.WarningAmber
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

enum class ScannerTab {
    CAMERA,
    MANUAL
}

@Composable
fun QrScannerScreen(
    currentLanguage: AppLanguage,
    articles: List<TechnicalArticle> = emptyList(),
    onNavigateBack: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onArticleSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf(ScannerTab.CAMERA) }

    // Camera permission state
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var permissionDeniedExplanation by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (!isGranted) {
            permissionDeniedExplanation = true
            // If denied, fallback to manual entry tab smoothly
            selectedTab = ScannerTab.MANUAL
        }
    }

    // Auto-launch camera permission as soon as the screen opens
    LaunchedEffect(Unit) {
        if (!hasCameraPermission && !permissionDeniedExplanation) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Scanned result state
    var scannedResult by remember { mutableStateOf<String?>(null) }
    var isScanningActive by remember { mutableStateOf(true) }
    var showUrlConfirmationDialog by remember { mutableStateOf<String?>(null) }
    var cameraErrorMessage by remember { mutableStateOf<String?>(null) }
    var torchEnabled by remember { mutableStateOf(false) }
    var lensFacing by remember { mutableIntStateOf(CameraSelector.LENS_FACING_BACK) }

    // Photo picker launcher to scan QR codes from device gallery / images
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { imageUri ->
            try {
                val inputImage = InputImage.fromFilePath(context, imageUri)
                val barcodeScanner = BarcodeScanning.getClient(
                    BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(
                            Barcode.FORMAT_QR_CODE,
                            Barcode.FORMAT_DATA_MATRIX,
                            Barcode.FORMAT_AZTEC,
                            Barcode.FORMAT_CODE_128,
                            Barcode.FORMAT_CODE_39
                        )
                        .build()
                )
                barcodeScanner.process(inputImage)
                    .addOnSuccessListener { barcodes ->
                        val firstCode = barcodes.firstOrNull { b -> !b.rawValue.isNullOrBlank() }?.rawValue
                        if (!firstCode.isNullOrBlank()) {
                            isScanningActive = false
                            scannedResult = firstCode.trim()
                        } else {
                            Toast.makeText(
                                context,
                                if (currentLanguage == AppLanguage.SINHALA) "තෝරාගත් පින්තූරයේ QR කේතයක් හමු නොවීය."
                                else "No QR code found in selected image.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "පින්තූරය කියවීමට නොහැකි විය: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: Exception) {
                Log.e("QrScanner", "Image parsing error", e)
                Toast.makeText(context, "Error reading image file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Manual input state
    var manualInputText by remember { mutableStateOf("") }
    var manualInputError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("qr_scanner_screen")
    ) {
        // Mode Selection Tabs (Embedded Camera vs Manual Entry)
        TabRow(
            selectedTabIndex = selectedTab.ordinal,
            containerColor = IndustrialNavy,
            contentColor = Color.White,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                    color = ElectricBlueAccent
                )
            }
        ) {
            Tab(
                selected = selectedTab == ScannerTab.CAMERA,
                onClick = {
                    selectedTab = ScannerTab.CAMERA
                    if (!hasCameraPermission) {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (selectedTab == ScannerTab.CAMERA) ElectricBlueAccent else Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "කැමරා Scan"
                                AppLanguage.TAMIL -> "கேமரா ஸ்கேன்"
                                AppLanguage.ENGLISH -> "Camera Scan"
                            },
                            fontWeight = if (selectedTab == ScannerTab.CAMERA) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                },
                modifier = Modifier.testTag("tab_qr_camera")
            )

            Tab(
                selected = selectedTab == ScannerTab.MANUAL,
                onClick = { selectedTab = ScannerTab.MANUAL },
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (selectedTab == ScannerTab.MANUAL) ElectricBlueAccent else Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, currentLanguage),
                            fontWeight = if (selectedTab == ScannerTab.MANUAL) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                modifier = Modifier.testTag("tab_qr_manual")
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            when (selectedTab) {
                ScannerTab.CAMERA -> {
                    if (hasCameraPermission) {
                        if (cameraErrorMessage != null) {
                            // Camera hardware failure or error
                            CameraErrorFallbackView(
                                errorMessage = cameraErrorMessage ?: "",
                                currentLanguage = currentLanguage,
                                onSwitchToManual = { selectedTab = ScannerTab.MANUAL },
                                onRetry = { cameraErrorMessage = null }
                            )
                        } else {
                            // Embedded In-App CameraX + ML Kit Scanner View
                            EmbeddedCameraScannerView(
                                isScanningActive = isScanningActive && scannedResult == null,
                                torchEnabled = torchEnabled,
                                lensFacing = lensFacing,
                                onCodeDetected = { code ->
                                    isScanningActive = false
                                    scannedResult = code.trim()
                                },
                                onError = { error ->
                                    cameraErrorMessage = error
                                }
                            )

                            // Viewfinder Overlay with Reticle & Scan line
                            ScannerOverlay(
                                isScanning = isScanningActive && scannedResult == null,
                                currentLanguage = currentLanguage
                            )

                            // Top Controls Bar: Torch (Left) & Camera Switch (Right)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp)
                                    .align(Alignment.TopCenter),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Torch Status & Toggle Bar
                                Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = if (torchEnabled) WarningAmber.copy(alpha = 0.95f) else IndustrialNavy.copy(alpha = 0.9f),
                                    border = androidx.compose.foundation.BorderStroke(
                                        1.5.dp,
                                        if (torchEnabled) Color.White else ElectricBlueAccent
                                    ),
                                    shadowElevation = 6.dp,
                                    modifier = Modifier.clip(RoundedCornerShape(24.dp))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable { torchEnabled = !torchEnabled }
                                            .padding(horizontal = 14.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = if (torchEnabled) Icons.Default.FlashOn else Icons.Default.FlashOff,
                                            contentDescription = "Torch",
                                            tint = if (torchEnabled) Color.Black else WarningAmber,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (torchEnabled) LocaleStrings.get(LocaleStrings.QR_TORCH_ON, currentLanguage)
                                            else LocaleStrings.get(LocaleStrings.QR_TORCH_OFF, currentLanguage),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = if (torchEnabled) Color.Black else Color.White
                                            )
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (torchEnabled) Color.Black.copy(alpha = 0.15f) else ElectricBlueAccent.copy(alpha = 0.25f)
                                        ) {
                                            Text(
                                                text = if (torchEnabled) "ON" else "OFF",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontWeight = FontWeight.ExtraBold,
                                                    color = if (torchEnabled) Color.Black else ElectricBlueAccent,
                                                    fontSize = 10.sp
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                // Switch Camera (Front/Back) Button
                                Surface(
                                    shape = CircleShape,
                                    color = IndustrialNavy.copy(alpha = 0.9f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent),
                                    shadowElevation = 4.dp,
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
                                                CameraSelector.LENS_FACING_FRONT
                                            } else {
                                                CameraSelector.LENS_FACING_BACK
                                            }
                                        },
                                        modifier = Modifier.testTag("btn_flip_camera")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Cameraswitch,
                                            contentDescription = "Flip Camera",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }

                            // Floating Button to Scan QR from Gallery / Photo (Bottom above instructions)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(bottom = 76.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(20.dp),
                                    color = IndustrialNavyCard.copy(alpha = 0.95f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent),
                                    shadowElevation = 6.dp,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .clickable {
                                            photoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        }
                                        .testTag("btn_pick_qr_image")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.PhotoLibrary,
                                            contentDescription = null,
                                            tint = ElectricBlueAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = LocaleStrings.get(LocaleStrings.QR_PICK_IMAGE, currentLanguage),
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        // Permission not granted state
                        CameraPermissionRequestView(
                            currentLanguage = currentLanguage,
                            isDenied = permissionDeniedExplanation,
                            onRequestPermission = {
                                permissionLauncher.launch(Manifest.permission.CAMERA)
                            },
                            onPickImage = {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                            onSwitchToManual = {
                                selectedTab = ScannerTab.MANUAL
                            }
                        )
                    }
                }

                ScannerTab.MANUAL -> {
                    // Manual Link / Code Input View
                    ManualEntryView(
                        inputText = manualInputText,
                        error = manualInputError,
                        currentLanguage = currentLanguage,
                        onTextChanged = {
                            manualInputText = it
                            manualInputError = null
                        },
                        onSubmit = { text ->
                            if (text.isBlank()) {
                                manualInputError = when (currentLanguage) {
                                    AppLanguage.SINHALA -> "කරුණාකර ලින්ක් එකක් හෝ මැෂින් කේතයක් ඇතුළත් කරන්න"
                                    AppLanguage.TAMIL -> "தயவுசெய்து ஒரு இணைப்பை உள்ளிடவும்"
                                    AppLanguage.ENGLISH -> "Please enter a valid link or machine code"
                                }
                            } else {
                                scannedResult = text.trim()
                            }
                        }
                    )
                }
            }

            // Scanned Result Bottom Sheet / Card (Always displayed in Sinhala / selected language)
            scannedResult?.let { result ->
                ScannedResultCard(
                    scannedText = result,
                    currentLanguage = currentLanguage,
                    articles = articles,
                    onDismiss = {
                        scannedResult = null
                        isScanningActive = true
                    },
                    onOpenLinkConfirmed = { url ->
                        showUrlConfirmationDialog = url
                    },
                    onSearchMachine = { keyword ->
                        scannedResult = null
                        onNavigate(
                            Screen.KnowledgeList(
                                categoryFilter = "ALL",
                                machineFilter = "ALL",
                                searchQuery = keyword
                            )
                        )
                    },
                    onArticleSelected = { articleId ->
                        scannedResult = null
                        onArticleSelected(articleId)
                    }
                )
            }
        }
    }

    // Safety Confirmation Dialog for Opening External Links (Requirement 7)
    showUrlConfirmationDialog?.let { urlToOpen ->
        AlertDialog(
            onDismissRequest = { showUrlConfirmationDialog = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SINHALA -> "ලින්ක් එක විවෘත කිරීම තහවුරු කරන්න"
                            AppLanguage.TAMIL -> "இணைப்பைத் திறப்பதை உறுதிப்படுத்தவும்"
                            AppLanguage.ENGLISH -> "Confirm Opening Link"
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = LocaleStrings.get(LocaleStrings.QR_CONFIRM_OPEN, currentLanguage),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = urlToOpen,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                color = ElectricBlueAccent,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(10.dp),
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SINHALA -> "ආරක්ෂිත බාහිර වෙබ් අඩවියකට පිවිසීමට ඔබේ බ්‍රවුසරය විවෘත වේ."
                            AppLanguage.TAMIL -> "இது உங்கள் உலாவியில் திறக்கப்படும்."
                            AppLanguage.ENGLISH -> "This will safely open in your verified mobile browser."
                        },
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val targetUrl = if (!urlToOpen.startsWith("http://", ignoreCase = true) && !urlToOpen.startsWith("https://", ignoreCase = true)) {
                            "https://$urlToOpen"
                        } else {
                            urlToOpen
                        }
                        showUrlConfirmationDialog = null
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Log.e("QrScanner", "Could not open URL: $targetUrl", e)
                            Toast.makeText(
                                context,
                                LocaleStrings.get(LocaleStrings.QR_INVALID_LINK, currentLanguage),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MachineGreen),
                    modifier = Modifier.testTag("btn_confirm_open_link")
                ) {
                    Icon(
                        imageVector = Icons.Default.OpenInBrowser,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (currentLanguage) {
                            AppLanguage.SINHALA -> "විවෘත කරන්න"
                            AppLanguage.TAMIL -> "திறக்க"
                            AppLanguage.ENGLISH -> "Open Link"
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showUrlConfirmationDialog = null },
                    modifier = Modifier.testTag("btn_cancel_open_link")
                ) {
                    Text(text = LocaleStrings.get(LocaleStrings.BTN_CANCEL, currentLanguage))
                }
            }
        )
    }
}

/**
 * Embedded Camera View using CameraX and ML Kit Barcode Scanning.
 * Shuts down camera explicitly on disposal (Requirement 9).
 */
@OptIn(ExperimentalGetImage::class)
@Composable
fun EmbeddedCameraScannerView(
    isScanningActive: Boolean,
    torchEnabled: Boolean,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    onCodeDetected: (String) -> Unit,
    onError: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var cameraControlRef by remember { mutableStateOf<Camera?>(null) }
    var cameraProviderRef by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    // Toggle Torch safely whenever torchEnabled changes or camera binds
    LaunchedEffect(torchEnabled, cameraControlRef) {
        try {
            cameraControlRef?.cameraControl?.enableTorch(torchEnabled)
        } catch (e: Exception) {
            Log.w("QrScanner", "Torch not supported or failed", e)
        }
    }

    DisposableEffect(lensFacing) {
        onDispose {
            try {
                cameraProviderRef?.unbindAll()
                cameraExecutor.shutdown()
            } catch (e: Exception) {
                Log.e("QrScanner", "Error cleaning up camera", e)
            }
        }
    }

    androidx.compose.runtime.key(lensFacing) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }

                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                cameraProviderFuture.addListener({
                    try {
                        val cameraProvider = cameraProviderFuture.get()
                        cameraProviderRef = cameraProvider
                        cameraProvider.unbindAll()

                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }

                        val barcodeScanner = BarcodeScanning.getClient(
                            BarcodeScannerOptions.Builder()
                                .setBarcodeFormats(
                                    Barcode.FORMAT_QR_CODE,
                                    Barcode.FORMAT_DATA_MATRIX,
                                    Barcode.FORMAT_AZTEC,
                                    Barcode.FORMAT_CODE_128,
                                    Barcode.FORMAT_CODE_39
                                )
                                .build()
                        )

                        val imageAnalysis = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy: ImageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null && isScanningActive) {
                                val inputImage = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                barcodeScanner.process(inputImage)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            val rawValue = barcode.rawValue
                                            if (!rawValue.isNullOrBlank()) {
                                                onCodeDetected(rawValue)
                                                break
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("QrScanner", "Barcode scanning failure", e)
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        }

                        // Robust camera selection: supports back camera, front camera, or virtual camera fallback
                        val cameraSelector = when {
                            lensFacing == CameraSelector.LENS_FACING_FRONT && cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            }
                            lensFacing == CameraSelector.LENS_FACING_BACK && cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                            cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) -> {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                            cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) -> {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            }
                            else -> {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            }
                        }

                        val camera = cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                        cameraControlRef = camera

                    } catch (e: Exception) {
                        Log.e("QrScanner", "Use case binding failed", e)
                        onError(e.localizedMessage ?: "Camera initialization failed")
                    }
                }, ContextCompat.getMainExecutor(ctx))

                previewView
            },
            modifier = Modifier
                .fillMaxSize()
                .testTag("camera_preview_view")
        )
    }
}

/**
 * Viewfinder overlay with semi-transparent background, clear center aperture,
 * neon blue corner brackets and pulsing scan laser.
 */
@Composable
fun ScannerOverlay(
    isScanning: Boolean,
    currentLanguage: AppLanguage
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scanner_laser")
    val laserPosition by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laser_pos"
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()
        val boxSize = (width * 0.72f).coerceAtMost(300f * 3f)
        val left = (width - boxSize) / 2f
        val top = (height - boxSize) / 2.6f
        val right = left + boxSize
        val bottom = top + boxSize

        Canvas(modifier = Modifier.fillMaxSize()) {
            // Darkened backdrop outside target box
            drawRect(
                color = Color.Black.copy(alpha = 0.65f),
                size = Size(width, height)
            )

            // Transparent cut-out aperture
            drawRoundRect(
                color = Color.Transparent,
                topLeft = Offset(left, top),
                size = Size(boxSize, boxSize),
                cornerRadius = CornerRadius(16f, 16f),
                blendMode = BlendMode.Clear
            )

            // Neon cyan / blue border
            drawRoundRect(
                color = ElectricBlueAccent.copy(alpha = 0.7f),
                topLeft = Offset(left, top),
                size = Size(boxSize, boxSize),
                cornerRadius = CornerRadius(16f, 16f),
                style = Stroke(width = 3.dp.toPx())
            )

            // Highlighted Corner Brackets
            val cornerLength = 36.dp.toPx()
            val strokeW = 5.dp.toPx()

            // Top-Left
            drawLine(ElectricBlueAccent, Offset(left, top), Offset(left + cornerLength, top), strokeW)
            drawLine(ElectricBlueAccent, Offset(left, top), Offset(left, top + cornerLength), strokeW)
            // Top-Right
            drawLine(ElectricBlueAccent, Offset(right, top), Offset(right - cornerLength, top), strokeW)
            drawLine(ElectricBlueAccent, Offset(right, top), Offset(right, top + cornerLength), strokeW)
            // Bottom-Left
            drawLine(ElectricBlueAccent, Offset(left, bottom), Offset(left + cornerLength, bottom), strokeW)
            drawLine(ElectricBlueAccent, Offset(left, bottom), Offset(left, bottom - cornerLength), strokeW)
            // Bottom-Right
            drawLine(ElectricBlueAccent, Offset(right, bottom), Offset(right - cornerLength, bottom), strokeW)
            drawLine(ElectricBlueAccent, Offset(right, bottom), Offset(right, bottom - cornerLength), strokeW)

            // Animated Laser Line
            if (isScanning) {
                val currentLaserY = top + (boxSize * laserPosition)
                drawLine(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Transparent,
                            ElectricBlueAccent,
                            Color.White,
                            ElectricBlueAccent,
                            Color.Transparent
                        )
                    ),
                    start = Offset(left + 8f, currentLaserY),
                    end = Offset(right - 8f, currentLaserY),
                    strokeWidth = 3.dp.toPx()
                )
            }
        }

        // Instructions below scanner box
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = IndustrialNavy.copy(alpha = 0.9f),
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCodeScanner,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.QR_INSTRUCTION, currentLanguage),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

/**
 * Displayed when camera permissions are needed.
 */
@Composable
fun CameraPermissionRequestView(
    currentLanguage: AppLanguage,
    isDenied: Boolean,
    onRequestPermission: () -> Unit,
    onPickImage: () -> Unit = {},
    onSwitchToManual: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = if (isDenied) WarningAmber.copy(alpha = 0.15f) else ElectricBlueAccent.copy(alpha = 0.15f),
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isDenied) Icons.Default.Warning else Icons.Default.CameraAlt,
                    contentDescription = null,
                    tint = if (isDenied) WarningAmber else ElectricBlueAccent,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = LocaleStrings.get(LocaleStrings.QR_PERMISSION_TITLE, currentLanguage),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = LocaleStrings.get(LocaleStrings.QR_PERMISSION_DESC, currentLanguage),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRequestPermission,
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_grant_camera_permission")
        ) {
            Icon(imageVector = Icons.Default.CameraAlt, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = LocaleStrings.get(LocaleStrings.QR_PERMISSION_GRANT, currentLanguage),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Option to pick QR code image from device gallery
        OutlinedButton(
            onClick = onPickImage,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_pick_gallery_image")
        ) {
            Icon(imageVector = Icons.Default.PhotoLibrary, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = when (currentLanguage) {
                    AppLanguage.SINHALA -> "ගැලරියෙන් පින්තූරයක් තෝරන්න (Gallery)"
                    AppLanguage.TAMIL -> "கேலரியில் இருந்து படம் தேர்வு (Gallery)"
                    AppLanguage.ENGLISH -> "Choose Image from Gallery"
                },
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Requirement 8: Manual Entry Option
        OutlinedButton(
            onClick = onSwitchToManual,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_switch_to_manual_fallback")
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, currentLanguage),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/**
 * Camera Error Fallback View (Requirement 10).
 */
@Composable
fun CameraErrorFallbackView(
    errorMessage: String,
    currentLanguage: AppLanguage,
    onSwitchToManual: () -> Unit,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = WarningAmber.copy(alpha = 0.2f),
            modifier = Modifier.size(72.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = WarningAmber,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = LocaleStrings.get(LocaleStrings.QR_CAMERA_ERROR, currentLanguage),
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily.Monospace,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSwitchToManual,
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, currentLanguage),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onRetry,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = LocaleStrings.get(LocaleStrings.QR_SCAN_AGAIN, currentLanguage))
        }
    }
}

/**
 * Manual link / code input form (Requirement 8).
 */
@Composable
fun ManualEntryView(
    inputText: String,
    error: String?,
    currentLanguage: AppLanguage,
    onTextChanged: (String) -> Unit,
    onSubmit: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = IndustrialNavyCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent.copy(alpha = 0.4f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = ElectricBlueAccent,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (currentLanguage) {
                        AppLanguage.SINHALA -> "කැමරා අවසර නොමැති විට හෝ අඳුරු ආලෝක තත්ත්වයකදී, මැෂිමේ ඇති QR ලින්ක් එක හෝ කේතය (උදා: Juki DDL-8700 හෝ https://...) මෙහි ටයිප් කර සෙවුම් කළ හැක."
                        AppLanguage.TAMIL -> "இணைப்பு அல்லது மாதிரி குறியீட்டை இங்கே உள்ளிடவும்."
                        AppLanguage.ENGLISH -> "Type or paste a manual link, PDF URL, or sewing machine model code directly to view resources."
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White.copy(alpha = 0.8f),
                        lineHeight = 18.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = onTextChanged,
            label = { Text(LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_TITLE, currentLanguage)) },
            placeholder = {
                Text(
                    LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_HINT, currentLanguage),
                    fontSize = 13.sp
                )
            },
            isError = error != null,
            supportingText = {
                if (error != null) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                }
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = null,
                    tint = ElectricBlueAccent
                )
            },
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = { onTextChanged("") }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Clear")
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onSubmit(inputText) }),
            singleLine = false,
            maxLines = 3,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("input_qr_manual")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSubmit(inputText) },
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("btn_submit_qr_manual")
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = LocaleStrings.get(LocaleStrings.QR_MANUAL_ENTRY_SUBMIT, currentLanguage),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick Preset Suggestions for technicians
        Text(
            text = when (currentLanguage) {
                AppLanguage.SINHALA -> "නිතර භාවිතා වන මැෂින් මාදිලි පරීක්ෂාව:"
                AppLanguage.TAMIL -> "பொதுவான மாதிரிகள்:"
                AppLanguage.ENGLISH -> "Common Models Quick Check:"
            },
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val commonPresets = listOf(
            "Juki DDL-8700",
            "Pegasus M900",
            "Yamato VC2700",
            "Brother S-7200C",
            "Siruba 747K"
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Live App Web URL preset
            val appWebUrl = "https://ais-pre-qi7jwprmjuil2byda6fkod-917057435570.asia-east1.run.app"
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = IndustrialNavyCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlueAccent),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onTextChanged(appWebUrl)
                        onSubmit(appWebUrl)
                    }
                    .testTag("preset_app_url")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = ElectricBlueAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Karmika Danuma Live App URL",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Text(
                                text = appWebUrl,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = ElectricBlueAccent,
                                    fontSize = 11.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MachineGreen.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "LIVE URL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MachineGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            commonPresets.forEach { preset ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            onTextChanged(preset)
                            onSubmit(preset)
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = WarningAmber,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = preset,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                        Text(
                            text = when (currentLanguage) {
                                AppLanguage.SINHALA -> "තෝරන්න"
                                AppLanguage.TAMIL -> "தேர்க"
                                AppLanguage.ENGLISH -> "Select"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(color = ElectricBlueAccent)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Result Card displaying the scanned QR code in Sinhala,
 * with trusted URL verification and search options.
 * Requirements: 6, 7
 */
@Composable
fun ScannedResultCard(
    scannedText: String,
    currentLanguage: AppLanguage,
    articles: List<TechnicalArticle>,
    onDismiss: () -> Unit,
    onOpenLinkConfirmed: (String) -> Unit,
    onSearchMachine: (String) -> Unit,
    onArticleSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    val trimmed = scannedText.trim()
    val isUrl = trimmed.startsWith("http://", ignoreCase = true) || trimmed.startsWith("https://", ignoreCase = true)

    // Check if this matches an in-app article or article ID
    val matchingArticle = remember(trimmed, articles) {
        // Direct ID match?
        val asId = trimmed.toLongOrNull()
        if (asId != null) {
            articles.firstOrNull { it.id == asId }
        } else {
            // Model match?
            articles.firstOrNull {
                it.machineType.contains(trimmed, ignoreCase = true) ||
                it.titleEnglish.contains(trimmed, ignoreCase = true) ||
                it.titleSinhala.contains(trimmed, ignoreCase = true)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() }
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = false) {}
                .testTag("scanned_result_card")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header (In Sinhala: "ස්කෑන් කරන ලද QR ප්‍රතිඵලය")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = CircleShape,
                            color = MachineGreen.copy(alpha = 0.15f),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MachineGreen,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = LocaleStrings.get(LocaleStrings.QR_RESULT_TITLE, currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = if (isUrl) LocaleStrings.get(LocaleStrings.QR_RESULT_URL_TYPE, currentLanguage)
                                else LocaleStrings.get(LocaleStrings.QR_RESULT_TEXT_TYPE, currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isUrl) ElectricBlueAccent else WarningAmber,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("btn_close_scanned_result")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Close / Scan Again")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // The Scanned Content Box
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = trimmed,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 6,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Copy Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = {
                                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                    val clip = ClipData.newPlainText("QR Code", trimmed)
                                    clipboard.setPrimaryClip(clip)
                                    Toast.makeText(
                                        context,
                                        LocaleStrings.get(LocaleStrings.QR_COPIED, currentLanguage),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier.testTag("btn_copy_scanned_text")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = LocaleStrings.get(LocaleStrings.QR_COPY_RESULT, currentLanguage),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // If matches an in-app article directly
                if (matchingArticle != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MachineGreen.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MachineGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onArticleSelected(matchingArticle.id) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MachineGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> "අදාළ අත්පොත / මාර්ගෝපදේශය හමුවිය!"
                                        AppLanguage.TAMIL -> "பொருந்தும் கையேடு கிடைத்தது!"
                                        AppLanguage.ENGLISH -> "Matching manual found!"
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MachineGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = when (currentLanguage) {
                                        AppLanguage.SINHALA -> matchingArticle.titleSinhala
                                        AppLanguage.TAMIL -> matchingArticle.titleTamil
                                        AppLanguage.ENGLISH -> matchingArticle.titleEnglish
                                    },
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons: Open Link (with confirmation) or Search Machine Model
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isUrl) {
                        Button(
                            onClick = { onOpenLinkConfirmed(trimmed) },
                            colors = ButtonDefaults.buttonColors(containerColor = MachineGreen),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("btn_open_scanned_link")
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.QR_OPEN_LINK, currentLanguage),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    } else {
                        Button(
                            onClick = { onSearchMachine(trimmed) },
                            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlueAccent),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("btn_search_scanned_machine")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = LocaleStrings.get(LocaleStrings.QR_SEARCH_MACHINE, currentLanguage),
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("btn_scan_again")
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = LocaleStrings.get(LocaleStrings.QR_SCAN_AGAIN, currentLanguage),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
