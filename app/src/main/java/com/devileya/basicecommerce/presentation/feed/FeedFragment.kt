package com.devileya.basicecommerce.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.GeneralEnum
import com.devileya.basicecommerce.utils.adapter.ProductAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private val viewModel: FeedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.feed_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.products.observe(this, Observer {
            showProductList(it)
        })
    }

    private fun showProductList(products: List<ProductModel>) {
        rv_product.apply {
            adapter = ProductAdapter(products, true) {
                val bundle = Bundle()
                bundle.putParcelable(GeneralEnum.PRODUCT.value, it)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.detailFragment, true).build()
                Navigation.findNavController(view!!).navigate(R.id.goToDetail, bundle, navOptions)
            }
        }
    }
}
