package com.challenge.cabifyshop.data.database.dao

import androidx.room.*
import com.challenge.cabifyshop.data.database.entities.ProductCartEntity
import com.challenge.cabifyshop.data.database.entities.ProductEntity
import com.challenge.cabifyshop.data.database.entities.ShopCartEntity

/**
 * DAO interface to interact with database
 */

@Dao
interface ShopDao {

    @Query("SELECT * FROM product_table")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM product_table WHERE  code = :code")
    suspend fun getProductByCode(code: String): ProductEntity

    @Query("DELETE FROM product_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(productEntity: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(shopCartEntity: ShopCartEntity)

    @Update
    suspend fun updateCart(shopCartEntity: ShopCartEntity)

    @Query("DELETE FROM shop_cart WHERE product_code = :code")
    suspend fun removeFromChar(code: String)

    @Query("DELETE FROM shop_cart")
    suspend fun emptyCart()

    @Query("SELECT product_table.code, product_table.name,product_table.price, shop_cart.quantity " +
            "FROM product_table, shop_cart WHERE product_table.code = shop_cart.product_code")
    suspend fun getAllProductsFromCart(): List<ProductCartEntity>


}