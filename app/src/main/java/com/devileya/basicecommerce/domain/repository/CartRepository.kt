package com.devileya.basicecommerce.domain.repository

import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
interface CartRepository {
    suspend fun get(): UseCaseResult<List<ProductModel>>
    suspend fun insert(productModel: ProductModel)
    suspend fun update(productModel: ProductModel)
    suspend fun delete(productModel: ProductModel)
}