package org.override.logistics.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
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

@Composable
fun ChipBrutalism(
    label: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        shape = RectangleShape,
        border = BorderStroke(2.dp, colorScheme.onBackground),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colorScheme.surfaceContainerLowest,
            labelColor = colorScheme.onSurface
        ),
        onClick = { onSelected() },
        label = { Text(label) },
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