package org.override.logistics.presentation.features.warehouse.domain

/**
 * Define los pasos del proceso de despacho activo.
 */
enum class DispatchStep {
    NOT_STARTED,
    IN_PROGRESS,
    FINALIZING
}