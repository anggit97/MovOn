package com.eoa.tech.core.util.state


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
sealed class ResultState<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val error: String?, val statusCode: Int?) : ResultState<Nothing>()
    data class NetworkError(val error: String?) : ResultState<Nothing>()
}