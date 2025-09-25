package org.override.docs.domain.auth.usecase

import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class SignOutUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(): Result<Unit> = authManager.signOut()
}

