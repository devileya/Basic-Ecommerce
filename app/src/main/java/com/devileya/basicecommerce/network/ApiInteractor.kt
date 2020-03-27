package com.devileya.basicecommerce.network

import com.devileya.basicecommerce.network.message.response.DataResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
interface ApiInteractor {
    @GET("https://private-4639ce-ecommerce56.apiary-mock.com/home")
    fun getDataAsync(): Deferred<List<DataResponse>>
}