package com.ecliptacare.backend.configs

import io.ktor.server.application.*

fun Application.configureDatabases() {
    return DatabaseFactory.init()
}