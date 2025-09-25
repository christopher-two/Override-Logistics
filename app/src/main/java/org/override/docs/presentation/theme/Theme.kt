package org.override.docs.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle

@Composable
fun DocsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = Color(0xFF0061A4),
        isDark = darkTheme,
        style = PaletteStyle.Monochrome,
        content = {
            Surface(
                color = colorScheme.background,
                contentColor = colorScheme.onBackground,
                modifier = Modifier.fillMaxSize(),
                content = {
                    content()
                }
            )
        }
    )
}