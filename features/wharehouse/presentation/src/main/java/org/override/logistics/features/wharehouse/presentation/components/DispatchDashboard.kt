package org.override.logistics.features.wharehouse.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.override.logistics.features.wharehouse.domain.DispatchStep
import org.override.logistics.features.wharehouse.domain.Order
import org.override.logistics.features.wharehouse.domain.OrderStatus

/**
 * Componente principal que orquesta el panel de control de despachos.
 * Es un componente "stateless", recibe el estado y expone los eventos.
 *
 * @param orders Lista de pedidos a mostrar.
 * @param searchQuery Texto actual en la barra de búsqueda.
 * @param selectedStatus El estado actualmente seleccionado para filtrar, o null para "Todos".
 * @param onSearchQueryChange Lambda que se invoca cuando el texto de búsqueda cambia.
 * @param onStatusSelected Lambda que se invoca al seleccionar un filtro de estado.
 * @param onOrderClick Lambda que se invoca al hacer clic en un pedido.
 * @param showNewOrderNotification Booleano para controlar la visibilidad de la notificación.
 * @param onDismissNotification Lambda para cerrar la notificación de nuevo pedido.
 * @param modifier Modificador de Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatchDashboard(
    orders: List<Order>,
    searchQuery: String,
    selectedStatus: OrderStatus?,
    showNewOrderNotification: Boolean,
    selectedOrder: Order? = null,
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    onDismissSheetState: () -> Unit,
    onChangeStatus: (OrderStatus) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onStatusSelected: (OrderStatus?) -> Unit,
    onOrderClick: (Order) -> Unit,
    onDismissNotification: () -> Unit
) {
    var currentStep by remember { mutableStateOf(DispatchStep.NOT_STARTED) }
    var timerValue by remember { mutableStateOf("00:00:00") }
    var timerRunning by remember { mutableStateOf(false) }

    // Simulación del temporizador
    LaunchedEffect(key1 = timerRunning) {
        var seconds = 0
        while (timerRunning) {
            delay(1000)
            seconds++
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60
            timerValue = String.format("%02d:%02d:%02d", h, m, s)
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        snackbarHost = {
            NewOrderNotification(
                visible = showNewOrderNotification,
                onDismiss = onDismissNotification
            )
        },
        topBar = {
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
                    searchQuery = searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                // Fila de filtros por estado
                StatusFilterRow(
                    selectedStatus = selectedStatus,
                    onStatusSelected = onStatusSelected
                )
                // Lista de pedidos
                OrderList(
                    orders = orders,
                    onOrderClick = onOrderClick,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            selectedOrder?.let { order ->
                ModalBottomSheet(
                    sheetState = sheetState,
                    onDismissRequest = onDismissSheetState,
                    dragHandle = {}
                ) {
                    when (order.status) {
                        OrderStatus.IN_PROGRESS, OrderStatus.COMPLETED -> {
                            OrderDetailSheetContent(
                                order = order,
                                onDismiss = onDismissSheetState,
                                onChangeStatus = onChangeStatus
                            )
                        }

                        OrderStatus.PENDING -> {
                            DispatchProcessSheetContent(
                                order = order,
                                employeeId = "EMP-4521",
                                timerValue = timerValue,
                                currentStep = currentStep,
                                onStartDispatch = {
                                    currentStep = DispatchStep.IN_PROGRESS
                                    timerRunning = true
                                },
                                onReportIncident = { desc, photo ->
                                    println("Incidencia reportada: '$desc', Con foto: $photo")
                                },
                                onFinishDispatch = {
                                    currentStep = DispatchStep.FINALIZING
                                    timerRunning = false
                                    onDismissSheetState()
                                },
                                onConfirmSignature = {
                                    println("Firma confirmada. Proceso completado.")
                                    currentStep = DispatchStep.NOT_STARTED
                                    onDismissSheetState()
                                },
                                onDismiss = {
                                    currentStep = DispatchStep.NOT_STARTED
                                    timerRunning = false
                                    timerValue = "00:00:00"
                                    onDismissSheetState()
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}
