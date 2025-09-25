package org.override.docs.data.auth.impl.firebase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import org.override.docs.data.auth.api.plataform.FirebaseAuthManager

/**
 * Implementación de [FirebaseAuthManager] ubicada en `data/impl/firebase`.
 * Equivalente funcional a la implementación existente en `data/auth/impl/firebase` pero
 * con el paquete y la ruta solicitados.
 */
class FirebaseAuthManagerImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthManager {

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<AuthResult> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Result<AuthResult> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithCredential(credential: AuthCredential): Result<AuthResult> {
        return try {
            val result = firebaseAuth.signInWithCredential(credential).await()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    override suspend fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCurrentUser(): Result<Unit> {
        val user = firebaseAuth.currentUser
            ?: return Result.failure(IllegalStateException("No authenticated user to delete"))
        return try {
            user.delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun reauthenticate(credential: AuthCredential): Result<Unit> {
        val user = firebaseAuth.currentUser
            ?: return Result.failure(IllegalStateException("No authenticated user to reauthenticate"))
        return try {
            user.reauthenticate(credential).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
