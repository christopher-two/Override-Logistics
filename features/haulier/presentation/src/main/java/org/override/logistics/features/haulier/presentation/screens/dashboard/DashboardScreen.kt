package org.override.logistics.features.haulier.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.History
import org.override.logistics.core.ui.theme.OverrideLogisticsTheme
import org.override.logistics.features.haulier.presentation.components.BrutalistButton
import org.override.logistics.features.haulier.presentation.components.ShipmentCard

@Composable
fun DashboardRoot(
    viewModel: DashboardViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DashboardScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.shipments) { shipment ->
            ShipmentCard(
                shipment = shipment,
                onViewDetails = { onAction(DashboardAction.OnViewDetails(shipment)) },
                onOpenInMaps = { onAction(DashboardAction.OnOpenInMaps(shipment)) }
            )
        }
        item {
            BrutalistButton(
                text = "VER HISTORIAL DE VIAJES",
                onClick = { onAction(DashboardAction.OnShowTripHistory) },
                icon = FontAwesomeIcons.Solid.History,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    OverrideLogisticsTheme {
        DashboardScreen(
            state = DashboardState(),
            onAction = {}
        )
    }
}