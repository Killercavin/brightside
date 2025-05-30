package com.brightside.backend.services.users.admin.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import com.brightside.backend.models.users.admin.dto.requests.AdminLoginRequest
import com.brightside.backend.models.users.admin.dto.responses.AdminLoginResponse
import com.brightside.backend.security.jwt.JwtProvider
import com.brightside.backend.services.users.admin.AdminService
import com.brightside.backend.validators.users.admin.AdminLoginValidator
import com.brightside.backend.validators.users.admin.ValidationResult
import com.brightside.backend.infrastructure.redis.RedisClientProvider // Adjust if different

import kotlinx.coroutines.future.await

object AdminAuthService {

    suspend fun login(request: AdminLoginRequest): Result<AdminLoginResponse> {
        // Validate input
        when (val validation = AdminLoginValidator.validate(request)) {
            is ValidationResult.Error -> return Result.failure(IllegalArgumentException(validation.message))
            is ValidationResult.Success -> Unit
        }

        // Fetch admin
        val admin = AdminService.getAdminByEmail(request.email)
            ?: return Result.failure(NoSuchElementException("Admin with email ${request.email} not found"))

        // Verify password
        val isPasswordCorrect = BCrypt.verifyer().verify(
            request.password.toCharArray(),
            admin.passwordHash
        ).verified

        if (!isPasswordCorrect) {
            return Result.failure(IllegalArgumentException("Incorrect password"))
        }

        // Generate tokens
        val accessToken = JwtProvider.generateToken(admin.email)
        val refreshToken = JwtProvider.generateToken(admin.email)

        // Store refresh token in Redis
        RedisClientProvider.asyncCommands.setex("refresh:${admin.email}", 7 * 24 * 60 * 60L, refreshToken).await()

        // Return both tokens
        return Result.success(
            AdminLoginResponse(
                email = admin.email,
                token = accessToken,
                refreshToken = refreshToken // Ensure your DTO supports this
            )
        )
    }

    suspend fun refreshToken(refreshToken: String): Result<AdminLoginResponse> {
        val email = JwtProvider.verifyToken(refreshToken)
            ?: return Result.failure(SecurityException("Invalid or expired refresh token"))

        // Check stored refresh token in Redis
        val stored = RedisClientProvider.asyncCommands.get("refresh:$email").await()
        if (stored != refreshToken) {
            return Result.failure(SecurityException("Refresh token mismatch"))
        }

        // Generate new access token
        val newAccessToken = JwtProvider.generateToken(email)

        return Result.success(
            AdminLoginResponse(
                email = email,
                token = newAccessToken,
                refreshToken = refreshToken
            )
        )
    }
}