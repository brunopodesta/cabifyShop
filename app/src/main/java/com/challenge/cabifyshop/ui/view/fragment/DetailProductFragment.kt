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
            isEnabled = false
            setOnClickListener {
                quantity--
                binding.txvQuantity.text = quantity.toString()
                if (quantity == 0) {
                    binding.btnDecrease.setBackgroundResource(R.drawable.ic_remove_outline_24)
                    binding.btnDecrease.isEnabled = false
                }
            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.txvQuantity.text = quantity.toString()

            if(!binding.btnDecrease.isEnabled) {
                binding.btnDecrease.isEnabled = true
                binding.btnDecrease.setBackgroundResource(R.drawable.ic_remove_circle_24)
            }
        }

        binding.txvQuantity.text = quantity.toString()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}