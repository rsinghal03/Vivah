package com.example.vivah.di

import com.example.vivah.networking.VivahApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { getRecipeService() }
}

private fun getRecipeService(): VivahApiService {
    return createRetrofit().create(VivahApiService::class.java)
}

private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(getOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}

private fun getOkHttpClient(): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .readTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()
}

private const val BASE_URL = "https://randomuser.me/"
private const val READ_WRITE_TIMEOUT: Long = 60
private const val CONNECTION_TIMEOUT: Long = 60