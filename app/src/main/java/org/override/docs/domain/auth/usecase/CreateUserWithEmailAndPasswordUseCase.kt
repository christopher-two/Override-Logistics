package org.override.docs.domain.auth.usecase

import com.google.firebase.auth.AuthResult
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class CreateUserWithEmailAndPasswordUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthResult> =
        authManager.createUserWithEmailAndPassword(email, password)
}

