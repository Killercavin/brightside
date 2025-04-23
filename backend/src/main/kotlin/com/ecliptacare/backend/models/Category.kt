package com.ecliptacare.backend.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.EntityID
import java.time.Instant

@Serializable
data class Category(
    @Contextual val id: EntityID<Int>,
    val name: String,
    val description: String,
    @Contextual val createdAt: Instant,
    @Contextual val updatedAt: Instant,
)
