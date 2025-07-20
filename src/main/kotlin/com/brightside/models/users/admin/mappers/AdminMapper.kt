package com.brightside.models.users.admin.mappers

import com.brightside.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.models.users.admin.entities.AdminEntity
import com.brightside.models.users.admin.tables.AdminTable
import org.jetbrains.exposed.sql.ResultRow

fun AdminEntity.toProfileResponse() = AdminProfileResponse(
    id = this.id,
    fullName = this.fullName,
    email = this.email,
    role = this.role,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun ResultRow.toAdminEntity() = AdminEntity(
    id = this[AdminTable.id].value,
    firstName = this[AdminTable.firstName],
    lastName = this[AdminTable.lastName],
    email = this[AdminTable.email],
    role = this[AdminTable.role],
    passwordHash = this[AdminTable.passwordHash],
    createdAt = this[AdminTable.createdAt].toString(),
    updatedAt = this[AdminTable.updatedAt].toString()
)

