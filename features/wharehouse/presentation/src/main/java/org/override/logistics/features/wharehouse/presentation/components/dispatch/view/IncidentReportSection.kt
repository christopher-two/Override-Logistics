package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ExclamationTriangle
import compose.icons.fontawesomeicons.solid.Image
import org.override.logistics.core.ui.ChipBrutalism

@Composable
fun IncidentReportSection(onReportIncident: (description: String, hasPhoto: Boolean) -> Unit) {
    var description by remember { mutableStateOf("") }
    var photoAttached by remember { mutableStateOf(false) }
    var selectedIncident by remember { mutableStateOf<String?>(null) }
    var showCustomField by remember { mutableStateOf(false) }

    val incidenceChips = listOf(
        "Caja Dañada",
        "Producto Faltante",
        "Ubicación Vacía",
        "Error de Sistema"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, colorScheme.outline.copy(alpha = 0.5f))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("¿ALGÚN PROBLEMA?", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            incidenceChips.forEach { chip ->
                ChipBrutalism(
                    label = chip,
                    isSelected = selectedIncident == chip,
                    onSelected = {
                        selectedIncident = chip
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // Campo de texto personalizado si selecciona "Otro..."
        if (showCustomField) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Describe la incidencia personalizada...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape
            )
            Spacer(Modifier.height(8.dp))
        } else {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Describe la incidencia...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape
            )
            Spacer(Modifier.height(8.dp))
        }
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

@Preview
@Composable
fun IncidentReportSectionPreview() {
    IncidentReportSection(onReportIncident = { _, _ -> })
}
