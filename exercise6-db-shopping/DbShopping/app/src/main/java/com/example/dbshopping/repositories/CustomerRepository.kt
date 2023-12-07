package com.example.dbshopping.repositories

import com.example.dbshopping.models.Customer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CustomerRepository private constructor() {
    companion object {
        @Volatile
        private var INSTANCE: CustomerRepository? = null

        fun getInstance(): CustomerRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CustomerRepository()
                INSTANCE = instance
                instance
            }
        }
    }

    private val db = FirebaseFirestore.getInstance()
    private val customerCollection = db.collection("customers")

    suspend fun addCustomer(customer: Customer) {
        customer.id?.let {
            customerCollection.document(it).set(customer).await()
        }
    }

    suspend fun getCustomer(id: String): Customer? {
        val document = customerCollection.document(id).get().await()

        return if (document.exists()) {
            document.toObject(Customer::class.java)
        } else null
    }

    suspend fun getAllCustomers(): List<Customer> {
        val query = customerCollection.get().await()
        return query.toObjects(Customer::class.java)
    }

    suspend fun updateCustomer(customer: Customer) {
        customer.id?.let {
            customerCollection.document(it).set(customer).await()
        }
    }

    suspend fun deleteCustomer(id: String) {
        customerCollection.document(id).delete().await()
    }
}