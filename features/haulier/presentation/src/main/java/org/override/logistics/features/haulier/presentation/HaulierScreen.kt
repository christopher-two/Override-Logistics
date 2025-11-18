package org.override.logistics.features.haulier.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.logistics.features.haulier.presentation.components.CarrierDashboardScreen
import org.override.logistics.features.haulier.presentation.components.ChecklistItem
import org.override.logistics.features.haulier.presentation.components.Shipment
import org.override.logistics.features.haulier.presentation.components.ShipmentStatus

@Composable
fun HaulierRoot(
    viewModel: HaulierViewModel,
    onMapNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HaulierScreen(
        state = state,
        onAction = viewModel::onAction,
        onMapNavigate = onMapNavigate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HaulierScreen(
    state: HaulierState,
    onAction: (HaulierAction) -> Unit,
    onMapNavigate: () -> Unit
) {
    val sampleShipments = remember {
        mutableStateListOf(
            Shipment("Emb-112", "Av. Reforma 222, CDMX", ShipmentStatus.ASSIGNED),
            Shipment("Emb-113", "Calle Falsa 123, Springfield", ShipmentStatus.LOADING)
        )
    }
    val checklistItems = remember {
        mutableStateListOf(
            ChecklistItem(1, "Revisar neumáticos", false),
            ChecklistItem(2, "Verificar luces", false),
            ChecklistItem(3, "Niveles de aceite y agua", false),
            ChecklistItem(4, "Documentación completa", false)
        )
    }

    CarrierDashboardScreen(
        assignedShipments = sampleShipments,
        checklistItems = checklistItems,
        onShowShipmentDetails = { /* Logica */ },
        onAcceptShipmentWithSignature = { /* Logica */ },
        onOpenInMaps = { onMapNavigate() },
        onChecklistItemToggle = { id ->
            val index = checklistItems.indexOfFirst { it.id == id }
            if (index != -1) {
                checklistItems[index] =
                    checklistItems[index].copy(isChecked = !checklistItems[index].isChecked)
            }
        },
        onStartRoute = { /* Logica */ },
        onConfirmDelivery = { _, _, _ -> /* Logica */ },
        onShowTripHistory = { /* Logica */ }
    )
}

@Preview
@Composable
private fun Preview() {
    HaulierScreen(
        state = HaulierState(),
        onAction = {},
        onMapNavigate = {}
    )
}