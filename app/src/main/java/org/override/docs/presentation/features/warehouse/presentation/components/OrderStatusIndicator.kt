package org.override.docs.presentation.features.warehouse.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.docs.presentation.features.warehouse.domain.OrderStatus

/**
 * Indicador visual para el estado de un pedido (círculo de color y texto).
 *
 * @param status El estado del pedido a representar.
 */
@Composable
fun OrderStatusIndicator(status: OrderStatus) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(status.color.copy(alpha = 0.1f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(status.color, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status.displayName,
                color = status.color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
