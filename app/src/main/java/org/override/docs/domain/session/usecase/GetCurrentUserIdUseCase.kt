package org.override.docs.domain.session.usecase

import kotlinx.coroutines.flow.Flow
import org.override.docs.data.session.api.SessionRepository

class GetCurrentUserIdUseCase(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<String?> = sessionRepository.getCurrentUserId()
}

