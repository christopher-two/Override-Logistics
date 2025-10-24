package org.override.logistics.domain.auth.usecase

import com.google.firebase.auth.FirebaseUser
import org.override.logistics.data.auth.api.plataform.FirebaseAuthManager

class GetCurrentUserUseCase(
    private val authManager: FirebaseAuthManager
) {
    operator fun invoke(): FirebaseUser? = authManager.getCurrentUser()
}

