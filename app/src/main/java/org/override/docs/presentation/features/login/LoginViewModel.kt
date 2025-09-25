package org.override.docs.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.override.docs.domain.auth.usecase.SignInWithEmailAndPasswordUseCase
import org.override.docs.domain.session.usecase.SaveUserSessionUseCase

class LoginViewModel(
    private val signInUseCase: SignInWithEmailAndPasswordUseCase,
    private val saveUserSessionUseCase: SaveUserSessionUseCase
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    private val _events = MutableSharedFlow<LoginEvent>(extraBufferCapacity = 4)
    val events = _events.asSharedFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UpdateUsername -> {
                _state.value = _state.value.copy(username = action.username, error = null)
            }

            is LoginAction.UpdatePassword -> {
                _state.value = _state.value.copy(password = action.password, error = null)
            }

            LoginAction.Submit -> submit()
        }
    }

    private fun submit() {
        val current = _state.value
        if (current.username.isBlank() || current.password.isBlank()) {
            viewModelScope.launch {
                _state.update { it.copy(error = "Usuario y contraseña requeridos") }
                _events.emit(LoginEvent.Message("Usuario y contraseña requeridos"))
            }
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(loading = true, error = null) }
            runCatching {
                signInUseCase(current.username, current.password)
            }.onSuccess { result ->
                result.getOrNull()?.user?.uid?.let { uid ->
                    if (uid.isBlank()) {
                        _state.update { it.copy(error = "No se obtuvo UID de usuario") }
                        _events.emit(LoginEvent.Message("No se obtuvo UID de usuario"))
                    } else {
                        val saveResult = saveUserSessionUseCase(uid)
                        if (saveResult.isSuccess) {
                            _state.update { it.copy(error = null) }
                            _events.emit(LoginEvent.Success)
                        } else {
                            _state.update { it.copy(error = "Error guardando sesión") }
                            _events.emit(LoginEvent.Message("Error guardando sesión"))
                        }
                    }
                }
            }.onFailure { result ->
                _state.update { it.copy(error = "Credenciales inválidas") }
                val message = result.localizedMessage ?: "Credenciales inválidas"
                _events.emit(LoginEvent.Message(message))
            }
        }.invokeOnCompletion {
            _state.update { it.copy(loading = false) }
        }
    }
}