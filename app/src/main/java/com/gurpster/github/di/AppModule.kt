package com.gurpster.github.di

import android.content.Context
import com.gurpster.github.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }
}