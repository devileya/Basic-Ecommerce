package com.devileya.basicecommerce.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
@Parcelize
data class ProductModel(
    val id: String,
    val imageUrl: String,
    val title: String,
    val description: String,
    val price: String,
    val loved: Int
): Parcelable {
    constructor():this("","","","","",0)
}