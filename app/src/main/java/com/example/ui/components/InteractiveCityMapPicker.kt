package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.ZoomIn
import androidx.compose.material.icons.filled.ZoomOut
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InteractiveCityMapPicker(
    initialLat: Double = -23.5505,
    initialLng: Double = -46.6333,
    addressLabel: String,
    onLocationChanged: (Double, Double, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var pinOffsetX by remember { mutableFloatStateOf(0.5f) }
    var pinOffsetY by remember { mutableFloatStateOf(0.5f) }
    var zoomLevel by remember { mutableFloatStateOf(1.0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseRadius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseAlpha"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .testTag("interactive_map_picker")
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Interactive Map Canvas rendering realistic styled vector city streets & blocks
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .pointerInput(Unit) {
                        detectTapGestures { tapOffset ->
                            val normX = (tapOffset.x / size.width).coerceIn(0.05f, 0.95f)
                            val normY = (tapOffset.y / size.height).coerceIn(0.05f, 0.95f)
                            pinOffsetX = normX
                            pinOffsetY = normY

                            // Calculate simulated Brazilian municipality coordinates & address
                            val lat = initialLat + ((normY - 0.5f) * 0.01)
                            val lng = initialLng + ((normX - 0.5f) * 0.01)

                            val estimatedNum = (100 + (normX * 1200).toInt())
                            val street = when {
                                normY < 0.33f -> "Av. dos Cidadãos, nº $estimatedNum"
                                normY < 0.66f -> "Rua das Flores, nº $estimatedNum"
                                else -> "Av. Central da República, nº $estimatedNum"
                            }
                            onLocationChanged(lat, lng, street)
                        }
                    }
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Map Base ground color (Soft Map Tile Canvas)
                drawRect(color = Color(0xFFE9F1EE))

                // Draw Municipal Park / Green Area
                val parkPath = Path().apply {
                    moveTo(canvasWidth * 0.05f, canvasHeight * 0.1f)
                    lineTo(canvasWidth * 0.35f, canvasHeight * 0.12f)
                    lineTo(canvasWidth * 0.32f, canvasHeight * 0.42f)
                    lineTo(canvasWidth * 0.04f, canvasHeight * 0.38f)
                    close()
                }
                drawPath(path = parkPath, color = Color(0xFFCBEAD7))

                // Draw Lake / Water Stream
                val waterPath = Path().apply {
                    moveTo(0f, canvasHeight * 0.85f)
                    cubicTo(
                        canvasWidth * 0.3f, canvasHeight * 0.75f,
                        canvasWidth * 0.7f, canvasHeight * 0.95f,
                        canvasWidth, canvasHeight * 0.82f
                    )
                    lineTo(canvasWidth, canvasHeight)
                    lineTo(0f, canvasHeight)
                    close()
                }
                drawPath(path = waterPath, color = Color(0xFFBCE3F7))

                // Draw City Blocks
                val blockColor = Color(0xFFDCE6E1)
                val strokeColor = Color(0xFFCAD6D0)

                // Grid Blocks
                val cols = 4
                val rows = 3
                val blockW = canvasWidth / (cols + 1)
                val blockH = canvasHeight / (rows + 1)

                for (i in 0 until cols) {
                    for (j in 0 until rows) {
                        val left = (i + 0.6f) * (blockW * 0.9f)
                        val top = (j + 0.6f) * (blockH * 0.85f)
                        drawRoundRect(
                            color = blockColor,
                            topLeft = Offset(left, top),
                            size = androidx.compose.ui.geometry.Size(blockW * 0.7f, blockH * 0.65f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                        )
                    }
                }

                // Main Avenues & Roads (Light asphalt / Clean White)
                val roadColor = Color(0xFFFFFFFF)
                val roadStroke = 18f * zoomLevel
                val secRoadStroke = 9f * zoomLevel

                // Horizontal Avenues
                drawLine(
                    color = roadColor,
                    start = Offset(0f, canvasHeight * 0.3f),
                    end = Offset(canvasWidth, canvasHeight * 0.3f),
                    strokeWidth = roadStroke
                )
                drawLine(
                    color = roadColor,
                    start = Offset(0f, canvasHeight * 0.65f),
                    end = Offset(canvasWidth, canvasHeight * 0.65f),
                    strokeWidth = roadStroke
                )

                // Vertical Avenues
                drawLine(
                    color = roadColor,
                    start = Offset(canvasWidth * 0.38f, 0f),
                    end = Offset(canvasWidth * 0.38f, canvasHeight),
                    strokeWidth = roadStroke
                )
                drawLine(
                    color = roadColor,
                    start = Offset(canvasWidth * 0.72f, 0f),
                    end = Offset(canvasWidth * 0.72f, canvasHeight),
                    strokeWidth = roadStroke
                )

                // Diagonal Boulevard
                drawLine(
                    color = roadColor,
                    start = Offset(0f, canvasHeight * 0.1f),
                    end = Offset(canvasWidth, canvasHeight * 0.9f),
                    strokeWidth = secRoadStroke
                )

                // Selected Pin Location
                val pinX = canvasWidth * pinOffsetX
                val pinY = canvasHeight * pinOffsetY

                // Pulse Effect
                drawCircle(
                    color = Color(0xFF0D5C4E).copy(alpha = pulseAlpha),
                    radius = pulseRadius * 1.5f,
                    center = Offset(pinX, pinY)
                )

                // Target Circle
                drawCircle(
                    color = Color(0xFF0D5C4E),
                    radius = 9f,
                    center = Offset(pinX, pinY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 5f,
                    center = Offset(pinX, pinY)
                )
                drawCircle(
                    color = Color(0xFFE27C00),
                    radius = 3f,
                    center = Offset(pinX, pinY)
                )
            }

            // Pin Overlay Icon
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ponto no mapa",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier
                        .size(36.dp)
                        .offset(
                            x = (pinOffsetX * 280).dp - 18.dp,
                            y = (pinOffsetY * 190).dp - 32.dp
                        )
                )
            }

            // Top Instructions Badge
            Surface(
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                shape = RoundedCornerShape(20.dp),
                tonalElevation = 4.dp,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Toque no mapa para marcar o local exato",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Zoom and Recenter Controls
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        zoomLevel = (zoomLevel + 0.2f).coerceAtMost(1.6f)
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.ZoomIn, contentDescription = "Aumentar zoom", modifier = Modifier.size(18.dp))
                }

                SmallFloatingActionButton(
                    onClick = {
                        zoomLevel = (zoomLevel - 0.2f).coerceAtLeast(0.8f)
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.ZoomOut, contentDescription = "Diminuir zoom", modifier = Modifier.size(18.dp))
                }

                SmallFloatingActionButton(
                    onClick = {
                        pinOffsetX = 0.5f
                        pinOffsetY = 0.5f
                        onLocationChanged(initialLat, initialLng, "Av. Central da República, nº 500")
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "Minha localização", modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
