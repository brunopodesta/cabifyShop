package com.challenge.cabifyshop.domain.model

import com.challenge.cabifyshop.data.database.entities.ProductEntity
import com.challenge.cabifyshop.data.model.ProductModel

data class Product(
    val code: String,
    val name: String,
    val price: Double,
) {
    val productType get() = when(code) {
        "VOUCHER" -> TypeProduct.VOUCHER()
        "TSHIRT" -> TypeProduct.TSHIRT()
        "MUG" -> TypeProduct.MUG()
        else -> TypeProduct.OTHER()
    }
}

sealed class TypeProduct() {
    class VOUCHER : TypeProduct()
    class TSHIRT  : TypeProduct()
    class MUG  : TypeProduct()
    class OTHER  : TypeProduct()
}

fun ProductModel.toDomain() = Product(code = code, name = name, price = price)
fun ProductEntity.toDomain() = Product(code = code, name = name, price = price)
