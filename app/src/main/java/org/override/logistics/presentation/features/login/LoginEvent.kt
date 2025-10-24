package org.override.logistics.presentation.features.login

sealed interface LoginEvent {
    object Success : LoginEvent
    data class Message(val text: String) : LoginEvent
}