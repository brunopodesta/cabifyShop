package com.challenge.cabifyshop.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.challenge.cabifyshop.R
import com.challenge.cabifyshop.databinding.ListProductFragmentBinding
import com.challenge.cabifyshop.ui.view.adapter.ProductAdapter

/**
 * Fragment to display the list of Products
 */

class ProductListFragment : BaseFragment() {

    private var _binding : ListProductFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListProductFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val productAdapter = ProductAdapter(){ productCode ->
            activityViewModel.onProductSelected(productCode)
            navController.navigate(R.id.action_listProductFragment_to_detailProductFragment)
        }

        binding.productRV.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context,2)
        }


        activityViewModel.productListLiveData.observe(viewLifecycleOwner) { productList ->
            productAdapter.setData(productList)
        }

        activityViewModel.cartCounter.observe(viewLifecycleOwner) { counter ->
            if( counter > 0) {
                binding.txvCounterCart.visibility = View.VISIBLE
                binding.txvCounterCart.text = counter.toString()
            } else {
                binding.txvCounterCart.visibility = View.INVISIBLE
            }


        }

        binding.fabCart.setOnClickListener() {
            navController.navigate(R.id.action_listProductFragment_to_castFragment)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}