@file:OptIn(ExperimentalMaterial3Api::class)

package org.override.logistics.features.wharehouse.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus
import org.override.logistics.features.wharehouse.presentation.components.dispatch.DispatchDashboardAction
import org.override.logistics.features.wharehouse.presentation.components.dispatch.DispatchDashboardState

class WarehouseViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(WarehouseState())

    private val _dispatchDashboardState = MutableStateFlow(DispatchDashboardState())

    // Datos de ejemplo - órdenes disponibles
    private val sampleOrders = listOf(
        Order("78901", "Ana Torres", "DHL", "25/09/2025", OrderStatus.PENDING),
        Order("78902", "Luis Vera", "FedEx", "25/09/2025", OrderStatus.PENDING),
        Order("78805", "Carlos Paz", "Estafeta", "24/09/2025", OrderStatus.IN_PROGRESS),
        Order("78806", "Sofía Marín", "DHL", "24/09/2025", OrderStatus.IN_PROGRESS),
        Order("78771", "Juan Gómez", "UPS", "23/09/2025", OrderStatus.COMPLETED),
        Order("78770", "María López", "FedEx", "22/09/2025", OrderStatus.COMPLETED),
    )

    // Órdenes filtradas basadas en búsqueda y estado
    val filteredOrders = _dispatchDashboardState
        .map { dashboardState ->
            val orders = sampleOrders.filter { order ->
                val matchesSearch = if (dashboardState.searchQuery.isNullOrBlank()) {
                    true
                } else {
                    order.id.contains(dashboardState.searchQuery, ignoreCase = true) ||
                            order.clientName.contains(dashboardState.searchQuery, ignoreCase = true) ||
                            order.carrierName.contains(dashboardState.searchQuery, ignoreCase = true)
                }
                val matchesStatus = if (dashboardState.selectedStatus == null) {
                    true
                } else {
                    order.status == dashboardState.selectedStatus
                }
                matchesSearch && matchesStatus
            }

            _dispatchDashboardState.update { it.copy(orders = orders) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = sampleOrders
        )

    val dispatchDashboardState = _dispatchDashboardState
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = DispatchDashboardState()
        )

    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = WarehouseState()
        )

    fun onAction(action: WarehouseAction) {
        when (action) {
            is WarehouseAction -> {} // No actions defined yet
        }
    }

    fun onDispatchDashboardAction(action: DispatchDashboardAction) {
        when (action) {
            is DispatchDashboardAction.OnChangeStatus -> {
                _dispatchDashboardState.update { it.copy(selectedStatus = action.status) }
            }

            is DispatchDashboardAction.OnOrderClick -> {
                _dispatchDashboardState.update { it.copy(selectedOrder = action.order) }
            }

            is DispatchDashboardAction.OnStepChanged -> {
                _dispatchDashboardState.update { it.copy(currentStep = action.step) }
            }

            is DispatchDashboardAction.OnTimerChanged -> {
                _dispatchDashboardState.update { it.copy(timerValue = action.value) }
            }

            is DispatchDashboardAction.OnTimerRunning -> {
                _dispatchDashboardState.update { it.copy(timerRunning = action.isRunning) }
            }

            is DispatchDashboardAction.OnDismissNotification -> {
                _dispatchDashboardState.update { it.copy(isNotification = false) }
            }

            is DispatchDashboardAction.OnStatusSelected -> {
                _dispatchDashboardState.update { it.copy(selectedStatus = action.status) }
            }

            is DispatchDashboardAction.OnSearchQueryChange -> {
                _dispatchDashboardState.update { it.copy(searchQuery = action.query) }
            }
        }
    }
}