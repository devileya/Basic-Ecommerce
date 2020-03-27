package com.devileya.basicecommerce.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var imageUrl: String,
    var title: String,
    var description: String,
    var price: String,
    var loved: Int
)