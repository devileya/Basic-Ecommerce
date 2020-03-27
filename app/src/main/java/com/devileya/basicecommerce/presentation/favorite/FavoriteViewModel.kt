package com.devileya.basicecommerce.presentation.favorite

import androidx.lifecycle.MutableLiveData
import com.devileya.basicecommerce.base.BaseViewModel
import com.devileya.basicecommerce.domain.repository.FavoriteRepository
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository) : BaseViewModel() {

    val data = MutableLiveData<List<ProductModel>>()

    init { getData() }

    private fun getData() {
        showProgress.value = true
        launch {
            val result = withContext(Dispatchers.Default) { favoriteRepository.get() }
            showProgress.value = false
            when (result) {
                is UseCaseResult.Success<List<ProductModel>> -> data.value = result.data
                is UseCaseResult.Error -> showError.value = result.exception.message
            }
        }
    }
}
