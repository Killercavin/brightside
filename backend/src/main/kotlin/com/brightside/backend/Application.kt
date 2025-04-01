package com.brightside.backend

import com.brightside.backend.config.DatabaseFactory
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureRouting()
    DatabaseFactory.init() // Initializing the database everytime the app is started
}
