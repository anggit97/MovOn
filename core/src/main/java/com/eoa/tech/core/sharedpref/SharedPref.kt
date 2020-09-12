package com.eoa.tech.core.sharedpref

import android.content.Context
import android.content.SharedPreferences


/**
 * Created by Anggit Prayogo on 02,August,2020
 * GitHub : https://github.com/anggit97
 */
object SharedPref {

    private const val SHARED_PREF_APP = "shared_pref_app"

    fun sharedPrefClient(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_APP, Context.MODE_PRIVATE)
    }
}