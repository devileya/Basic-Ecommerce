package com.devileya.basicecommerce.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.presentation.MainActivity
import com.devileya.basicecommerce.utils.GeneralEnum
import com.devileya.basicecommerce.utils.adapter.ProductAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val parentActivity = activity as MainActivity
        parentActivity.showBackButton(true)

        viewModel.data.observe(this, Observer {
            showFavoriteList(it)
        })
    }

    private fun showFavoriteList(favorites: List<ProductModel>) {
        rv_product.apply {
            adapter = ProductAdapter(favorites, false) {
                val bundle = Bundle()
                bundle.putParcelable(GeneralEnum.PRODUCT.value, it)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.detailFragment, true).build()
                Navigation.findNavController(view!!).navigate(R.id.goToDetail, bundle, navOptions)
            }
        }
    }
}
