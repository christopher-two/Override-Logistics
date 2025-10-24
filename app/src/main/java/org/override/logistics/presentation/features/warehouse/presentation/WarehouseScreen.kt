package org.override.logistics.presentation.features.warehouse.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.override.logistics.presentation.features.warehouse.domain.Order
import org.override.logistics.presentation.features.warehouse.domain.OrderStatus
import org.override.logistics.presentation.features.warehouse.presentation.components.DispatchDashboard
import org.override.logistics.presentation.theme.DocsTheme

@Composable
fun WarehouseRoot(
    viewModel: WarehouseViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WarehouseScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WarehouseScreen(
    state: WarehouseState,
    onAction: (WarehouseAction) -> Unit,
) {
    // Datos de ejemplo
    val sampleOrders = remember {
        listOf(
            Order("78901", "Ana Torres", "DHL", "25/09/2025", OrderStatus.PENDING),
            Order("78902", "Luis Vera", "FedEx", "25/09/2025", OrderStatus.PENDING),
            Order("78805", "Carlos Paz", "Estafeta", "24/09/2025", OrderStatus.IN_PROGRESS),
            Order("78806", "Sofía Marín", "DHL", "24/09/2025", OrderStatus.IN_PROGRESS),
            Order("78771", "Juan Gómez", "UPS", "23/09/2025", OrderStatus.COMPLETED),
            Order("78770", "María López", "FedEx", "22/09/2025", OrderStatus.COMPLETED),
        )
    }

    // Estado de la UI
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<OrderStatus?>(null) } // null = Todos
    var showNotification by remember { mutableStateOf(true) }
    var selectedOrder by remember { mutableStateOf<Order?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Simular que la notificación desaparece después de un tiempo
    LaunchedEffect(showNotification) {
        if (showNotification) {
            delay(5000) // Espera 5 segundos
            showNotification = false
        }
    }

    // Lógica de filtrado combinada
    val filteredOrders = remember(searchQuery, selectedStatus, sampleOrders) {
        sampleOrders.filter { order ->
            val matchesSearch = if (searchQuery.isBlank()) {
                true
            } else {
                order.id.contains(searchQuery, ignoreCase = true) ||
                        order.clientName.contains(searchQuery, ignoreCase = true) ||
                        order.carrierName.contains(searchQuery, ignoreCase = true)
            }
            val matchesStatus = if (selectedStatus == null) {
                true
            } else {
                order.status == selectedStatus
            }
            matchesSearch && matchesStatus
        }
    }

    DispatchDashboard(
        orders = filteredOrders,
        searchQuery = searchQuery,
        selectedStatus = selectedStatus,
        selectedOrder = selectedOrder,
        showNewOrderNotification = showNotification,
        sheetState = sheetState,
        onStatusSelected = { newStatus -> selectedStatus = newStatus },
        onSearchQueryChange = { newQuery -> searchQuery = newQuery },
        onDismissNotification = { showNotification = false },
        onOrderClick = { order ->
            selectedOrder = order
        },
        onChangeStatus = { orderStatus -> },
        onDismissSheetState = { selectedOrder = null }
    )
}

@Preview
@Composable
private fun Preview() {
    DocsTheme {
        WarehouseScreen(
            state = WarehouseState(),
            onAction = {}
        )
    }
}