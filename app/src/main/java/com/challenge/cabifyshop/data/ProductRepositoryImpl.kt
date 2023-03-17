package com.challenge.cabifyshop.data

import android.content.Context
import android.util.Log
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.data.database.dao.ShopDao
import com.challenge.cabifyshop.data.database.entities.ShopCartEntity
import com.challenge.cabifyshop.data.database.entities.toDatabase
import com.challenge.cabifyshop.data.database.entities.toDomain
import com.challenge.cabifyshop.data.model.ProductModel
import com.challenge.cabifyshop.data.model.PromotionsResponse
import com.challenge.cabifyshop.data.model.toDomain
import com.challenge.cabifyshop.data.network.ApiResult
import com.challenge.cabifyshop.data.network.ProductService
import com.challenge.cabifyshop.domain.ProductRepository
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.Promotion
import com.challenge.cabifyshop.ui.viewmodel.TAG
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Repository class with the functions to get the data from Server or Database access
 */

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val shopDao: ShopDao
) : ProductRepository {

    override suspend fun getProductsFromApi(): List<Product> {
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

    override suspend fun getProductsFromDatabase(): List<Product> {
        val products = shopDao.getAllProducts()
        return products.map { it.toDomain() }
    }

    override suspend fun insertProductDatabase(products: List<Product>) {
        shopDao.insertAllProduct(products.map { it.toDatabase() })
    }

    override suspend fun deleteProductsDatabase() {
        shopDao.deleteAll()
    }

    override suspend fun addProductToCart(codeProduct: String, quantity: Int) {
        val shopCartEntity = ShopCartEntity(codeProduct, quantity)
        shopDao.addToCart(shopCartEntity)
    }

    override suspend fun getProductsFromCart(): List<ProductCart> {
        return shopDao.getAllProductsFromCart().map { it.toDomain() }
    }

    override suspend fun removeProductFromCart(code : String) {
        shopDao.removeFromChar(code)
    }

    override fun getPromotions(context: Context): List<Promotion> {
        val promotionFromFile = context.resources.openRawResource(R.raw.promotions).bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val adapter = gson.fromJson(promotionFromFile, PromotionsResponse::class.java)
        return adapter.promotions.map { it.toDomain() }
    }


}