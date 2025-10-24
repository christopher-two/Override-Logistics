package org.override.logistics.presentation.features.warehouse.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.override.logistics.presentation.features.warehouse.domain.Order
import org.override.logistics.presentation.features.warehouse.domain.OrderStatus

/**
 * Contenido para un BottomSheet que muestra los detalles de una orden con estilo brutalista.
 *
 * @param order La orden a mostrar.
 * @param onDismiss Lambda para solicitar el cierre del BottomSheet.
 * @param onChangeStatus Lambda que se invoca al presionar un botón para cambiar el estado.
 * @param modifier Modificador de Compose.
 */
@Composable
fun OrderDetailSheetContent(
    order: Order,
    onDismiss: () -> Unit,
    onChangeStatus: (OrderStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = colorScheme.surface,
        contentColor = colorScheme.onSurface,
        modifier = modifier
            .fillMaxSize()
            .border(2.dp, colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal - ID del pedido
            Text(
                text = "ORDEN #${order.id}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = 2.dp, color = colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            // Sección de detalles
            OrderDetailSection(order = order)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de cierre
            Button(
                onClick = onDismiss,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.surfaceContainer,
                    contentColor = colorScheme.onSurface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .border(2.dp, colorScheme.onSurface)
            ) {
                Text(
                    text = "CERRAR",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
private fun OrderDetailSheetContent_Preview() {
    var sampleOrder by remember {
        mutableStateOf(
            Order(
                id = "78805",
                clientName = "Carlos Paz",
                carrierName = "Estafeta",
                date = "24/09/2025",
                status = OrderStatus.IN_PROGRESS
            )
        )
    }

    // Simulamos el BottomSheet para el Preview
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        OrderDetailSheetContent(
            order = sampleOrder,
            onDismiss = { /* Acción de cierre */ },
            onChangeStatus = { newStatus ->
                // En una app real, esto lo manejaría el ViewModel.
                // Aquí actualizamos el estado local para ver el cambio en el Preview.
                sampleOrder = sampleOrder.copy(status = newStatus)
            }
        )
    }
}