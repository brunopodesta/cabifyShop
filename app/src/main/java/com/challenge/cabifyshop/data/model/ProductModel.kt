package com.challenge.cabifyshop.data.model

import com.challenge.cabifyshop.domain.model.Product
import com.google.gson.annotations.SerializedName

/**
 * Class represent a Product from the request
 */

data class ProductModel(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Double
)

fun ProductModel.toDomain() = Product(code = code, name = name, price = price)