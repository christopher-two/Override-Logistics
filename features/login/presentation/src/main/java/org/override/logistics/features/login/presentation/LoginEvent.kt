package org.override.logistics.features.login.presentation

sealed interface LoginEvent {
    object Success : LoginEvent
    data class Message(val text: String) : LoginEvent
}