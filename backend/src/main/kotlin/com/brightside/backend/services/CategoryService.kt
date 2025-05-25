package com.brightside.backend.services

import com.brightside.backend.configs.connection.DatabaseFactory.dbQuery
import com.brightside.backend.models.Category
import com.brightside.backend.models.table.CategoryTable
import com.brightside.backend.models.Product
import com.brightside.backend.models.table.ProductTable
import com.brightside.backend.services.ProductService.mapRowToProduct
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.Instant

private val logger = KotlinLogging.logger {}

object CategoryService {
    // get categories
    suspend fun getCategories(): List<Category> {
        return dbQuery {
            CategoryTable.selectAll().map { row ->
                Category(
                    id = row[CategoryTable.id],
                    name = row[CategoryTable.name],
                    description = row[CategoryTable.description],
                    createdAt = row[CategoryTable.createdAt],
                    updatedAt = row[CategoryTable.updatedAt]
                )
            }
        }
    }

    // get category by id
    suspend fun getCategoryById(id: Int): Category? {
        return dbQuery {
            CategoryTable
                .select { CategoryTable.id eq id }
                .map { mapRowToCategory(it) }
                .singleOrNull()
        }
    }

    // Fetch products by a specific category
    suspend fun getProductsByCategory(categoryId: Int): List<Product> = dbQuery {
        (ProductTable innerJoin CategoryTable)
            .select { ProductTable.categoryId eq categoryId }
            .map { mapRowToProduct(it) }
    }

    // add category
    suspend fun addCategory(
        name: String,
        description: String
    ): Category? {
        return dbQuery {
            try {
                val id = CategoryTable.insertAndGetId {
                    it[CategoryTable.name] = name
                    it[CategoryTable.description] = description
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()
                }.value

                val category = CategoryTable.select(CategoryTable.id.eq(id)).single()
                Category(
                    id = category[CategoryTable.id],
                    name = category[CategoryTable.name],
                    description = category[CategoryTable.description],
                    createdAt = category[CategoryTable.createdAt],
                    updatedAt = category[CategoryTable.updatedAt]
                )

            } catch (e: Exception) {
                logger.error("Error adding category ${e.message}")
                null
            }
        }
    }

    // update category
    suspend fun updateCategory(
        id: Int,
        name: String,
        description: String
    ): Category? = dbQuery {
        val updateRows = CategoryTable.update({ CategoryTable.id eq id }) {
            it[CategoryTable.id] = id
            it[CategoryTable.name] = name
            it[CategoryTable.description] = description
            it[updatedAt] = Instant.now()
        }

        if (updateRows > 0) {
            CategoryTable
                .select { CategoryTable.id eq id }
                .mapNotNull { row ->
                    Category(
                        id = row[CategoryTable.id],
                        name = row[CategoryTable.name],
                        description = row[CategoryTable.description],
                        createdAt = row[CategoryTable.createdAt],
                        updatedAt = row[CategoryTable.updatedAt]
                    )
                }.singleOrNull()
        } else null
    }

    // patch category
    suspend fun patchCategory(
        id: Int,
        name: String? = null,
        description: String? = null
    ): Category? {
        val patchResult = dbQuery {
            CategoryTable.update({ CategoryTable.id eq id }) { stmt ->
                name?.let { stmt[CategoryTable.name] = it }
                description?.let { stmt[CategoryTable.description] = it }
                stmt[updatedAt] = Instant.now()
            }
        }

        return if (patchResult > 0) {
            getCategoryById(id)
        } else null
    }

    // delete category
    suspend fun deleteCategory(id: Int) = dbQuery {
        CategoryTable.deleteWhere { CategoryTable.id eq id }
    }

    // helper function
    private fun mapRowToCategory(row: ResultRow): Category {
        return Category(
            id = row[CategoryTable.id],
            name = row[CategoryTable.name],
            description = row[CategoryTable.description],
            createdAt = row[CategoryTable.createdAt],
            updatedAt = row[CategoryTable.updatedAt]
        )
    }
}