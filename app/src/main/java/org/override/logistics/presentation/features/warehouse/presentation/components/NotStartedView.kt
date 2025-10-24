package org.override.logistics.presentation.features.warehouse.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ExclamationTriangle
import compose.icons.fontawesomeicons.solid.Image
import kotlinx.coroutines.delay
import org.override.logistics.presentation.features.warehouse.domain.DispatchStep
import org.override.logistics.presentation.features.warehouse.domain.Order
import org.override.logistics.presentation.features.warehouse.domain.OrderStatus

@Composable
fun NotStartedView(order: Order, onStartDispatch: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "INICIAR DESPACHO",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Text("ORDEN #${order.id}", fontSize = 16.sp, color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        BrutalistButton(
            text = "INICIAR DESPACHO AHORA",
            onClick = onStartDispatch,
            backgroundColor = Color(0xFF388E3C) // Verde
        )
    }
}

@Composable
private fun InProgressView(
    timerValue: String,
    employeeId: String,
    onFinishDispatch: () -> Unit,
    onReportIncident: (description: String, hasPhoto: Boolean) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "DESPACHO EN PROGRESO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            timerValue,
            fontSize = 52.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
        )
        Text("Empleado: $employeeId", fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))

        IncidentReportSection(onReportIncident)

        Spacer(Modifier.height(16.dp))
        BrutalistButton(
            text = "FINALIZAR CARGA",
            onClick = onFinishDispatch,
            backgroundColor = Color(0xFF1976D2) // Azul
        )
    }
}

@Composable
private fun FinalizingView(onConfirmSignature: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "FINALIZACIÓN Y FIRMA",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Text(
            "Realiza tu firma para validar la supervisión.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        // Simulación de pad de firma
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(2.dp, Color.Black)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "FIRMA AQUÍ",
                color = Color.LightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(24.dp))
        BrutalistButton(
            text = "CONFIRMAR FIRMA Y COMPLETAR",
            onClick = onConfirmSignature,
            backgroundColor = Color.Black
        )
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
private fun IncidentReportSection(onReportIncident: (description: String, hasPhoto: Boolean) -> Unit) {
    var description by remember { mutableStateOf("") }
    var photoAttached by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Black.copy(alpha = 0.5f))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¿ALGÚN PROBLEMA?", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Describe la incidencia...") },
            modifier = Modifier.fillMaxWidth(),
            shape = RectangleShape
        )
        Spacer(Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { photoAttached = !photoAttached },
                shape = RectangleShape,
                modifier = Modifier
                    .weight(1f)
                    .border(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (photoAttached) Color.Gray else Color.White,
                    contentColor = if (photoAttached) Color.White else Color.Black
                )
            ) {
                Icon(
                    FontAwesomeIcons.Solid.Image,
                    contentDescription = "Adjuntar Foto",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(if (photoAttached) "FOTO ADJUNTA" else "ADJUNTAR FOTO")
            }
            Button(
                onClick = {
                    onReportIncident(description, photoAttached)
                    // Reset fields after reporting
                    description = ""
                    photoAttached = false
                },
                enabled = description.isNotBlank(),
                shape = RectangleShape,
                modifier = Modifier
                    .weight(1f)
                    .border(2.dp, Color.Black),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFBC02D),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    FontAwesomeIcons.Solid.ExclamationTriangle,
                    contentDescription = "Reportar",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("REPORTAR")
            }
        }
    }
}

// --- PREVIEW ---

@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
fun DispatchProcessSheetContent_Preview() {
    var currentStep by remember { mutableStateOf(DispatchStep.NOT_STARTED) }
    var timerValue by remember { mutableStateOf("00:00:00") }
    var timerRunning by remember { mutableStateOf(false) }

    // Simulación del temporizador
    LaunchedEffect(key1 = timerRunning) {
        var seconds = 0
        while (timerRunning) {
            delay(1000)
            seconds++
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60
            timerValue = String.format("%02d:%02d:%02d", h, m, s)
        }
    }

    val sampleOrder =
        Order("78805", "Carlos Paz", "Estafeta", "24/09/2025", OrderStatus.IN_PROGRESS)

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        DispatchProcessSheetContent(
            order = sampleOrder,
            employeeId = "EMP-4521",
            timerValue = timerValue,
            currentStep = currentStep,
            onStartDispatch = {
                currentStep = DispatchStep.IN_PROGRESS
                timerRunning = true
            },
            onReportIncident = { desc, photo ->
                println("Incidencia reportada: '$desc', Con foto: $photo")
            },
            onFinishDispatch = {
                currentStep = DispatchStep.FINALIZING
                timerRunning = false
            },
            onConfirmSignature = {
                println("Firma confirmada. Proceso completado.")
                currentStep = DispatchStep.NOT_STARTED // Reiniciar para el preview
            },
            onDismiss = {
                currentStep = DispatchStep.NOT_STARTED // Reiniciar para el preview
                timerRunning = false
                timerValue = "00:00:00"
            }
        )
    }
}

/**
 * Contenido para un BottomSheet que gestiona el proceso de despacho.
 *
 * @param order La orden que se está procesando.
 * @param employeeId ID del empleado que realiza el despacho.
 * @param timerValue El valor actual del temporizador (ej. "00:15:32").
 * @param currentStep El paso actual del proceso (NOT_STARTED, IN_PROGRESS, FINALIZING).
 * @param onStartDispatch Lambda que se invoca para iniciar el despacho.
 * @param onReportIncident Lambda que se invoca para reportar una incidencia con un texto y evidencia.
 * @param onFinishDispatch Lambda que se invoca para detener el temporizador y pasar a la firma.
 * @param onConfirmSignature Lambda que se invoca al confirmar la firma.
 * @param onDismiss Lambda para cerrar el sheet.
 */
@Composable
fun DispatchProcessSheetContent(
    order: Order,
    employeeId: String,
    timerValue: String,
    currentStep: DispatchStep,
    onStartDispatch: () -> Unit,
    onReportIncident: (description: String, hasPhoto: Boolean) -> Unit,
    onFinishDispatch: () -> Unit,
    onConfirmSignature: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = colorScheme.surface,
        modifier = modifier
            .fillMaxSize()
            .border(2.dp, colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Contenido dinámico según el paso
            AnimatedContent(targetState = currentStep, label = "DispatchStepAnimation") { step ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    when (step) {
                        DispatchStep.NOT_STARTED -> NotStartedView(
                            order = order,
                            onStartDispatch = onStartDispatch
                        )

                        DispatchStep.IN_PROGRESS -> InProgressView(
                            timerValue = timerValue,
                            employeeId = employeeId,
                            onFinishDispatch = onFinishDispatch,
                            onReportIncident = onReportIncident
                        )

                        DispatchStep.FINALIZING -> FinalizingView(
                            onConfirmSignature = onConfirmSignature
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Botón de cierre siempre visible
            Button(
                onClick = onDismiss,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.surface,
                    contentColor = colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(2.dp, Color.Black)
            ) {
                Text("CANCELAR PROCESO", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
