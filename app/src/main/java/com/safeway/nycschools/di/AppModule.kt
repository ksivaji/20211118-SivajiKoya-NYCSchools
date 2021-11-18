package com.safeway.nycschools.di

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.safeway.nycschools.BuildConfig
import com.safeway.nycschools.data.remote.NYCSchoolsService
import com.safeway.nycschools.data.remote.interceptors.ConnectivityInterceptor
import com.safeway.nycschools.data.remote.interceptors.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class AppModule {

    protected open fun baseUrl(): HttpUrl = HttpUrl.get("https://data.cityofnewyork.us/")

    @Singleton
    @Provides
    fun provideNYCSchoolsService(
        okHttpClient: OkHttpClient
    ) = provideService(okHttpClient, NYCSchoolsService::class.java)

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    fun provideOkHttpClient(
        connectivityInterceptor: ConnectivityInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logInterceptor)
        }
        httpClient.apply {
            addInterceptor(headerInterceptor)
            addInterceptor(connectivityInterceptor)
        }
        return httpClient.build()
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = MediaType.parse("application/json")
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(okHttpClient)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }
                    .asConverterFactory(contentType!!)
            ).build()
    }

    private fun <T> provideService(
        okHttpClient: OkHttpClient,
        clazz: Class<T>
    ): T {
        return createRetrofit(okHttpClient).create(clazz)
    }

}
