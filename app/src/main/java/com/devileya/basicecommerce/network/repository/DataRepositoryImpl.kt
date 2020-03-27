package com.devileya.basicecommerce.network.repository

import com.devileya.basicecommerce.domain.repository.DataRepository
import com.devileya.basicecommerce.network.ApiInteractor
import com.devileya.basicecommerce.network.message.response.DataSummary
import com.devileya.basicecommerce.utils.UseCaseResult

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class DataRepositoryImpl(private val apiInteractor: ApiInteractor): DataRepository {

    override suspend fun getData(): UseCaseResult<DataSummary> {
        return try {
            val result = apiInteractor.getDataAsync().await()
            UseCaseResult.Success(result[0].data!!)
        } catch (ex: Exception) {
            UseCaseResult.Error(ex)
        }
    }
}