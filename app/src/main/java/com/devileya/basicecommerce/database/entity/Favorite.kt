package com.devileya.basicecommerce.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Arif Fadly Siregar 2020-03-27.
 */
@Entity(tableName = "favorite")
data class Favorite(
    @PrimaryKey
    var id: String,
    var imageUrl: String,
    var title: String,
    var description: String,
    var price: String,
    var loved: Int
): Serializable {
    constructor():this("","","","","",0)
}