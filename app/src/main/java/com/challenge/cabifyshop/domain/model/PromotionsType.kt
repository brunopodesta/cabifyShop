package com.challenge.cabifyshop.domain.model

/**
 * Sealed class to represent the type of promotions
 */

sealed class PromotionsType {
    class TWOFORONE() : PromotionsType()
    class BULKPURCHASE () : PromotionsType()
    class NONSPECIFIYC () : PromotionsType()

}
