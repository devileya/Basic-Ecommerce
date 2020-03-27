package com.devileya.basicecommerce.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
abstract class BaseViewModel : ViewModel(), CoroutineScope {
    companion object {
        val showProgress = MutableLiveData<Boolean>(false)
        val showError = MutableLiveData<String>()
        val isLogout = MutableLiveData<Boolean>(false)
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}