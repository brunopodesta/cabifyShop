package com.challenge.cabifyshop.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.CartFragmentBinding
import com.challenge.cabifyshop.ui.view.adapter.CartAdapter

/**
 * Cart fragment to display all the product in the basket and
 * show the total of the purchase
 *
 */

class CartFragment : BaseFragment(), CartAction {

    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activityViewModel.getProductFromCart()

        val adapterCart = CartAdapter(this)


        activityViewModel.productCartListLiveData.observe(viewLifecycleOwner) { productList ->
            setVisibilityCart(productList.isNotEmpty())
            adapterCart.setData(productList)
            activityViewModel.calculateTotal()
        }

        binding.cartListRV.apply {
            adapter = adapterCart
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        activityViewModel.total.observe(viewLifecycleOwner) { amount ->
            binding.apply {
                txvTotal.text = getString(R.string.total, amount)
                txvDiscount.text = getString(R.string.discounts, activityViewModel.discount)
                txvSubtotal.text = getString(R.string.subtotal, activityViewModel.subtotalResult)
            }
        }
    }

    private fun setVisibilityCart(productInList: Boolean) {
        var visible = View.VISIBLE
        var messageVisibility = View.GONE
        if (!productInList) {
            visible = View.GONE
            messageVisibility = View.VISIBLE

        }
        binding.apply {
            cartListRV.visibility = visible
            btnProcessShop.visibility = visible
            txvSubtotal.visibility = visible
            txvDiscount.visibility = visible
            txvTotal.visibility = visible
            txvEmptyCart.visibility = messageVisibility
        }
    }

    override fun onModify(code: String, quantity: Int) {
        activityViewModel.modifyProductQuantity(code, quantity)
    }

    override fun onRemove(code: String) {
        activityViewModel.removeProductFromCart(code)
    }
}

/**
 * Interface use it to know the button action in the CarAdapter
 */

interface CartAction {
    fun onModify(code: String, quantity: Int)
    fun onRemove(code: String)
}