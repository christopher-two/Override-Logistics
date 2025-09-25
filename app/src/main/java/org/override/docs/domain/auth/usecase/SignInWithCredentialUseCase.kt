package org.override.docs.domain.auth.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class SignInWithCredentialUseCase(
    private val authManager: FirebaseAuthManager
) {
    suspend operator fun invoke(credential: AuthCredential): Result<AuthResult> =
        authManager.signInWithCredential(credential)
}

