package com.brightside.backend.services.users.admin

import com.brightside.backend.configs.connection.DatabaseFactory.dbQuery
import com.brightside.backend.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.backend.models.users.admin.entities.AdminEntity
import com.brightside.backend.models.users.admin.tables.AdminTable
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.select

class AdminService {

    suspend fun getAdminProfile(id: Int): AdminProfileResponse {
        val row = dbQuery {
            AdminTable.select { AdminTable.id eq id }.singleOrNull()
        } ?: throw NotFoundException("Admin with id $id not found")

        return AdminProfileResponse(
            id = row[AdminTable.id].value,
            email = row[AdminTable.email],
            fullName = "${row[AdminTable.firstName]} ${row[AdminTable.lastName]}".trim(),
            role = row[AdminTable.role],
            createdAt = row[AdminTable.createdAt].toString(),
            updatedAt = row[AdminTable.updatedAt].toString()
        )
    }

    // fetching admin by their email
    suspend fun getAdminByEmail(email: String): AdminEntity? = dbQuery {
        AdminTable
            .select { AdminTable.email eq email }
            .singleOrNull()
            ?.let {
                AdminEntity(
                    id = it[AdminTable.id].value,
                    firstName = it[AdminTable.firstName],
                    lastName = it[AdminTable.lastName],
                    email = it[AdminTable.email],
                    passwordHash = it[AdminTable.passwordHash],
                    role = it[AdminTable.role],
                    createdAt = it[AdminTable.createdAt].toString(),
                    updatedAt = it[AdminTable.updatedAt].toString()
                )
            }
    }

    // fetch the admin by their ID
    suspend fun getAdminById(id: Int): AdminEntity? = dbQuery {
        AdminTable
            .select { AdminTable.id eq id }
            .singleOrNull()
            ?.let { row ->
                AdminEntity(
                    id = row[AdminTable.id].value,
                    email = row[AdminTable.email],
                    firstName = row[AdminTable.firstName],
                    lastName = row[AdminTable.lastName],
                    passwordHash = row[AdminTable.passwordHash],
                    role = row[AdminTable.role],
                    createdAt = row[AdminTable.createdAt].toString(),
                    updatedAt = row[AdminTable.updatedAt].toString()
                )
            }
    }

}