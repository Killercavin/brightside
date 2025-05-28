package com.brightside.backend.models.products.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp
import org.jetbrains.exposed.sql.javatime.timestamp

object StockTable : IntIdTable("stock") {
    val variantId = reference("variant_id", ProductVariantTable, onDelete = ReferenceOption.CASCADE)
    val quantity = integer("quantity").default(0)
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())
}
