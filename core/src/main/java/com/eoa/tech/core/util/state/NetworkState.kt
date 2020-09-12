package com.eoa.tech.core.util.state


/**
 * Created by Anggit Prayogo on 29,July,2020
 * GitHub : https://github.com/anggit97
 */
sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}