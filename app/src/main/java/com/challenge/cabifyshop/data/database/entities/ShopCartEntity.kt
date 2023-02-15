package com.challenge.cabifyshop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Shop Cart table
 */

@Entity(tableName = "shop_cart")
data class ShopCartEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_code") val code : String,
    @ColumnInfo(name = "quantity") val quantity : Int
)
