package com.anggitprayogo.movon.network.interceptor

import com.anggitprayogo.movon.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .build()
        request = request.newBuilder().url(url).addHeader("Accept", "application/json").build()
        return chain.proceed(request)
    }
}