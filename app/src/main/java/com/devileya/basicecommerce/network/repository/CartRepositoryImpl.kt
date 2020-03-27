package com.devileya.basicecommerce.network.repository

import com.devileya.basicecommerce.database.dao.CartDao
import com.devileya.basicecommerce.database.entity.Cart
import com.devileya.basicecommerce.domain.repository.CartRepository
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
class CartRepositoryImpl(private val cartDao: CartDao): CartRepository {

    override suspend fun insert(productModel: ProductModel) {
        cartDao.insert(convertToCart(productModel))
    }

    override suspend fun update(productModel: ProductModel) {
        cartDao.update(convertToCart(productModel))
    }

    override suspend fun delete(productModel: ProductModel) {
        cartDao.deleteById(productModel.id)
    }

    override suspend fun get(): UseCaseResult<List<ProductModel>> {
        return try {
            val result = convertToProduct(cartDao.get())
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    private fun convertToCart(productModel: ProductModel): Cart {
        return Cart(
            id = 0,
            description = productModel.description,
            imageUrl = productModel.imageUrl,
            loved = productModel.loved,
            price = productModel.price,
            title = productModel.title
        )
    }

    private fun convertToProduct(carts: List<Cart>): List<ProductModel> {
        val result = mutableListOf<ProductModel>()
        carts.forEach {
            result.add(
                ProductModel(
                    id = it.id.toString(),
                    imageUrl = it.imageUrl,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    loved = it.loved
            ))
        }
        return result
    }
}