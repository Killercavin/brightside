package com.brightside.backend.configs

import io.ktor.server.application.*

fun Application.configureDatabases() {
    return DatabaseFactory.init()
}