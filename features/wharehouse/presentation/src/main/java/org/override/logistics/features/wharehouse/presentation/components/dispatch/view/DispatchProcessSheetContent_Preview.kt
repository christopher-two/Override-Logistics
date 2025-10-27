package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus

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

