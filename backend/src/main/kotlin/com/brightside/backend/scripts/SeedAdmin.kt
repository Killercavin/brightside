package com.brightside.backend.scripts

import com.brightside.backend.configs.connection.DatabaseFactory
import com.brightside.backend.configs.connection.EnvConfig
import com.brightside.backend.models.users.admin.tables.AdminTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import at.favre.lib.crypto.bcrypt.BCrypt

private val logger = KotlinLogging.logger {}

fun main() {
    runCatching {
        // Initialize DB
        DatabaseFactory.init()

        // Read environment variables
        val name: String = EnvConfig.getEnv("ADMIN_NAME", "") ?: error("ADMIN_NAME is missing")
        val email: String = EnvConfig.getEnv("ADMIN_EMAIL", "") ?: error("ADMIN_EMAIL is missing")
        val rawPassword: String = EnvConfig.getEnv("ADMIN_PASSWORD", "") ?: error("ADMIN_PASSWORD is missing")

        require(name.isNotBlank()) { "Admin name cannot be blank" }
        require(email.isNotBlank()) { "Admin email cannot be blank" }
        require(rawPassword.isNotBlank()) { "Admin password cannot be blank" }

        // Transaction: seed admin
        transaction {
            val adminExists = AdminTable.select { AdminTable.email eq email }.singleOrNull()

            if (adminExists != null) {
                logger.warn { "Admin with email '$email' already exists." }
                return@transaction
            }

            // Use favre bcrypt to hash password
            val hashedPassword = BCrypt.withDefaults()
                .hashToString(12, rawPassword.toCharArray())

            AdminTable.insert {
                it[AdminTable.name] = name
                it[AdminTable.email] = email
                it[AdminTable.passwordHash] = hashedPassword
            }

            logger.info { "Super admin '$email' created successfully." }
        }
    }.onFailure {
        logger.error(it) { "Admin seeding failed: ${it.message}" }
    }
}
