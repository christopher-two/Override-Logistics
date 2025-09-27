package org.override.docs.presentation.features.warehouse.presentation.components

import android.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Search

/**
 * Barra con campo de texto para búsqueda y botones para filtros (funcionalidad de filtros pendiente).
 *
 * @param searchQuery Texto actual de búsqueda.
 * @param onSearchQueryChange Lambda que se invoca cuando el texto cambia.
 * @param modifier Modificador de Compose.
 */
@Composable
fun SearchAndFilterBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text("Buscar por pedido, cliente...") },
        leadingIcon = {
            Icon(
                FontAwesomeIcons.Solid.Search,
                contentDescription = "Buscar",
                modifier = Modifier.size(24.dp)
            )
        },
        shape = RectangleShape,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorScheme.outline,
            unfocusedBorderColor = colorScheme.outlineVariant,
            cursorColor = colorScheme.primary,
            focusedTrailingIconColor = colorScheme.primary,
            unfocusedTrailingIconColor = colorScheme.onBackground,
            focusedTextColor = colorScheme.onSurface,
            unfocusedTextColor = colorScheme.onSurface,
            focusedLeadingIconColor = colorScheme.primary,
            unfocusedLeadingIconColor = colorScheme.onBackground,
            focusedPlaceholderColor = colorScheme.onBackground,
            unfocusedPlaceholderColor = colorScheme.onBackground,
            focusedSupportingTextColor = colorScheme.onBackground,
            unfocusedSupportingTextColor = colorScheme.onBackground,
            focusedContainerColor = colorScheme.surfaceContainerLowest,
            unfocusedContainerColor = colorScheme.surfaceContainerLowest
        )
    )
    // Aquí se podrían agregar botones o un DropdownMenu para los filtros.
}
