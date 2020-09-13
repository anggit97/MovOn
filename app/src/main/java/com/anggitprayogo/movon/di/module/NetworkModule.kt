package com.anggitprayogo.movon.di.module

import com.anggitprayogo.movon.data.routes.MovieDBService
import com.anggitprayogo.movon.network.Network
import com.anggitprayogo.movon.network.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module
class NetworkModule{

    @Singleton
    @Provides
    fun provideNetworkInterceptor(): Interceptor {
        return NetworkInterceptor()
    }

    @Singleton
    @Provides
    fun provideApiService(
        networkInterceptor: Interceptor
    ): MovieDBService {
        return Network.retrofitClient(networkInterceptor = networkInterceptor)
            .create(MovieDBService::class.java)
    }
}