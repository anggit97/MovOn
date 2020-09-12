package com.eoa.tech.core.util.state


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
sealed class LoaderState {
    object ShowLoading: LoaderState()
    object HideLoading: LoaderState()
}