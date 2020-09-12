package com.eoa.tech.core.util.thread

import kotlinx.coroutines.CoroutineDispatcher


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}