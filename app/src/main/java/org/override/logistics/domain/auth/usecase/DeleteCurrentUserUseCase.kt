package org.override.logistics.domain.auth.usecase

import org.override.logistics.data.auth.api.plataform.FirebaseAuthManager

class DeleteCurrentUserUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(): Result<Unit> = authManager.deleteCurrentUser()
}

