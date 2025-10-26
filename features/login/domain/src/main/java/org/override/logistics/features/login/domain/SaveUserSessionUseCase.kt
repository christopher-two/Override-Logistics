package org.override.logistics.features.login.domain

import org.override.logistics.data.session.api.SessionRepository

class SaveUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(uid: String): Result<Unit> = sessionRepository.saveUserSession(uid)
}