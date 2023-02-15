package com.challenge.cabifyshop.data

import android.content.Context
import android.util.Log
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.data.database.dao.ShopDao
import com.challenge.cabifyshop.data.database.entities.ProductCartEntity
import com.challenge.cabifyshop.data.database.entities.ProductEntity
import com.challenge.cabifyshop.data.database.entities.ShopCartEntity
import com.challenge.cabifyshop.data.model.ProductModel
import com.challenge.cabifyshop.data.model.PromotionModel
import com.challenge.cabifyshop.data.model.PromotionsResponse
import com.challenge.cabifyshop.data.network.ApiResult
import com.challenge.cabifyshop.data.network.ProductService
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.toDomain
import com.challenge.cabifyshop.ui.viewmodel.TAG
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Repository class with the functions to get the data from Server or Database access
 */

class ProductRepository @Inject constructor(
    private val productService: ProductService,
    private val shopDao: ShopDao
) {

    suspend fun getProductsFromApi(): List<Product> {
        return when (val result = productService.getProducts()) {
            is ApiResult.Success<*> -> {
                val listProduct = result.data as List<ProductModel>
                listProduct.map { it.toDomain() }
            }
            is ApiResult.Error -> {
                Log.d(TAG, "${result.errorMessage}")
                emptyList()
            }
        }
    }

    suspend fun getProductsFromDatabase(): List<Product> {
        val products = shopDao.getAllProducts()
        return products.map { it.toDomain() }
    }

    suspend fun insertProductDatabase(products: List<ProductEntity>) {
        shopDao.insertAllProduct(products)
    }

    suspend fun deleteProductsDatabase() {
        shopDao.deleteAll()
    }

    suspend fun addProductToCart(codeProduct: String, quantity: Int) {
        val shopCartEntity = ShopCartEntity(codeProduct, quantity)
        shopDao.addToCart(shopCartEntity)
    }

    suspend fun getProductsFromCart(): List<ProductCartEntity> {
        return shopDao.getAllProductsFromCart()
    }

    suspend fun removeProductFromCart(code : String) {
        shopDao.removeFromChar(code)
    }

    fun getPromotions(context: Context): List<PromotionModel> {
        val promotionFromFile = context.resources.openRawResource(R.raw.promotions).bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val adapter = gson.fromJson(promotionFromFile, PromotionsResponse::class.java)
        return adapter.promotions
    }


}