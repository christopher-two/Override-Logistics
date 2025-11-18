package org.override.logistics.core.common

sealed class RoutesApp(
    val route: String
) {
    object Login : RoutesApp("Login")

    object Warehouse : RoutesApp("Warehouse")

    object Haulier : RoutesApp("Haulier") {
        const val DASHBOARD = "Dashboard"
        const val CHECKLIST = "Checklist"
        const val DELIVERY = "Delivery"
    }

    object Map : RoutesApp("Map")
}