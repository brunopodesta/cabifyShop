package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.data.ProductRepository
import com.challenge.cabifyshop.data.database.entities.ProductCartEntity
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.toDomain
import javax.inject.Inject

/**
 * Use case to get the list of products from the cart using the Repository class
 */

class GetProductFromCartUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() : List<ProductCart> {
        val productList = repository.getProductsFromCart()
        return if (productList.isNotEmpty()) {
            productList.map { it.toDomain() }
        } else {
            emptyList()
        }
    }
}