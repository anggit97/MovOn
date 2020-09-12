package com.anggitprayogo.movon.di.module

import androidx.lifecycle.ViewModelProvider
import com.eoa.tech.core.util.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}