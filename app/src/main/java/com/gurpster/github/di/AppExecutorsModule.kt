package com.gurpster.github.di

import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

@Module
@InstallIn(ApplicationComponent::class)
open class AppExecutorsModule(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    @Inject
    constructor(): this (
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIO(): Executor {
        return diskIO
    }
    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}