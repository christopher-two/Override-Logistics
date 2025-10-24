package org.override.logistics.domain.auth.usecase

import com.google.firebase.auth.AuthCredential
import org.override.logistics.data.auth.api.plataform.FirebaseAuthManager

class ReauthenticateUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(credential: AuthCredential): Result<Unit> =
        authManager.reauthenticate(credential)
}
