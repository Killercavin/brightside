package com.brightside.backend.models.users.admin.mappers

import com.brightside.backend.models.users.admin.dto.AdminDTO
import com.brightside.backend.models.users.admin.entities.AdminEntity

fun AdminEntity.toDTO(): AdminDTO = AdminDTO(
    id = this.id,
    name = this.name,
    email = this.email,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)