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

    override suspend fun addNewProduct(name: String, description: String, price: Float): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.price] = price
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(id: Int, name: String, description: String, price: Float): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[Products.name] = name
            it[Products.description] = description
            it[Products.price] = price
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

val productDao: ProductDao = ProductDaoImpl()