package org.override.logistics.features.wharehouse.domain

import androidx.compose.ui.graphics.Color

/**
 * Define los posibles estados de un pedido.
 * @param displayName Nombre legible para mostrar en la UI.
 * @param color Color asociado para la UI.
 */
enum class OrderStatus(val displayName: String, val color: Color) {
    PENDING("Pendiente", Color(0xFFFBC02D)), // Amarillo
    IN_PROGRESS("En Proceso", Color(0xFF1976D2)), // Azul
    COMPLETED("Completado", Color(0xFF388E3C)) // Verde
}