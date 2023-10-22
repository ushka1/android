package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Product
import com.example.models.Products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProductDaoImpl : ProductDao {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        description = row[Products.description],
        price = row[Products.price],
        categoryCode = row[Products.categoryCode]
    )

    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(
        name: String,
        description: String,
        price: Float,
        categoryCode: String
    ): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.price] = price
            it[Products.categoryCode] = categoryCode
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(
        id: Int,
        name: String?,
        description: String?,
        price: Float?,
        categoryCode: String?
    ): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            if (name != null) {
                it[Products.name] = name
            }
            if (description != null) {
                it[Products.description] = description
            }
            if (price != null) {
                it[Products.price] = price
            }
            if (categoryCode != null) {
                it[Products.categoryCode] = categoryCode
            }
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

val productDao: ProductDao = ProductDaoImpl()