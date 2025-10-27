package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.Order

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
