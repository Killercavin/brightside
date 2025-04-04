package com.brightside.backend.services

import com.brightside.backend.configs.DatabaseFactory.dbQuery
import com.brightside.backend.models.Product
import com.brightside.backend.models.ProductTable
import com.brightside.backend.models.CategoryTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import java.math.BigDecimal
import java.time.Instant

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
                    category = "", // leaving blank will handle later
                    createdAt = product[ProductTable.createdAt],
                    updatedAt = product[ProductTable.updatedAt]
                )
            } catch (e: Exception) {
                // Log the error for debugging
                println("Error in addProduct: ${e.message}")
                e.printStackTrace()
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

    // Fetch products by a specific category ID
    suspend fun getProductsByCategory(categoryId: Int): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryTable)
            .select { ProductTable.categoryId eq categoryId }
            .map { mapRowToProduct(it) }
    }

    // Helper function to map a result row to a Product object
    private fun mapRowToProduct(row: ResultRow): Product {
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
