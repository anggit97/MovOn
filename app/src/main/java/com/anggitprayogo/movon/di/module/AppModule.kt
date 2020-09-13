package com.anggitprayogo.movon.di.module

import com.anggitprayogo.movon.data.local.dao.MovieDao
import com.anggitprayogo.movon.data.repository.MovieRepository
import com.anggitprayogo.movon.data.repository.MovieRepositoryImpl
import com.anggitprayogo.movon.data.routes.MovieDBService
import com.anggitprayogo.movon.domain.MovieUseCase
import com.eoa.tech.core.util.thread.AppSchedulerProvider
import com.eoa.tech.core.util.thread.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module(includes = [RoomModule::class, NetworkModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieDBService: MovieDBService,
        movieDao: MovieDao
    ): MovieRepository {
        return MovieRepositoryImpl(movieDBService, movieDao)
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