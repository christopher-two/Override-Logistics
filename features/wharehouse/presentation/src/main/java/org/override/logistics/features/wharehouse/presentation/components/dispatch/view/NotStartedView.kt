package org.override.logistics.features.wharehouse.presentation.components.dispatch.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.core.ui.BrutalistButton

@Composable
fun NotStartedView(order: Order, onStartDispatch: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text(
            "INICIAR DESPACHO",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
        )
        Text("ORDEN #${order.id}", fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        BrutalistButton(
            text = "INICIAR DESPACHO AHORA",
            onClick = onStartDispatch,
            backgroundColor = Color(0xFF388E3C) // Verde
        )
    }
}
