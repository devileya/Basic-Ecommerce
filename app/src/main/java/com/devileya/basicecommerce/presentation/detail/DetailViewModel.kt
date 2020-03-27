package com.devileya.basicecommerce.presentation.detail

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.devileya.basicecommerce.base.BaseViewModel
import com.devileya.basicecommerce.domain.repository.CartRepository
import com.devileya.basicecommerce.domain.repository.FavoriteRepository
import com.devileya.basicecommerce.network.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class DetailViewModel(private val favoriteRepository: FavoriteRepository, private val cartRepository: CartRepository) : BaseViewModel() {
    val data = MutableLiveData<ProductModel>()
    val favorite = MutableLiveData<ProductModel>()
    val cart = MutableLiveData<ProductModel>()
    val isFavorite = ObservableBoolean(data.value?.loved == 1)

    fun onFavoriteClick(view: View) {
        when (isFavorite.get()) {
            true -> deleteFavorite(data.value)
            false -> insertFavorite(data.value)
        }
    }

    fun onBuyClick(view: View) {
        insertCart(data.value)
    }

    private fun insertFavorite(productModel: ProductModel?) {
        launch { withContext(Dispatchers.Default) { favoriteRepository.insert(data.value!!) } }
        favorite.value = productModel
    }

    private fun deleteFavorite(productModel: ProductModel?) {
        launch { withContext(Dispatchers.Default) { favoriteRepository.delete(data.value!!) } }
        favorite.value = null
    }

    private fun insertCart(productModel: ProductModel?) {
        launch { withContext(Dispatchers.Default) { cartRepository.insert(data.value!!) } }
        cart.value = productModel
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}
