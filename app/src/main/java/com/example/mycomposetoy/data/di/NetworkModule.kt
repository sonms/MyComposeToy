package com.example.mycomposetoy.data.di

import com.example.mycomposetoy.BuildConfig
import com.example.mycomposetoy.data.di.constants.NetworkConstants.API_KEY_VALUE
import com.example.mycomposetoy.data.di.constants.NetworkConstants.CONTENT_TYPE_JSON
import com.example.mycomposetoy.data.di.constants.NetworkConstants.HEADER_API_KEY
import com.example.mycomposetoy.data.di.constants.NetworkConstants.TIMEOUT_SECONDS
import com.example.mycomposetoy.data.qualifier.HeaderInterceptor
import com.example.mycomposetoy.data.qualifier.LoggingInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @LoggingInterceptor
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    @HeaderInterceptor
    fun providesHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(HEADER_API_KEY, API_KEY_VALUE)
            .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @LoggingInterceptor loggingInterceptor: HttpLoggingInterceptor,
        @HeaderInterceptor headerInterceptor : Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun providesConverterFactory(): Converter.Factory = Json.asConverterFactory(CONTENT_TYPE_JSON.toMediaType())

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactory)
        .client(client)
        .build()
}