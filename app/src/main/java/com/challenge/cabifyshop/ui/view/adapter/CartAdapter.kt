package com.challenge.cabifyshop.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.CartProductItemBinding
import com.challenge.cabifyshop.domain.model.ProductCart
import com.challenge.cabifyshop.domain.model.TypeProduct
import com.challenge.cabifyshop.ui.view.fragment.CartAction

/**
 * Adapter to show the list of items in the Cart
 */

class CartAdapter(private val cartAction: CartAction) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var productCartEntityList = ArrayList<ProductCart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.cart_product_item, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productCartEntityList[position]
        holder.bind(product, cartAction)
    }

    override fun getItemCount(): Int {
        return productCartEntityList.size
    }

    fun setData(productList: List<ProductCart>) {
        productCartEntityList.clear()
        productCartEntityList.addAll(productList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CartProductItemBinding.bind(view)

        fun bind(productCart: ProductCart, cartAction: CartAction) {
            binding.apply {
                txtCartName.text = productCart.name
                txvCartPrice.text =
                    itemView.context.getString(R.string.price_amount, productCart.price)
                txvCartQuantity.text = productCart.quantity.toString()
                when (productCart.productType) {
                    is TypeProduct.MUG -> imvCartProduct.setImageResource(R.drawable.mug)
                    is TypeProduct.OTHER -> imvCartProduct.setImageResource(R.drawable.cabify_logo)
                    is TypeProduct.TSHIRT -> imvCartProduct.setImageResource(R.drawable.tshirt)
                    is TypeProduct.VOUCHER -> imvCartProduct.setImageResource(R.drawable.voucher)
                }
                if (productCart.quantity == 1) {
                    btnDecrease.setImageResource(R.drawable.ic_delete_24)
                } else {
                    btnDecrease.setImageResource(R.drawable.ic_remove_circle_24)
                }

                btnDecrease.setOnClickListener {
                    if (productCart.quantity == 1) {
                        cartAction.onRemove(productCart.code)
                    } else {
                        val quantity = productCart.quantity - 1
                        cartAction.onModify(productCart.code, quantity)
                    }
                }

                btnIncrease.setOnClickListener {
                    val quantity = productCart.quantity + 1
                    cartAction.onModify(productCart.code, quantity)
                }

                btnDeleteCart.setOnClickListener { cartAction.onRemove(productCart.code) }
            }
        }
    }
}