package org.override.docs.presentation.features.haulier

data class HaulierState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)