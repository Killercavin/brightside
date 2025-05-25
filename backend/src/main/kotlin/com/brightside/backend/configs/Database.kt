package com.brightside.backend.configs

import com.brightside.backend.configs.connection.DatabaseFactory

fun configureDatabases() {
    return DatabaseFactory.init()
}