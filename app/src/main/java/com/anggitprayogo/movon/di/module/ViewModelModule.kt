package com.anggitprayogo.movon.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anggitprayogo.movon.feature.detail.MovieDetailViewModel
import com.anggitprayogo.movon.feature.favourite.FavouriteListViewModel
import com.anggitprayogo.movon.feature.main.MainViewModel
import com.eoa.tech.core.util.viewmodel.ViewModelFactory
import com.eoa.tech.core.util.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    internal abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteListViewModel::class)
    internal abstract fun bindFavouriteListViewModel(viewModel: FavouriteListViewModel): ViewModel
}