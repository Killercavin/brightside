package com.brightside.configs.connection

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseFactory {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun init() {
        logger.info("Running Flyway migrations")

        val dbUrl = EnvConfig.getEnv("DATABASE_URL", "")
        val dbUser = EnvConfig.getEnv("DATABASE_USER", "")
        val dbPassword = EnvConfig.getEnv("DATABASE_PASSWORD", "")

        // Run Flyway migrations
        val flyway = Flyway.configure()
            .dataSource(dbUrl, dbUser, dbPassword)
            .locations("classpath:db/migration")
            .load()

        // flyway.validate() // checks whether the current schema matches what Flyway expects (based on migration history)
        flyway.migrate() // then finally migrate if the validation check is passed

        logger.info("Flyway migrations completed")

        // Connect to the database
        logger.info("Initializing database connection")
        Database.Companion.connect(hikari())

        /*
        transaction {
            SchemaUtils.create(ProductTable)
        }
        */

        logger.info("Database connection initialized successfully")
    }

    private fun hikari(): HikariDataSource {
        val dbUrl = EnvConfig.getEnv("DATABASE_URL", "")
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