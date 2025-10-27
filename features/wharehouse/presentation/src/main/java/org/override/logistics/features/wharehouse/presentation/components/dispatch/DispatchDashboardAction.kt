package org.override.logistics.features.wharehouse.presentation.components.dispatch

import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus

sealed interface DispatchDashboardAction {
    data class OnTimerChanged(val value: String) : DispatchDashboardAction
    data class OnTimerRunning(val isRunning: Boolean) : DispatchDashboardAction
    data class OnStepChanged(val step: DispatchStep) : DispatchDashboardAction
    data class OnChangeStatus(val status: OrderStatus) : DispatchDashboardAction
    data class OnSearchQueryChange(val query: String) : DispatchDashboardAction
    data class OnStatusSelected(val status: OrderStatus?) : DispatchDashboardAction
    data class OnOrderClick(val order: Order?) : DispatchDashboardAction
    object OnDismissNotification : DispatchDashboardAction
}