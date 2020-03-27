package com.devileya.basicecommerce.database.dao

import androidx.room.*
import com.devileya.basicecommerce.database.entity.Cart
import com.devileya.basicecommerce.network.model.ProductModel

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cart: Cart?): Long

    @Delete
    fun delete(cart: Cart?)

    @Update
    fun update(cart: Cart?): Int

    @Query("SELECT * FROM cart")
    fun get(): List<Cart>

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteById(id: String) : Int
}