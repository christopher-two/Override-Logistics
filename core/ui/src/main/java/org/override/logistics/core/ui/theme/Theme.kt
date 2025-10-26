package org.override.logistics.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.materialkolor.DynamicMaterialTheme
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamiccolor.ColorSpec

@Composable
fun OverrideLogisticsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    DynamicMaterialTheme(
        seedColor = Color(0xFFF44336),
        isDark = darkTheme,
        style = PaletteStyle.TonalSpot,
        specVersion = ColorSpec.SpecVersion.SPEC_2025,
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