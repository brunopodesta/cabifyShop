package com.challenge.cabifyshop.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.cabifyshop.domain.*
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.Promotion
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit test for ViewModel
 */

@ExperimentalCoroutinesApi
class CabifyShopViewModelTest {

    @RelaxedMockK
    private lateinit var getProductUseCase: GetProductUseCase

    @RelaxedMockK
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase

    @RelaxedMockK
    private lateinit var getProductFromCartUseCase: GetProductFromCartUseCase

    @RelaxedMockK
    private lateinit var getPromotionsUseCase: GetPromotionsUseCase

    @RelaxedMockK
    private lateinit var removeProductFromCartUseCase: RemoveProductFromCartUseCase

    private lateinit var cabifyShopViewModel: CabifyShopViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    var product1 = ProductCart("TSHIRT", "Cabify T-Shirt", 20.0, 3)
    var product2 = ProductCart("VOUCHER", "Cabify Voucher", 5.0, 2)

    val promotion1 = Promotion(
        "twoforone",
        "Buying 2 items of this article you get one FREE!!",
        listOf("VOUCHER"),
        0.0,
        2
    )
    val promotion2 = Promotion(
        "bulk_purchase",
        "Buy 3 or more items and the price for unit is 19.00€",
        listOf("TSHIRT"),
        19.0,
        3
    )

    private val promotionsList = listOf(promotion1,promotion2)

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        cabifyShopViewModel = CabifyShopViewModel(
            getProductUseCase,
            addProductToCartUseCase,
            getProductFromCartUseCase,
            getPromotionsUseCase,
            removeProductFromCartUseCase
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when in the cart are 2 items from same item without promotion the total is equal to quantity x price`() {
        val product = product1.copy(quantity = 2)
        val productCartList = listOf(product)

        coEvery { getProductFromCartUseCase() } returns productCartList
        coEvery { getPromotionsUseCase() } returns emptyList()


        cabifyShopViewModel.getProductFromCart()

        cabifyShopViewModel.calculateTotal()

        assert(cabifyShopViewModel.total.value == 40.0)
    }

    @Test
    fun `when 3 items in cart with promotion the total is equal to quantity x price less discount`() {

        val productCartList = listOf(product1)
        coEvery { getProductFromCartUseCase() } returns productCartList
        coEvery { getPromotionsUseCase() } returns promotionsList

        cabifyShopViewModel.onCreate()
        cabifyShopViewModel.getProductFromCart()

        cabifyShopViewModel.calculateTotal()

        assert(cabifyShopViewModel.total.value == 57.0)
    }

    @Test
    fun `when 2 different items in cart with promotion the total is equals to quantity x price less discount`(){

        val productCartList = listOf(product1, product2)
        coEvery { getProductFromCartUseCase() } returns productCartList
        coEvery { getPromotionsUseCase() } returns promotionsList

        cabifyShopViewModel.onCreate()
        cabifyShopViewModel.getProductFromCart()

        cabifyShopViewModel.calculateTotal()

        assert(cabifyShopViewModel.total.value == 62.0)
    }




}