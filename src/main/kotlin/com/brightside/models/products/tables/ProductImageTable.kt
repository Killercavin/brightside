package com.brightside.models.products.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.javatime.CurrentTimestamp

// defines how the image is stored in DB using Exposed DSL
object ProductImageTable : IntIdTable("product_images") {
    val productId = reference("product_id", ProductTable, onDelete = ReferenceOption.CASCADE).nullable()
    val variantId = reference("variant_id", ProductVariantTable, onDelete = ReferenceOption.CASCADE).nullable()
    val url = varchar("url", 500)
    val altText = varchar("alt_text", 255).nullable()
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").defaultExpression(CurrentTimestamp())
}
