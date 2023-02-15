package com.challenge.cabifyshop.ui.view.fragment

import androidx.fragment.app.Fragment
import com.challenge.cabifyshop.ui.view.MainActivity
import com.challenge.cabifyshop.ui.viewmodel.CabifyShopViewModel

/**
 * Base Fragment where the others Fragment will inherit
 */
abstract class BaseFragment : Fragment() {

    protected val activityViewModel : CabifyShopViewModel
    get() = (activity as MainActivity).mainViewModel

    protected val navController by lazy {
        (activity as MainActivity).navController
    }

}