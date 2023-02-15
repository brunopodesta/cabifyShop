package com.challenge.cabifyshop.domain.model

/**
 * Sealed class to represent the type of products
 */

sealed class TypeProduct() {
    class VOUCHER : TypeProduct()
    class TSHIRT  : TypeProduct()
    class MUG  : TypeProduct()
    class OTHER  : TypeProduct()
}
