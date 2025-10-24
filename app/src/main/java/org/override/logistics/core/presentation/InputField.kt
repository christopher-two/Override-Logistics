package org.override.logistics.core.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true,
        shape = RectangleShape,
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
}

