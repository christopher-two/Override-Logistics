package org.override.logistics.features.haulier.presentation.screens.dashboard

import org.override.logistics.features.haulier.presentation.components.Shipment

data class DashboardState(
    val shipments: List<Shipment> = listOf()
)