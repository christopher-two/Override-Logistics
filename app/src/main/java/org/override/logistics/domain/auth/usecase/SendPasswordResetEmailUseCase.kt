package org.override.logistics.domain.auth.usecase

import org.override.logistics.data.auth.api.plataform.FirebaseAuthManager

class SendPasswordResetEmailUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        authManager.sendPasswordResetEmail(email)
}

