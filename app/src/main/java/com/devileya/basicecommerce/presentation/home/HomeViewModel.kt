package com.devileya.basicecommerce.presentation.home

import androidx.lifecycle.MutableLiveData
import com.devileya.basicecommerce.base.BaseViewModel
import com.devileya.basicecommerce.domain.repository.DataRepository
import com.devileya.basicecommerce.network.message.response.DataSummary
import com.devileya.basicecommerce.network.model.CategoryModel
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
internal class HomeViewModel (private val dataRepository: DataRepository) : BaseViewModel() {

    val categories = MutableLiveData<List<CategoryModel>>()
    val products = MutableLiveData<List<ProductModel>>()

    init { fetchData() }

    private fun fetchData() {
        showProgress.value = true
        launch {
            when (val result = withContext(Dispatchers.Default) { dataRepository.getData()}) {
                is UseCaseResult.Success<DataSummary> -> {
                    if (result.data.category!!.isNotEmpty()) {
                        Timber.d("result categories ${result.data.category}")
                        categories.value = result.data.category
                    }
                    if (result.data.productPromo!!.isNotEmpty()) {
                        Timber.d("result products ${result.data.productPromo}")
                        products.value = result.data.productPromo
                    }
                }
                is UseCaseResult.Error -> {
                    showError.value = result.exception.message
                    Timber.d("showError ${showError.value}")
                }
            }
            showProgress.value = false
        }
    }
}
