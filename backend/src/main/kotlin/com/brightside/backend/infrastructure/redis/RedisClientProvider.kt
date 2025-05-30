package com.brightside.backend.infrastructure.redis

import io.lettuce.core.RedisClient
import io.lettuce.core.api.async.RedisAsyncCommands

object RedisClientProvider {
    private val client = RedisClient.create("redis://localhost:6379")
    private val connection = client.connect()
    val asyncCommands: RedisAsyncCommands<String, String> = connection.async()
}