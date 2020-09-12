package com.eoa.tech.core.base

import androidx.lifecycle.ViewModel
import com.eoa.tech.core.util.thread.SchedulerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext


/**
 * Created by Anggit Prayogo on 24,July,2020
 * GitHub : https://github.com/anggit97
 */
abstract class BaseViewModel(private val baseDispatcher: SchedulerProvider) : ViewModel(),
    CoroutineScope {

    private val supervisorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = baseDispatcher.ui() + supervisorJob
}