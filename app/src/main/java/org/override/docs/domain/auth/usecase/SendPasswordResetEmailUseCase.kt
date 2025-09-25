package org.override.docs.domain.auth.usecase

import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class SendPasswordResetEmailUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        authManager.sendPasswordResetEmail(email)
}

