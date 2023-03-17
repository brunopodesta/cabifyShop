package com.challenge.cabifyshop.domain

import com.challenge.cabifyshop.domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit test for GetProductUseCase
 */

class GetProductUseCaseTest {

    @RelaxedMockK
    private lateinit var productRepository: ProductRepository

    lateinit var getProductUseCase: GetProductUseCase

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        getProductUseCase = GetProductUseCase(productRepository)
    }

    @Test
    fun `when the api doesnt return anything get list of products from database`() = runBlocking {
        coEvery { productRepository.getProductsFromApi() } returns emptyList()

        getProductUseCase()

        coVerify { productRepository.getProductsFromDatabase() }

    }

    @Test
    fun `when the api return a list of product return from api`() = runBlocking {

        val productList = listOf(Product("voucher", "voucher", 5.0))
        coEvery { productRepository.getProductsFromApi() } returns productList

        val response = getProductUseCase()

        coVerify(exactly = 1) { productRepository.deleteProductsDatabase() }
        coVerify(exactly = 1) { productRepository.insertProductDatabase(productList) }
        assert(response == productList)
    }

}