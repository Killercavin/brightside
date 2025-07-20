package com.brightside

import com.brightside.configs.appSerializersModule
import com.brightside.configs.configureDatabases
import com.brightside.configs.configureSerialization
import com.brightside.plugins.configureStatusPages
import com.brightside.routes.configureRouting
import com.brightside.security.configureSecurity
import com.brightside.security.jwt.configureJwtAuth
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // JSON serialization with custom serializers
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule = appSerializersModule // hook in custom serializers here
        })
    }

    configureJwtAuth() // JWT authentication
    configureSecurity()
    configureRouting()
    configureSerialization()
    configureDatabases()
    configureStatusPages()
}
