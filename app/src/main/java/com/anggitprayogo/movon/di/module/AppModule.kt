package com.anggitprayogo.movon.di.module

import com.anggitprayogo.movon.data.repository.MovieRepository
import com.anggitprayogo.movon.data.repository.MovieRepositoryImpl
import com.anggitprayogo.movon.data.routes.MovieDBService
import com.anggitprayogo.movon.domain.MovieUseCase
import com.anggitprayogo.movon.network.Network
import com.anggitprayogo.movon.network.interceptor.NetworkInterceptor
import com.eoa.tech.core.util.thread.AppSchedulerProvider
import com.eoa.tech.core.util.thread.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module
class AppModule {

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

    @Singleton
    @Provides
    fun provideMovieRepository(movieDBService: MovieDBService): MovieRepository {
        return MovieRepositoryImpl(movieDBService)
    }

    @Singleton
    @Provides
    fun provideAppUseCase(
        movieRepository: MovieRepository,
    ): MovieUseCase {
        return MovieUseCase(movieRepository)
    }

    @Singleton
    @Provides
    fun provideAppScheduler(): SchedulerProvider = AppSchedulerProvider()
}