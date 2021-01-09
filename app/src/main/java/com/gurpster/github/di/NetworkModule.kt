package com.gurpster.github.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gurpster.github.data.remote.LiveDataCallAdapterFactory
import com.gurpster.github.data.remote.WebService
import com.gurpster.github.util.Config.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Williaan Souza (dextter) on 09/01/2021
 * Contact williaanlopes@gmail.com
 */

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor {
            val request: Request = it.request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build()
            it.proceed(request)
        }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Config.BASE_URL)
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideWebService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)

}