package com.devileya.basicecommerce.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
object EspressoIdlingResource {
    private val RESOURCE = "GLOBAL"
    private val espressoTestIdlingResource = CountingIdlingResource(RESOURCE)

    val espressoIdlingResource: IdlingResource
        get() = espressoTestIdlingResource

    fun increment() {
        espressoTestIdlingResource.increment()
    }

    fun decrement() {
        espressoTestIdlingResource.decrement()
    }
}