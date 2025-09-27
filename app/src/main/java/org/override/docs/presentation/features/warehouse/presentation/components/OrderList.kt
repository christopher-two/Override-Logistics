package org.override.docs.presentation.features.warehouse.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.docs.presentation.features.warehouse.domain.Order

/**
 * Muestra la lista de pedidos usando un LazyColumn para optimizar el rendimiento.
 *
 * @param orders Lista de pedidos.
 * @param onOrderClick Lambda invocada al hacer clic en un item.
 * @param modifier Modificador de Compose.
 */
@Composable
fun OrderList(
    orders: List<Order>,
    onOrderClick: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        // Agrupación por estado
        val groupedOrders = orders.groupBy { it.status }

        groupedOrders.forEach { (status, ordersInStatus) ->
            item {
                Text(
                    text = status.displayName,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }
            items(items = ordersInStatus, key = { it.id }) { order ->
                OrderItem(order = order, onClick = { onOrderClick(order) })
            }
        }
    }
}
