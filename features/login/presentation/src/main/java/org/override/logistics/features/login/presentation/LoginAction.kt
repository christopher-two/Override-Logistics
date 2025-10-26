package org.override.logistics.features.login.presentation

sealed interface LoginAction {
    data class UpdateUsername(val username: String) : LoginAction
    data class UpdatePassword(val password: String) : LoginAction
    object Submit : LoginAction
}