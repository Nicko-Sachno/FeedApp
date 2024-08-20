package com.example.feedapp.networking.di

import com.example.feedapp.networking.service.RssService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

val networkModule = module {
    // Provide HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
    }

    // Provide OkHttpClient
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    // Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://www.ynet.co.il/")
            .client(get<OkHttpClient>())
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
    }

    // Provide RssService
    single {
        get<Retrofit>().create(RssService::class.java)
    }
}