package org.override.docs.presentation.features.warehouse.domain

/**
 * Representa un pedido de despacho.
 */
data class Order(
    val id: String,
    val clientName: String,
    val carrierName: String,
    val date: String,
    val status: OrderStatus
)