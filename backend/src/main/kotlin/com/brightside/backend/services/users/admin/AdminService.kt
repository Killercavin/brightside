package com.brightside.backend.services.users.admin

import com.brightside.backend.models.users.admin.tables.AdminTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class AdminRecord(
    val email: String,
    val name: String,
    val passwordHash: String
)

object AdminService {

    fun getAdminByEmail(email: String): AdminRecord? {
        return transaction {
            AdminTable.select { AdminTable.email eq email }
                .singleOrNull()
                ?.let {
                    AdminRecord(
                        email = it[AdminTable.email],
                        name = it[AdminTable.name],
                        passwordHash = it[AdminTable.passwordHash]
                    )
                }
        }
    }

    // Later more operations like getProfile, updateAdmin, can be added
}