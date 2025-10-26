package org.override.logistics.data.auth.api.plataform

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

/**
 * Contrato para interactuar con Firebase Authentication.
 *
 * Este interfaz vive en el módulo de la API de autenticación y expone
 * las operaciones básicas (registro, inicio de sesión, restablecimiento de contraseña,
 * gestión de sesión) necesarias por la capa de dominio o presentación.
 * Todas las operaciones que implican IO/Red son suspend y devuelven un [Result]
 * para encapsular éxito o fallo de forma explícita.
 */
interface FirebaseAuthManager {
    /**
     * Registra un usuario con correo y contraseña en Firebase.
     * @return Result.success con [AuthResult] si el registro fue exitoso, o Result.failure con la excepción.
     */
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<AuthResult>

    /**
     * Inicia sesión con correo y contraseña.
     * @return Result.success con [AuthResult] si el inicio fue exitoso, o Result.failure con la excepción.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<AuthResult>

    /**
     * Inicia sesión con una credencial externa (por ejemplo, Google, Facebook).
     * @param credential Credencial obtenida del proveedor externo.
     * @return Result.success con [AuthResult] si la autenticación fue exitosa, o Result.failure con la excepción.
     */
    suspend fun signInWithCredential(credential: AuthCredential): Result<AuthResult>

    /**
     * Envía un correo de restablecimiento de contraseña.
     * @return Result.success(Unit) si la petición fue enviada correctamente, o Result.failure con la excepción.
     */
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>

    /**
     * Devuelve el usuario actualmente autenticado o null si no hay sesión.
     */
    fun getCurrentUser(): FirebaseUser?

    /**
     * Cierra la sesión local del usuario.
     * @return Result.success(Unit) si la operación fue correcta, o Result.failure con la excepción.
     */
    suspend fun signOut(): Result<Unit>

    /**
     * Elimina la cuenta del usuario actual.
     * Nota: puede requerir reautenticación según el tiempo de sesión.
     */
    suspend fun deleteCurrentUser(): Result<Unit>

    /**
     * Reautentica al usuario actual con la credencial proporcionada (útil antes de operaciones sensibles).
     * @return Result.success(Unit) si la reautenticación fue exitosa, o Result.failure con la excepción.
     */
    suspend fun reauthenticate(credential: AuthCredential): Result<Unit>
}

