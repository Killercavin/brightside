package com.brightside.utils.enum

import kotlinx.serialization.Serializable

@Serializable
// enum class to hold the roles
enum class AdminRole {
    SUPER_ADMIN, // can manage admins
    ADMIN,       // can manage products, orders and staff
    STAFF;       // view only, limited privileges

    fun canViewAdmin(targetRole: AdminRole, targetId: Int, requesterId: Int): Boolean {
        return when (this) {
            SUPER_ADMIN -> true
            ADMIN -> targetId == requesterId || targetRole == STAFF
            STAFF -> targetId == requesterId
        }
    }

    fun canEditAdmin(targetRole: AdminRole, targetId: Int, requesterId: Int): Boolean {
        return when (this) {
            SUPER_ADMIN -> true // Full control
            ADMIN -> (targetId == requesterId || targetRole == STAFF) // Self + staff
            STAFF -> targetId == requesterId // Only self
        }
    }

    fun canDeleteAdmin(targetId: Int, requesterId: Int): Boolean {
        return this == SUPER_ADMIN && requesterId != targetId
    }

    // More permissions
}
