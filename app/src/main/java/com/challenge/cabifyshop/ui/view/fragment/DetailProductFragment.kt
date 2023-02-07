package com.challenge.cabifyshop.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.DetailProductFragmentBinding
import com.challenge.cabifyshop.domain.model.TypeProduct

class DetailProductFragment : BaseFragment() {

    private var _binding : DetailProductFragmentBinding? = null
    private val binding get() = _binding!!

    var quantity = 0

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
                txtProdPrice.text = "${product.price}€"
                when (product.productType) {
                    is TypeProduct.MUG -> imgProduct.setImageResource(R.drawable.mug)
                    is TypeProduct.OTHER -> imgProduct.setImageResource(R.drawable.cabify_logo)
                    is TypeProduct.TSHIRT -> imgProduct.setImageResource(R.drawable.tshirt)
                    is TypeProduct.VOUCHER -> imgProduct.setImageResource(R.drawable.voucher)
                }
            }
        }

        binding.btnDecrease.setOnClickListener {
            if (quantity > 0) {
                quantity--
                binding.txvQuantity.text = quantity.toString()

            }
        }

        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.txvQuantity.text = quantity.toString()
        }

        binding.txvQuantity.text = quantity.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}