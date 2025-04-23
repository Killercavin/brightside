package com.brightside.backend.services

import com.brightside.backend.configs.DatabaseFactory.dbQuery
import com.brightside.backend.models.Product
import com.brightside.backend.models.ProductTable
import com.brightside.backend.models.CategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.math.BigDecimal
import java.time.Instant
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

object ProductService {
    // addProduct function that handles insertion and fetching within the same method
    suspend fun addProduct(
        name: String,
        description: String,
        categoryId: Int,
        price: BigDecimal
    ): Product? {
        return dbQuery {
            try {
                // First, verify the category exists
                val categoryExists = CategoryTable.select { CategoryTable.id eq categoryId }.singleOrNull() != null

                if (!categoryExists) {
                    return@dbQuery null // Category doesn't exist
                }

                val id = ProductTable
                    .insertAndGetId {
                        it[ProductTable.name] = name
                        it[ProductTable.description] = description
                        it[ProductTable.categoryId] = categoryId
                        it[ProductTable.price] = price
                        it[createdAt] = Instant.now()
                        it[updatedAt] = Instant.now()
                    }.value

                // Get the inserted product data
                val product = ProductTable.select { ProductTable.id eq id }.single()

                // Creating the Product object manually without trying to access category data
                Product(
                    id = product[ProductTable.id],
                    name = product[ProductTable.name],
                    description = product[ProductTable.description],
                    price = product[ProductTable.price],
                    categoryId = product[ProductTable.categoryId],
                    category = CategoryTable.name.toString(),
                    createdAt = product[ProductTable.createdAt],
                    updatedAt = product[ProductTable.updatedAt]
                )
            } catch (e: Exception) {
                // Log the error for debugging
                logger.error("Error adding product: ${e.message}")
                null
            }
        }
    }

    // Fetch a single product by its ID
    suspend fun getProductById(id: Int): Product? = dbQuery {
        (ProductTable innerJoin CategoryTable)
            .select { ProductTable.id eq id }
            .map { mapRowToProduct(it) }
            .singleOrNull()
    }

    // Fetch all products
    suspend fun getProducts(): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryTable)
            .selectAll()
            .map { mapRowToProduct(it) }
    }

    // update products
    suspend fun updateProduct(
        id: Int,
        name: String,
        description: String,
        categoryId: Int,
        price: BigDecimal
    ): Product? = dbQuery {
        val updatedRows = ProductTable.update({ ProductTable.id eq id }) {
            it[ProductTable.name] = name
            it[ProductTable.description] = description
            it[ProductTable.categoryId] = categoryId
            it[ProductTable.price] = price
            it[updatedAt] = Instant.now()
        }

        if (updatedRows > 0) {
            // returning the updated product
            ProductTable
                .select { ProductTable.id eq id }
                .mapNotNull { row ->
                    Product(
                        id = row[ProductTable.id],
                        name = row[ProductTable.name],
                        description = row[ProductTable.description],
                        categoryId = row[ProductTable.categoryId],
                        category = "",
                        price = row[ProductTable.price],
                        createdAt = row[ProductTable.createdAt],
                        updatedAt = row[ProductTable.updatedAt]
                    )
                }.singleOrNull()
        } else null
    }

    // patch product
    suspend fun patchProduct(
        id: Int,
        name: String? = null,
        description: String? = null,
        categoryId: Int? = null,
        price: BigDecimal? = null
    ): Product? {
        val productExists = dbQuery {
            ProductTable.select { ProductTable.id eq id }.singleOrNull() != null
        }

        if (!productExists) {
            return null
        }

        val patchResult = dbQuery {
            ProductTable.update({ ProductTable.id eq id }) { stmt ->
                name?.let { stmt[ProductTable.name] = it }
                description?.let { stmt[ProductTable.description] = it }
                price?.let { stmt[ProductTable.price] = it }
                categoryId?.let { stmt[ProductTable.categoryId] = it }
                stmt[updatedAt] = Instant.now()
            }
        }

        return if (patchResult > 0) {
            getProductById(id)
        } else {
            null
        }
    }

    // delete product
    suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        ProductTable.deleteWhere { ProductTable.id eq id } > 0
    }

    // Helper function to map a result row to a Product object
    fun mapRowToProduct(row: ResultRow): Product {
        return Product(
            id = row[ProductTable.id],
            name = row[ProductTable.name],
            description = row[ProductTable.description],
            categoryId = row[ProductTable.categoryId],
            category = row[CategoryTable.name],
            price = row[ProductTable.price],
            createdAt = row[ProductTable.createdAt],
            updatedAt = row[ProductTable.updatedAt]
        )
    }
}
