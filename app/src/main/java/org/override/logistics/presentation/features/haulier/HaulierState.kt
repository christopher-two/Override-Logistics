package org.override.logistics.presentation.features.haulier

data class HaulierState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)