package org.override.docs.domain.auth.usecase

import com.google.firebase.auth.AuthCredential
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class ReauthenticateUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(credential: AuthCredential): Result<Unit> =
        authManager.reauthenticate(credential)
}
