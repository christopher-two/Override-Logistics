package org.override.logistics.presentation.features.haulier

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.logistics.presentation.features.haulier.components.CarrierDashboardScreen
import org.override.logistics.presentation.features.haulier.components.ChecklistItem
import org.override.logistics.presentation.features.haulier.components.Shipment
import org.override.logistics.presentation.features.haulier.components.ShipmentStatus
import org.override.logistics.presentation.theme.DocsTheme

@Composable
fun HaulierRoot(
    viewModel: HaulierViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HaulierScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun HaulierScreen(
    state: HaulierState,
    onAction: (HaulierAction) -> Unit,
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
        onOpenInMaps = { /* Logica */ },
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
    DocsTheme {
        HaulierScreen(
            state = HaulierState(),
            onAction = {}
        )
    }
}