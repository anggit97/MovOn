package com.anggitprayogo.movon.di.component

import android.content.Context
import com.anggitprayogo.movon.di.module.AppModule
import com.anggitprayogo.movon.di.module.ViewModelModule
import com.anggitprayogo.movon.feature.detail.MovieDetailActivity
import com.anggitprayogo.movon.feature.favourite.FavouriteActivity
import com.anggitprayogo.movon.feature.favouritedetail.FavouriteDetailActivity
import com.anggitprayogo.movon.feature.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    fun inject(activity: MovieDetailActivity)

    fun inject(activity: FavouriteActivity)

    fun inject(activity: FavouriteDetailActivity)
}