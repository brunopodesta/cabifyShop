package com.challenge.cabifyshop.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.cabifyshop.domain.GetProductUseCase
import com.challenge.cabifyshop.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "CabifyShopViewModel"

@HiltViewModel
class CabifyShopViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : ViewModel() {

    private val _productListLiveData = MutableLiveData<List<Product>>()
    val productListLiveData : LiveData<List<Product>> get() = _productListLiveData

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct get() = _selectedProduct

    fun onCreate() {
        viewModelScope.launch {
            val listProducts = getProductUseCase()
            _productListLiveData.postValue(listProducts)
            for(product in listProducts) {
                Log.d(TAG, "Product: ${product.code} ${product.name} ${product.price} ${product.productType}")
            }
        }
    }

    fun onProductSelected(productCode : String) {
        val product = _productListLiveData.value?.find { it.code == productCode } ?: return
        _selectedProduct.value = product
    }

}