package com.brightside.configs

import com.brightside.configs.connection.DatabaseFactory

fun configureDatabases() {
    return DatabaseFactory.init()
}