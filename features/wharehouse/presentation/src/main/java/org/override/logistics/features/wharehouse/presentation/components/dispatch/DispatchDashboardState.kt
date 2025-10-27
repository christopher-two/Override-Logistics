package org.override.logistics.features.wharehouse.presentation.components.dispatch

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Immutable
import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus

@OptIn(ExperimentalMaterial3Api::class)
@Immutable
data class DispatchDashboardState(
    val orders: List<Order>? = null,
    val searchQuery: String? = null,
    val selectedStatus: OrderStatus? = null,
    val showNewOrderNotification: Boolean = false,
    val selectedOrder: Order? = null,
    val currentStep: DispatchStep = DispatchStep.NOT_STARTED,
    val timerValue: String = "00:00:00",
    val timerRunning: Boolean = false,
    val isNotification: Boolean = false,
)