package org.override.docs.domain.session.usecase

import org.override.docs.data.session.api.SessionRepository

class ClearUserSessionUseCase(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke(): Result<Unit> = sessionRepository.clearUserSession()
}

