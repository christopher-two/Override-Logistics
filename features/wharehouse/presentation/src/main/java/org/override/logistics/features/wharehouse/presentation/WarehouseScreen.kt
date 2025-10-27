package org.override.logistics.features.wharehouse.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.override.logistics.core.ui.theme.OverrideLogisticsTheme
import org.override.logistics.features.wharehouse.presentation.components.dispatch.DispatchDashboard
import org.override.logistics.features.wharehouse.presentation.components.dispatch.DispatchDashboardAction
import org.override.logistics.features.wharehouse.presentation.components.dispatch.DispatchDashboardState

@Composable
fun WarehouseRoot(
    viewModel: WarehouseViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dispatchDashboardState by viewModel.dispatchDashboardState.collectAsStateWithLifecycle()
    val filteredOrders by viewModel.filteredOrders.collectAsStateWithLifecycle()

    WarehouseScreen(
        state = state,
        onAction = viewModel::onAction,
        dispatchDashboardState = dispatchDashboardState,
        onDispatchDashboardAction = viewModel::onDispatchDashboardAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WarehouseScreen(
    state: WarehouseState,
    dispatchDashboardState: DispatchDashboardState,
    onAction: (WarehouseAction) -> Unit,
    onDispatchDashboardAction: (DispatchDashboardAction) -> Unit,
) {
    // Simular que la notificación desaparece después de un tiempo
    LaunchedEffect(dispatchDashboardState.isNotification) {
        if (dispatchDashboardState.isNotification) {
            delay(5000) // Espera 5 segundos
            onDispatchDashboardAction(DispatchDashboardAction.OnDismissNotification)
        }
    }


    DispatchDashboard(
        dispatchDashboardState = dispatchDashboardState,
        dispatchDashboardAction = onDispatchDashboardAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun Preview() {
    OverrideLogisticsTheme {
        WarehouseScreen(
            state = WarehouseState(),
            onAction = {},
            dispatchDashboardState = DispatchDashboardState(),
            onDispatchDashboardAction = {}
        )
    }
}