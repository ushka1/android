package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Categories
import com.example.models.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CategoryDaoImpl : CategoryDao {
    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name],
        code = row[Categories.code],
        description = row[Categories.description]
    )


    override suspend fun allCategories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun category(id: Int): Category? = dbQuery {
        Categories
            .select { Categories.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun addNewCategory(name: String, code: String, description: String?): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
            it[Categories.code] = code
            it[Categories.description] = description
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun editCategory(id: Int, name: String, code: String, description: String?): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }) {
            it[Categories.name] = name
            it[Categories.code] = code
            it[Categories.description] = description
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean = dbQuery {
        Categories.deleteWhere { Categories.id eq id } > 0
    }
}

val categoryDao: CategoryDao = CategoryDaoImpl()