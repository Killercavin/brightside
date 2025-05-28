package com.brightside.backend.models.users.mappers

import com.brightside.backend.models.users.dto.AdminDTO
import com.brightside.backend.models.users.entities.AdminEntity

fun AdminEntity.toDTO(): AdminDTO = AdminDTO(
    id = this.id,
    name = this.name,
    email = this.email,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)