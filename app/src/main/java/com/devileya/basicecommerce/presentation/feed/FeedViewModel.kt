package com.devileya.basicecommerce.presentation.feed

import androidx.lifecycle.MutableLiveData
import com.devileya.basicecommerce.base.BaseViewModel
import com.devileya.basicecommerce.domain.repository.DataRepository
import com.devileya.basicecommerce.network.message.response.DataSummary
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class FeedViewModel(private val dataRepository: DataRepository) : BaseViewModel() {
    val products = MutableLiveData<List<ProductModel>>()

    init { fetchData() }

    private fun fetchData() {
        showProgress.value = true
        launch {
            when (val result = withContext(Dispatchers.Default) { dataRepository.getData()}) {
                is UseCaseResult.Success<DataSummary> -> {
                    if (result.data.productPromo!!.isNotEmpty()) {
                        products.value = result.data.productPromo
                    }
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                }
            }
            showProgress.value = false
        }
    }
}
