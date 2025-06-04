package com.brightside.backend.services.users.admin

import com.brightside.backend.configs.connection.DatabaseFactory.dbQuery
import com.brightside.backend.models.users.admin.dto.responses.AdminProfileResponse
import com.brightside.backend.models.users.admin.entities.AdminEntity
import com.brightside.backend.models.users.admin.tables.AdminTable
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object AdminService {

    fun getAdminByEmail(email: String): AdminEntity? {
        return transaction {
            AdminTable.select { AdminTable.email eq email }
                .singleOrNull()
                ?.let {
                    AdminEntity(
                        id = it[AdminTable.id].value,
                        name = it[AdminTable.name],
                        email = it[AdminTable.email],
                        passwordHash = it[AdminTable.passwordHash],
                        createdAt = it[AdminTable.createdAt].toString(),
                        updatedAt = it[AdminTable.updatedAt].toString()
                    )
                }
        }
    }



    // admin profile
    suspend fun getAdminProfile(id: Int): AdminProfileResponse {
        val row = dbQuery {
            AdminTable.select { AdminTable.id eq id }.singleOrNull()
        } ?: throw NotFoundException("Admin with id $id not found")

        return AdminProfileResponse(
            id = row[AdminTable.id].value,
            email = row[AdminTable.email],
            name = row[AdminTable.name],
            createdAt = row[AdminTable.createdAt].toString()
        )
    }



    // Later more operations like getProfile, updateAdmin, can be added
}