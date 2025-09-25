package org.override.docs.domain.auth.usecase

import org.override.docs.data.session.api.SessionRepository

class SaveUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(uid: String): Result<Unit> = sessionRepository.saveUserSession(uid)
}
