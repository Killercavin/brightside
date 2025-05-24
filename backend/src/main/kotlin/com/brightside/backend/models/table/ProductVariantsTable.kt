package com.brightside.backend.models.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object ProductVariantTable : IntIdTable("product_variants") {
    val productId = reference("product_id", ProductTable, onDelete = ReferenceOption.CASCADE)
    val sku = varchar("sku", 64).uniqueIndex() // Unique identifier
    val color = varchar("color", 100).nullable()
    val size = varchar("size", 100).nullable()
    val stockQuantity = integer("stock_quantity").default(0)
    val price = decimal("price", 10, 2).nullable() // optional override

    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())
}
