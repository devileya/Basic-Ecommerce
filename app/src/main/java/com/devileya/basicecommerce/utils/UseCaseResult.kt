package com.devileya.basicecommerce.utils

/**
 * Created by Arif Fadly Siregar 2020-03-25.
 */
sealed class UseCaseResult<out T : Any?> {
    class Success<out T : Any>(val data: T) : UseCaseResult<T>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}