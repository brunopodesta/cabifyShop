package com.challenge.cabifyshop.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.cabifyshop.domain.*
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.Promotion
import com.challenge.cabifyshop.domain.model.PromotionsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel use it to communicate the results of operations to the Views
 */
const val TAG = "CabifyShopViewModel"

@HiltViewModel
class CabifyShopViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getProductFromCartUseCase: GetProductFromCartUseCase,
    private val getPromotionsUseCase: GetPromotionsUseCase,
    private val removeProductFromCartUseCase: RemoveProductFromCartUseCase
) : ViewModel() {

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData: LiveData<List<Product>> get() = _productListLiveData

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct get() = _selectedProduct

    private val _productListCartLiveData = MutableLiveData<List<ProductCart>>()
    val productCartListLiveData: LiveData<List<ProductCart>> get() = _productListCartLiveData

    private val _total = MutableLiveData<Double>()
    val total: LiveData<Double> get() = _total

    private val _promotions = MutableLiveData<List<Promotion>>()
    val promotions get() = _promotions

    private val _cartCounter = MutableLiveData<Int>()
    val cartCounter : LiveData<Int> get() = _cartCounter

    var isLoading = true

    fun onCreate() {
        viewModelScope.launch {
            val listProducts = getProductUseCase()
            _productListLiveData.postValue(listProducts)
            val listPromotions = getPromotionsUseCase()
            _promotions.value = listPromotions
            isLoading = false
        }

        getProductFromCart()

    }

    fun onProductSelected(productCode: String) {
        val product = _productListLiveData.value?.find { it.code == productCode } ?: return
        _selectedProduct.value = product
    }

    fun modifyProductQuantity(codeProduct: String, quantity: Int) {
        viewModelScope.launch {
            addProductToCartUseCase.addProductToCart(codeProduct, quantity)
            getProductFromCart()
        }
    }

    fun addToCart(codeProduct: String, quantity: Int) {
        viewModelScope.launch {
            val response = getProductFromCartUseCase()
            if (response.isNotEmpty()) {

                val product: ProductCart? = response.find { it.code == codeProduct }
                if (product != null) {
                    addProductToCartUseCase.addProductToCart(
                        codeProduct,
                        quantity + product.quantity
                    )
                } else {
                    addProductToCartUseCase.addProductToCart(codeProduct, quantity)
                }
                val totalProducst = response.sumOf { it.quantity } + quantity
                _cartCounter.postValue(totalProducst)
            } else {
                _cartCounter.postValue(quantity)
                addProductToCartUseCase.addProductToCart(codeProduct, quantity)
            }
        }
    }

    fun getProductFromCart() {
        viewModelScope.launch {
            val productList = getProductFromCartUseCase()
            _productListCartLiveData.postValue(productList)
            _cartCounter.postValue(productList.sumOf { it.quantity })
        }
    }

    fun removeProductFromCart(code: String) {
        viewModelScope.launch {
            removeProductFromCartUseCase.removeProductFromCart(code)
            getProductFromCart()
        }
    }

    fun getPromotionByCode(code: String): List<Promotion>? {
        val result = promotions.value?.filter { it.products.contains(code) }
        return result?.ifEmpty {
            emptyList()
        }
    }

    var subtotalResult = 0.0
    var discount = 0.0

    fun calculateTotal() {
        subtotalResult = 0.0
        discount = 0.0
        val products = _productListCartLiveData.value
        if (products != null) {
            for (product in products) {
                subtotalResult += product.quantity * product.price
                discount += discounts(product)
            }
        }
        val totalCaculate = subtotalResult - discount
        _total.postValue(totalCaculate)
    }

    private fun discounts(product: ProductCart): Double {
        var discount = 0.0
        val proms = _promotions.value
        if (proms != null) {
            for (prom in proms) {
                if (prom.products.contains(product.code)) {
                    when (prom.promotionType) {
                        is PromotionsType.TWOFORONE -> {
                            if (product.quantity >= prom.min_quantity) {
                                val amount = (product.quantity / 2)
                                discount += amount * product.price
                            }
                        }
                        is PromotionsType.BULKPURCHASE -> {
                            if (product.quantity >= prom.min_quantity) {
                                val difference =
                                    (product.price * product.quantity) - (product.quantity * prom.new_price)

                                discount += difference
                            }
                        }
                        is PromotionsType.NONSPECIFIYC -> {}
                    }
                }
            }
        }
        return discount
    }

}