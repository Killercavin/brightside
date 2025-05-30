package com.brightside.backend.models.users.admin.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object AdminTable: IntIdTable("admins") {
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = text("password_hash")
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())
}