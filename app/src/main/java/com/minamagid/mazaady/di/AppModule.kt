package com.minamagid.mazaady.di

import com.minamagid.mazaady.BuildConfig
import com.minamagid.mazaady.common.Constants.BASE_URL
import com.minamagid.mazaady.data.remote.Api
import com.minamagid.mazaady.data.repository.RepositoryImpl
import com.minamagid.mazaady.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val timeOutMinutes = 1L

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request()
            .url
            .newBuilder()
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .addHeader("private-key", "3%o8i}_;3D4bF]G5@22r2)Et1&mLJ4?\$@+16")
            .build()

        chain.proceed(newRequest)
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client =
        if (BuildConfig.DEBUG) {
            OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .readTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .connectTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .writeTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .build()
        } else {
            OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .readTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .connectTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .writeTimeout(timeOutMinutes, TimeUnit.MINUTES)
                .build()
        }
    @Provides
    @Singleton
    fun provideRetrofit(): Api {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }


}