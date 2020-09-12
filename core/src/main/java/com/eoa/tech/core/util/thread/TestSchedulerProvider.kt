package com.eoa.tech.core.util.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
class TestSchedulerProvider : SchedulerProvider {
    override fun ui(): CoroutineDispatcher = Dispatchers.Unconfined
}