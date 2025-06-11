package com.brightside.backend.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.brightside.backend.configs.connection.EnvConfig
import java.util.Date

object JwtProvider {
    private val SECRET: String = EnvConfig.getEnv("JWT_SECRET", "") ?: error("JWT_SECRET is missing")
    private val EXPIRY_MINUTES: Long = EnvConfig.getEnv("JWT_EXPIRY_MINUTES", "45")?.toLongOrNull() ?: 60
    private val ISSUER: String = EnvConfig.getEnv("JWT_ISSUER", "") ?: ""

    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(email: String): String {
        return JWT.create()
            .withIssuer(ISSUER)
            .withSubject("admin-auth")
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRY_MINUTES * 60 * 1000))
            .sign(algorithm)
    }

    fun getAlgorithm(): Algorithm = algorithm
    fun getIssuer(): String = ISSUER
}
