package org.override.logistics.domain.session.usecase

import kotlinx.coroutines.flow.Flow
import org.override.logistics.data.session.api.SessionRepository

class IsUserLoggedInUseCase(
    private val sessionRepository: SessionRepository
) {
    operator fun invoke(): Flow<Boolean> = sessionRepository.isUserLoggedIn()
}

