package org.override.logistics.features.wharehouse.presentation.components.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.logistics.core.ui.DetailRow
import org.override.logistics.features.wharehouse.domain.Order

/**
 * Muestra las filas de detalles (Cliente, Transportista, Fecha) y el estado.
 */
@Composable
fun OrderDetailSection(order: Order) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailRow("CLIENTE:", order.clientName)
        DetailRow("TRANSPORTISTA:", order.carrierName)
        DetailRow("FECHA:", order.date)

        Spacer(modifier = Modifier.height(8.dp))

        // Indicador de estado estilo brutalista
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(order.status.color.copy(alpha = 0.2f))
                .border(2.dp, colorScheme.onSurface)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = order.status.displayName,
                color = order.status.color,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 1.sp
            )
        }
    }
}