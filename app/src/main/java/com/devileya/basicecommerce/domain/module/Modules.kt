package com.devileya.basicecommerce.domain.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.devileya.basicecommerce.database.AppDatabase
import com.devileya.basicecommerce.domain.repository.CartRepository
import com.devileya.basicecommerce.domain.repository.DataRepository
import com.devileya.basicecommerce.domain.repository.FavoriteRepository
import com.devileya.basicecommerce.network.ApiInteractor
import com.devileya.basicecommerce.network.repository.CartRepositoryImpl
import com.devileya.basicecommerce.network.repository.DataRepositoryImpl
import com.devileya.basicecommerce.network.repository.FavoriteRepositoryImpl
import com.devileya.basicecommerce.presentation.cart.CartViewModel
import com.devileya.basicecommerce.presentation.detail.DetailViewModel
import com.devileya.basicecommerce.presentation.favorite.FavoriteViewModel
import com.devileya.basicecommerce.presentation.feed.FeedViewModel
import com.devileya.basicecommerce.presentation.home.HomeViewModel
import com.devileya.basicecommerce.presentation.login.LoginViewModel
import com.devileya.basicecommerce.presentation.profile.ProfileViewModel
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
const val URL = "https://api.themoviedb.org/3/discover/"

val appModules =  module {

    single { provideSettingsPreferences(androidContext()) }

    factory {
        createWebService<ApiInteractor>(
            okHttpClient = createHttpClient(),
            baseUrl = URL)
    }

    factory { AppDatabase.getAppDataBase(androidContext())?.favoriteDao() }
    factory { AppDatabase.getAppDataBase(androidContext())?.cartDao() }


    factory<DataRepository> { DataRepositoryImpl(get()) }
    factory<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    factory<CartRepository> { CartRepositoryImpl(get()) }

    // Login Activity
    viewModel { LoginViewModel() }

    // Home Fragment
    viewModel { HomeViewModel(get()) }

    // Feed Fragment
    viewModel { FeedViewModel(get()) }

    // Cart Fragment
    viewModel { CartViewModel(get()) }

    //Profile Fragment
    viewModel { ProfileViewModel(get()) }

    // Detail Fragment
    viewModel { DetailViewModel(get(), get()) }

    // Favorite
    viewModel { FavoriteViewModel(get()) }
}

fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(2 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}

private fun provideSettingsPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)