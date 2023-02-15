package com.challenge.cabifyshop.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.CardiviewProductItemBinding
import com.challenge.cabifyshop.domain.model.Product
import com.challenge.cabifyshop.domain.model.TypeProduct

/**
 * Adapter to show the list of Products
 */

class ProductAdapter(private val onClickCallBack: (String) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var productList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.cardiview_product_item, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product, onClickCallBack)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(products: List<Product>) {
        productList.clear()
        productList.addAll(products)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binging = CardiviewProductItemBinding.bind(view)

        fun bind(product: Product, onClick: (String) -> Unit) {
            binging.apply {
                txvProductName.text = product.name
                txvProductPrice.text =
                    itemView.context.getString(R.string.price_amount, product.price)
                when (product.productType) {
                    is TypeProduct.MUG -> productImage.setImageResource(R.drawable.mug)
                    is TypeProduct.OTHER -> productImage.setImageResource(R.drawable.cabify_logo)
                    is TypeProduct.TSHIRT -> productImage.setImageResource(R.drawable.tshirt)
                    is TypeProduct.VOUCHER -> productImage.setImageResource(R.drawable.voucher)
                }
            }
            binging.root.setOnClickListener {
                onClick(product.code)
            }
        }
    }


}