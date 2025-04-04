package com.brightside.backend.config

import org.slf4j.LoggerFactory
import java.io.File
import java.util.Properties

object EnvConfig {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val properties = loadEnvFile()

    private fun loadEnvFile(): Properties {
        val props = Properties()
        try {
            val envFile = File(".env")
            if (envFile.exists()) {
                envFile.bufferedReader().use { reader ->
                    reader.lineSequence()
                        .filter { it.isNotBlank() && !it.startsWith("#") }
                        .forEach {
                            val (key, value) = it.split("=", limit = 2)
                            props[key.trim()] = value.trim()
                        }
                }
                logger.info("Loaded configuration from .env file successfully")
            } else {
                logger.warn(".env file not found, using system environment variables")
            }
        } catch (e: Exception) {
            logger.error("Error loading .env file: ${e.message}")
        }
        return props
    }

    fun getEnv(key: String, defaultValue: String? = null): String? {
        return properties.getProperty(key) ?: System.getenv(key) ?: defaultValue
    }
}
