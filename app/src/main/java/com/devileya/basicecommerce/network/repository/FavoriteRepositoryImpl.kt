package com.devileya.basicecommerce.network.repository

import com.devileya.basicecommerce.database.dao.FavoriteDao
import com.devileya.basicecommerce.database.entity.Favorite
import com.devileya.basicecommerce.domain.repository.FavoriteRepository
import com.devileya.basicecommerce.network.model.ProductModel
import com.devileya.basicecommerce.utils.UseCaseResult

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
class FavoriteRepositoryImpl(private val favoriteDao: FavoriteDao): FavoriteRepository {

    override suspend fun insert(productModel: ProductModel) {
        favoriteDao.insert(convertToFavorite(productModel))
    }

    override suspend fun update(productModel: ProductModel) {
        favoriteDao.update(convertToFavorite(productModel))
    }

    override suspend fun delete(productModel: ProductModel) {
        favoriteDao.deleteById(productModel.id)
    }

    override suspend fun get(): UseCaseResult<List<ProductModel>> {
        return try {
            val result = convertToProduct(favoriteDao.get())
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Error(e)
        }
    }

    private fun convertToFavorite(productModel: ProductModel): Favorite {
        val favorite = Favorite()
        favorite.description = productModel.description
        favorite.id = productModel.id
        favorite.imageUrl = productModel.imageUrl
        favorite.loved = productModel.loved
        favorite.price = productModel.price
        favorite.title = productModel.title

        return favorite
    }

    private fun convertToProduct(favorites: List<Favorite>): List<ProductModel> {
        val result = mutableListOf<ProductModel>()
        favorites.forEach {
            result.add(
                ProductModel(
                    id = it.id,
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