package com.devileya.basicecommerce.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.database.entity.Cart
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.GeneralEnum
import com.devileya.basicecommerce.utils.adapter.ProductAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    private val viewModel: CartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.data.observe(this, Observer {
            showCartList(it)
        })
    }

    private fun showCartList(carts: List<ProductModel>) {
        rv_product.apply {
            adapter = ProductAdapter(carts, false) {
                val bundle = Bundle()
                bundle.putParcelable(GeneralEnum.PRODUCT.value, it)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.detailFragment, true).build()
                Navigation.findNavController(view!!).navigate(R.id.goToDetail, bundle, navOptions)
            }
        }
    }
}
