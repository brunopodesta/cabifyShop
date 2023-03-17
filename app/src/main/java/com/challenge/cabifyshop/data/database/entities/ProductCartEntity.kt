package com.challenge.cabifyshop.data.database.entities

import com.challenge.cabifyshop.domain.model.ProductCart

data class ProductCartEntity(
    val code: String,
    val name: String,
    val price: Double,
    val quantity: Int
)

fun ProductCartEntity.toDomain() =
    ProductCart(code = code, name = name, price = price, quantity = quantity)