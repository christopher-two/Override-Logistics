package org.override.logistics.features.wharehouse.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus

/**
 * Representa una tarjeta individual para un pedido en la lista.
 *
 * @param order El objeto de pedido a mostrar.
 * @param onClick Lambda que se invoca al hacer clic en la tarjeta.
 */
@Composable
fun OrderItem(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(
            width = 2.dp,
            color = colorScheme.onBackground
        ),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.surfaceContainerLowest,
            contentColor = colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Pedido #${order.id}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Cliente: ${order.clientName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Transportista: ${order.carrierName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Fecha: ${order.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            OrderStatusIndicator(status = order.status)
        }
    }
}

@Preview
@Composable
fun OrderItemPreview() {
    val order = Order(
        id = "12345",
        clientName = "Juan Pérez",
        carrierName = "Transportes Rápidos",
        date = "2024-07-26",
        status = OrderStatus.PENDING
    )
    OrderItem(order = order, onClick = {})
}


