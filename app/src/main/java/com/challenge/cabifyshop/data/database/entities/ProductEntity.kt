package com.challenge.cabifyshop.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.challenge.cabifyshop.domain.model.Product

/**
 * Product table
 */

@Entity(tableName = "product_table")
data class ProductEntity (
    @PrimaryKey
    @ColumnInfo(name = "code") val code : String,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "price") val price : Double)

fun Product.toDatabase() = ProductEntity(code = code, name = name, price = price)