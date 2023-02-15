package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.data.ProductRepository
import com.challenge.cabifyshop.data.database.entities.toDatabase
import com.challenge.cabifyshop.domain.model.Product
import javax.inject.Inject

/**
 * Use case to get the product from the server or the database using the Repository class
 */

class GetProductUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() : List<Product> {
        val products = repository.getProductsFromApi()
        return if (products.isNotEmpty()) {
            repository.deleteProductsDatabase()
            repository.insertProductDatabase(products.map { it.toDatabase() })
            products
        } else {
            repository.getProductsFromDatabase()
        }
    }
}