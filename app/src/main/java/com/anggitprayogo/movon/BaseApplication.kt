package com.anggitprayogo.movon

import android.app.Application
import com.anggitprayogo.movon.di.component.AppComponent
import com.anggitprayogo.movon.di.component.DaggerAppComponent


/**
 * Created by Anggit Prayogo on 12,September,2020
 * GitHub : https://github.com/anggit97
 */
class BaseApplication: Application(){

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}