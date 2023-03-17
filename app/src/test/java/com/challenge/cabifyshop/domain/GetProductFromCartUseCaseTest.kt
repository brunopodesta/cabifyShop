package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.domain.model.ProductCart
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit test for GetProductFromCartUseCase
 */
class GetProductFromCartUseCaseTest {

    @RelaxedMockK
    private lateinit var productRepository: ProductRepository

    lateinit var getProductFromCartUseCase: GetProductFromCartUseCase

    private var product1 = ProductCart("TSHIRT", "Cabify T-Shirt", 20.0, 3)
    private var product2 = ProductCart("VOUCHER", "Cabify Voucher", 5.0, 2)

    val productCartList = listOf(product1, product2)

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getProductFromCartUseCase = GetProductFromCartUseCase(productRepository)
    }

    @Test
    fun `when there is no item on Cart return empty list`() = runBlocking() {
        coEvery { productRepository.getProductsFromCart() } returns emptyList()

        val response = getProductFromCartUseCase()

        assert(response.isEmpty())
    }

    @Test
    fun `when there is item on Cart returns the items`() = runBlocking {

        coEvery { productRepository.getProductsFromCart() } returns productCartList

        val response = getProductFromCartUseCase()

        assert(response == productCartList)

    }

}