package org.override.docs.domain.auth.usecase

import com.google.firebase.auth.FirebaseUser
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

class GetCurrentUserUseCase(
    private val authManager: FirebaseAuthManager
) {
    operator fun invoke(): FirebaseUser? = authManager.getCurrentUser()
}

