package com.devileya.basicecommerce.presentation.profile

import android.content.SharedPreferences
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.bumptech.glide.Glide
import com.devileya.basicecommerce.base.BaseViewModel
import com.devileya.basicecommerce.utils.GeneralEnum
import com.mikhaellopez.circularimageview.CircularImageView
import timber.log.Timber

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
class ProfileViewModel(private val sharedPreferences: SharedPreferences) : BaseViewModel() {
    val photo = ObservableField<String>(sharedPreferences.getString(GeneralEnum.PHOTO.value,""))
    val name = ObservableField<String>(sharedPreferences.getString(GeneralEnum.NAME.value, ""))

    fun onLogoutClick(view: View) {
        isLogout.value = true
        sharedPreferences.edit().clear().apply()
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: CircularImageView, url: String?) {
    if (!url.isNullOrEmpty() && url != "null") {
        Timber.d("urlImage $url")
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}
