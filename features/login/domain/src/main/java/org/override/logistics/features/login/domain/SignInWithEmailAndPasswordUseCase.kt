package org.override.logistics.features.login.domain

import com.google.firebase.auth.AuthResult
import org.override.logistics.data.auth.api.plataform.FirebaseAuthManager

class SignInWithEmailAndPasswordUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult> =
        authManager.signInWithEmailAndPassword(email, password)
}