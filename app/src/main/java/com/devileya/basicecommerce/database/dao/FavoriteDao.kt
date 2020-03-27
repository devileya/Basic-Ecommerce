package com.devileya.basicecommerce.database.dao

import androidx.room.*
import com.devileya.basicecommerce.database.entity.Favorite
import com.devileya.basicecommerce.network.model.ProductModel

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite?): Long

    @Delete
    fun delete(favorite: Favorite??)

    @Update
    fun update(favorite: Favorite??): Int

    @Query("SELECT * FROM favorite")
    fun get(): List<Favorite>

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteById(id: String?) : Int
}