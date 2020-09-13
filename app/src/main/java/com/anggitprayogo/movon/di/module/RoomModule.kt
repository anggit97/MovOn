package com.anggitprayogo.movon.di.module

import android.content.Context
import androidx.room.Room
import com.anggitprayogo.movon.data.local.AppDatabase
import com.anggitprayogo.movon.data.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 13,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module
class RoomModule {
    private val mDataBaseName = "Movon.db"

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            mDataBaseName
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}