package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.override.logistics.core.ui.BrutalistButton
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun InProgressView(
    timerValue: String,
    employeeId: String,
    onFinishDispatch: () -> Unit,
    onReportIncident: (description: String, hasPhoto: Boolean) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "INICIO DE DESPACHO",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = textoFormateado,
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

// Necesitas optar por la API experimental
fun formatarFechaHora(now: LocalDateTime): String {

    // 1. Definir el formato exacto usando el constructor (builder)
    val formato = LocalDateTime.Format {
        // "HH"
        hour(Padding.ZERO)
        // ":"
        char(':')
        // "mm"
        minute(Padding.ZERO)
        // ":"
        char(':')
        // "ss"
        second(Padding.ZERO)
    }

    // 2. Aplicar el formato
    return formato.format(now)
}

// ---- Cómo usarlo ----
@OptIn(ExperimentalTime::class)
val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
val textoFormateado = formatarFechaHora(now)