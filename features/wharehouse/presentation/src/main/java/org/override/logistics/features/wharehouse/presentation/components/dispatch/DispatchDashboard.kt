package org.override.logistics.features.wharehouse.presentation.components.dispatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.override.logistics.core.ui.search.SearchAndFilterBar
import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.OrderStatus
import org.override.logistics.features.wharehouse.presentation.components.StatusFilterRow
import org.override.logistics.features.wharehouse.presentation.components.dispatch.view.DispatchProcessSheetContent
import org.override.logistics.features.wharehouse.presentation.components.order.NewOrderNotification
import org.override.logistics.features.wharehouse.presentation.components.order.OrderDetailSheetContent
import org.override.logistics.features.wharehouse.presentation.components.order.OrderList

/**
 * Main component that orchestrates the dispatch control panel.
 * It is a "stateless" component, receiving the state and exposing the events.
 *
 * @param dispatchDashboardState The current state of the dispatch dashboard.
 * @param modifier The modifier to be applied to the component.
 * @param dispatchDashboardAction The action to be performed when an event occurs.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatchDashboard(
    dispatchDashboardState: DispatchDashboardState,
    dispatchDashboardAction: (DispatchDashboardAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    // Simulación del temporizador
    LaunchedEffect(key1 = dispatchDashboardState.timerRunning) {
        var seconds = 0
        while (dispatchDashboardState.timerRunning) {
            delay(1000)
            seconds++
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60
            dispatchDashboardAction(
                DispatchDashboardAction.OnTimerChanged("%02d:%02d:%02d".format(h, m, s))
            )
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        snackbarHost = {
            NewOrderNotification(
                visible = dispatchDashboardState.showNewOrderNotification,
                onDismiss = {
                    dispatchDashboardAction(DispatchDashboardAction.OnDismissNotification)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchAndFilterBar(
                    searchQuery = dispatchDashboardState.searchQuery ?: "",
                    onSearchQueryChange = { query ->
                        dispatchDashboardAction(DispatchDashboardAction.OnSearchQueryChange(query))
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                // Fila de filtros por estado
                StatusFilterRow(
                    selectedStatus = dispatchDashboardState.selectedStatus,
                    onStatusSelected = { status ->
                        dispatchDashboardAction(DispatchDashboardAction.OnStatusSelected(status))
                    }
                )
                // Lista de pedidos
                OrderList(
                    orders = dispatchDashboardState.orders ?: emptyList(),
                    onOrderClick = { order ->
                        dispatchDashboardAction(DispatchDashboardAction.OnOrderClick(order))
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            dispatchDashboardState.selectedOrder?.let { order ->
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = {
                        scope.launch {
                            sheetState.hide()
                            dispatchDashboardAction(DispatchDashboardAction.OnOrderClick(null))
                        }
                    },
                    dragHandle = {}
                ) {
                    when (order.status) {
                        OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED -> {
                            OrderDetailSheetContent(
                                order = order,
                                onDismiss = {
                                    scope.launch {
                                        sheetState.hide()
                                        dispatchDashboardAction(DispatchDashboardAction.OnOrderClick(null))
                                    }
                                },
                                onChangeStatus = { newStatus ->
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnChangeStatus(
                                            newStatus
                                        )
                                    )
                                }
                            )
                        }

                        OrderStatus.PENDING -> {
                            DispatchProcessSheetContent(
                                order = order,
                                employeeId = "EMP-4521",
                                timerValue = dispatchDashboardState.timerValue,
                                currentStep = dispatchDashboardState.currentStep,
                                onStartDispatch = {
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnStepChanged(
                                            DispatchStep.IN_PROGRESS
                                        )
                                    )
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnTimerRunning(
                                            true
                                        )
                                    )
                                },
                                onReportIncident = { desc, photo ->
                                    println("Incidencia reportada: '$desc', Con foto: $photo")
                                },
                                onFinishDispatch = {
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnStepChanged(
                                            DispatchStep.FINALIZING
                                        )
                                    )
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnTimerRunning(
                                            false
                                        )
                                    )
                                    scope.launch {
                                        sheetState.hide()
                                    }
                                },
                                onConfirmSignature = {
                                    println("Firma confirmada. Proceso completado.")
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnStepChanged(
                                            DispatchStep.NOT_STARTED
                                        )
                                    )
                                    scope.launch {
                                        sheetState.hide()
                                        dispatchDashboardAction(DispatchDashboardAction.OnOrderClick(null))
                                    }
                                },
                                onDismiss = {
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnStepChanged(
                                            DispatchStep.NOT_STARTED
                                        )
                                    )
                                    dispatchDashboardAction(
                                        DispatchDashboardAction.OnTimerRunning(
                                            false
                                        )
                                    )
                                    dispatchDashboardAction(DispatchDashboardAction.OnTimerChanged("00:00:00"))
                                    scope.launch {
                                        sheetState.hide()
                                        dispatchDashboardAction(DispatchDashboardAction.OnOrderClick(null))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}