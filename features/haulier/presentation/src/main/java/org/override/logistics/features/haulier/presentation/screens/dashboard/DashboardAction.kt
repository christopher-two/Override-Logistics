package org.override.logistics.features.haulier.presentation.screens.dashboard

import org.override.logistics.features.haulier.presentation.components.Shipment

sealed interface DashboardAction {
    data class OnViewDetails(val shipment: Shipment) : DashboardAction

    data class OnOpenInMaps(val shipment: Shipment) : DashboardAction

    object OnShowTripHistory : DashboardAction
}