package com.brightside.backend.models

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp


object ProductTable : Table("products") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 250)
    val description = text("description")
    val categoryId = integer("category_id")
        .references(CategoryTable.id, onDelete = ReferenceOption.CASCADE)
    val price = decimal("price", 10, 2)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())

    override val primaryKey = PrimaryKey(id)
}