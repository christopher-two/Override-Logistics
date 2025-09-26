package org.override.docs.presentation.features.warehouse

data class WarehouseState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)