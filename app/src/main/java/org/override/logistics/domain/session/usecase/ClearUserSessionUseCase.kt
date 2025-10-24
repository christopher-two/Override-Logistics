package org.override.logistics.domain.session.usecase

import org.override.logistics.data.session.api.SessionRepository

class ClearUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Result<Unit> = sessionRepository.clearUserSession()
}

