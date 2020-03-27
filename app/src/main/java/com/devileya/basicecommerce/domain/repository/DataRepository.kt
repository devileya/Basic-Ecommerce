package com.devileya.basicecommerce.domain.repository

import com.devileya.basicecommerce.network.message.response.DataSummary
import com.devileya.basicecommerce.utils.UseCaseResult

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
interface DataRepository {
    suspend fun getData(): UseCaseResult<DataSummary>
}