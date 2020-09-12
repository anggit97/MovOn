package com.eoa.tech.core.util.ext

import com.bumptech.glide.load.HttpException
import com.eoa.tech.core.util.state.ResultState
import okhttp3.ResponseBody
import okio.Timeout
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
suspend fun <T : Any> safeApiCall(apiCall: suspend () -> ResultState<T>): ResultState<T> {
    return try {
        apiCall.invoke()
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                val code = throwable.statusCode
                val errorResponse = throwable.message
                return ResultState.Error(errorResponse, code)
            }
            is ConnectException, is UnknownHostException -> ResultState.NetworkError("Internet Bermasalah, silahkan periksa kembali koneksi anda")
            is TimeoutException -> ResultState.NetworkError("Timeout")
            else -> ResultState.Error(throwable.message, 500)
        }
    }
}

fun <T : Any> safeApiErrorHandling(response: Response<T>): ResultState<T> {
    val responseCode = response.code()
    val responseError = response.errorBody()
    val errorMessageFromApi = decodeErrorMessage(responseError!!, "message")

    if (errorMessageFromApi.isNullOrEmpty()) {
        return ResultState.Error(response.message(), responseCode)
    }

    return ResultState.Error(errorMessageFromApi, responseCode)
}

private fun decodeErrorMessage(body: ResponseBody, key: String): String? {
    return JSONObject(body.string()).getString(key)
}
