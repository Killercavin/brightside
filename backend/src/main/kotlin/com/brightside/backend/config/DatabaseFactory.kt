package com.brightside.backend.config

import com.brightside.backend.models.ProductTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseFactory {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun init() {
        logger.info("Initializing database connection")

        Database.connect(hikari())

        transaction {
            // Example: SchemaUtils.create(Users, Products)
            SchemaUtils.create(ProductTable)
        }

        logger.info("Database connection initialized successfully")
    }

    private fun hikari(): HikariDataSource {
        val dbUrl = EnvConfig.getEnv("DATABASE_URL", "jdbc:postgresql://localhost:5432/brightside")
        val dbUser = EnvConfig.getEnv("DATABASE_USER", "")
        val dbPassword = EnvConfig.getEnv("DATABASE_PASSWORD", "")
        val maxPoolSize = EnvConfig.getEnv("DATABASE_MAX_POOL_SIZE", "10")?.toIntOrNull() ?: 10

        logger.info("Connecting to database at $dbUrl with user $dbUser")

        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            username = dbUser
            password = dbPassword
            maximumPoolSize = maxPoolSize
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            validate()
        }

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

}