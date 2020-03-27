package com.devileya.basicecommerce.network.message.response

import com.devileya.basicecommerce.network.model.CategoryModel
import com.devileya.basicecommerce.network.model.ProductModel

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
data class DataResponse(
    val data: DataSummary? = null
)

data class DataSummary(
    val category: List<CategoryModel>? = null,
    val productPromo: List<ProductModel>? = null
)