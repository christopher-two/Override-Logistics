package org.override.logistics.features.wharehouse.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Check
import org.override.logistics.features.wharehouse.domain.OrderStatus

/**
 * Muestra una fila horizontal de chips para filtrar por estado.
 *
 * @param selectedStatus El estado actualmente seleccionado.
 * @param onStatusSelected Lambda que se invoca al seleccionar un estado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusFilterRow(
    selectedStatus: OrderStatus?,
    onStatusSelected: (OrderStatus?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Chip para "Todos"
        item {
            val isSelected = selectedStatus == null
            FilterChip(
                selected = isSelected,
                shape = RectangleShape,
                border = BorderStroke(2.dp, colorScheme.onBackground),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = colorScheme.surfaceContainerLowest,
                    labelColor = colorScheme.onSurface
                ),
                onClick = { onStatusSelected(null) },
                label = { Text("Todos") },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            FontAwesomeIcons.Solid.Check,
                            contentDescription = "Seleccionado",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else null
            )
        }
        // Chips para cada estado
        items(OrderStatus.entries.toTypedArray()) { status ->
            val isSelected = selectedStatus == status
            FilterChip(
                selected = isSelected,
                shape = RectangleShape,
                border = BorderStroke(2.dp, colorScheme.onBackground),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = colorScheme.surfaceContainerLowest,
                    labelColor = colorScheme.onSurface
                ),
                onClick = { onStatusSelected(status) },
                label = { Text(status.displayName) },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            FontAwesomeIcons.Solid.Check,
                            contentDescription = "Seleccionado",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                } else null
            )
        }
    }
}