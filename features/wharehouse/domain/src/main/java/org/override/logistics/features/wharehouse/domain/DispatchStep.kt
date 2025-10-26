package org.override.logistics.features.wharehouse.domain

/**
 * Define los pasos del proceso de despacho activo.
 */
enum class DispatchStep {
    NOT_STARTED,
    IN_PROGRESS,
    FINALIZING
}