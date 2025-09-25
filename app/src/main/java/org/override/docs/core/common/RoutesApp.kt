package org.override.docs.core.common

sealed class RoutesApp(
    val route: String
) {
    object Login : RoutesApp("Login")
    object Home : RoutesApp("Home")
    object Profile : RoutesApp("Profile")
    object Settings : RoutesApp("Settings")
}