package com.devileya.basicecommerce.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.bumptech.glide.Glide
import com.devileya.basicecommerce.R
import com.devileya.basicecommerce.databinding.DetailFragmentBinding
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.presentation.MainActivity
import com.devileya.basicecommerce.utils.GeneralEnum
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val parentActivity = activity as MainActivity
        parentActivity.showBackButton(true)

        val data = arguments?.get(GeneralEnum.PRODUCT.value) as ProductModel
        Glide.with(this)
            .load(data.imageUrl)
            .into(iv_product)
        viewModel.data.value = data
        binding.data = data

        if (data.loved == 1) iv_heart.imageTintList = getColorStateList(iv_heart.context, R.color.red)

        viewModel.cart.observe(this, Observer {
            if (it != null) {
                parentActivity.showBackButton(false)
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.cartFragment, true).build()
                Navigation.findNavController(view!!).navigate(R.id.goToCart, null, navOptions)
            }
        })

        viewModel.favorite.observe(this, Observer {
            if (it != null) {
                viewModel.isFavorite.set(true)
                val ivAnimation = AnimatedVectorDrawableCompat.create(iv_heart.context, R.drawable.ic_heart_anim)
                iv_heart.setImageDrawable(ivAnimation)
                ivAnimation?.start()
                iv_heart.imageTintList = getColorStateList(iv_heart.context, R.color.red)
            } else {
                iv_heart.setImageDrawable(getDrawable(iv_heart.context, R.drawable.ic_heart))
                iv_heart.imageTintList = getColorStateList(iv_heart.context, R.color.greyLight)
                viewModel.isFavorite.set(false)
            }
        })
    }

}
