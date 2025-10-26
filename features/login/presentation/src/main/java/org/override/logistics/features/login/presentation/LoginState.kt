package org.override.logistics.features.login.presentation

/** Estado MVI simple para la pantalla de Login */
data class LoginState(
    val username: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)