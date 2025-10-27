package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.logistics.core.ui.BrutalistButton

@Composable
fun FinalizingView(onConfirmSignature: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "FINALIZACIÓN Y FIRMA",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Text(
            "Realiza tu firma para validar la supervisión.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))

        // Simulación de pad de firma
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(2.dp, Color.Black)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "FIRMA AQUÍ",
                color = Color.LightGray,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(24.dp))
        BrutalistButton(
            text = "CONFIRMAR FIRMA Y COMPLETAR",
            onClick = onConfirmSignature,
            backgroundColor = Color.Black
        )
    }
}