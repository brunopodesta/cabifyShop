package com.challenge.cabifyshop.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.DetailProductFragmentBinding
import com.challenge.cabifyshop.domain.model.TypeProduct

/**
 * Fragment to show the Product image, price and description.
 * From here the user can add the product to the Cart.
 */

const val QUANTITY_VALUE_KEY = "quantity_value_key"

class DetailProductFragment : BaseFragment() {

    private var _binding: DetailProductFragmentBinding? = null
    private val binding get() = _binding!!

    var quantity = 0

    var isProductAdded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailProductFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            quantity = savedInstanceState.getInt(QUANTITY_VALUE_KEY)
        }

        if (quantity > 0) {
            setEnableDecreaseButton(true)
        } else {
            setEnableDecreaseButton(false)
        }

        activityViewModel.selectedProduct.observe(viewLifecycleOwner) { product ->

            binding.apply {

                txtProdName.text = product.name
                txtProdPrice.text = getString(R.string.price_amount, product.price)
                when (product.productType) {
                    is TypeProduct.MUG -> imgProduct.setImageResource(R.drawable.mug)
                    is TypeProduct.OTHER -> imgProduct.setImageResource(R.drawable.cabify_logo)
                    is TypeProduct.TSHIRT -> imgProduct.setImageResource(R.drawable.tshirt)
                    is TypeProduct.VOUCHER -> imgProduct.setImageResource(R.drawable.voucher)
                }

                btnAddCart.setOnClickListener {
                    val quantity = txvQuantity.text.toString().toInt()
                    if (quantity > 0) {
                        isProductAdded = true
                        activityViewModel.addToCart(product.code, quantity)
                        Toast.makeText(context, getString(R.string.item_added), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                val promotions = activityViewModel.getPromotionByCode(product.code)

                if (!promotions.isNullOrEmpty()) {
                    val stringBuilder = StringBuilder()
                    for (prom in promotions) {
                        stringBuilder.append(prom.description)
                        stringBuilder.append("\n")
                    }
                    var text = stringBuilder.toString()
                    text = text.substring(0, text.lastIndexOf("\n"))
                    txvPromotion.text = text
                }
            }
        }

        binding.btnDecrease.apply {
            setOnClickListener {
                quantity--
                binding.txvQuantity.text = quantity.toString()
                if (quantity == 0) {
                    setEnableDecreaseButton(false)
                }
            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.txvQuantity.text = quantity.toString()

            if (!binding.btnDecrease.isEnabled) {
                setEnableDecreaseButton(true)
            }
        }

        binding.txvQuantity.text = quantity.toString()
    }

    fun setEnableDecreaseButton(enable: Boolean) {
        if (enable) {
            binding.btnDecrease.isEnabled = true
            binding.btnDecrease.setBackgroundResource(R.drawable.ic_remove_circle_24)
        } else {
            binding.btnDecrease.setBackgroundResource(R.drawable.ic_remove_outline_24)
            binding.btnDecrease.isEnabled = false
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        val value = binding.txvQuantity.text.toString().toInt()
        outState.putInt(QUANTITY_VALUE_KEY, value)
        super.onSaveInstanceState(outState)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}