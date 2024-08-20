package com.example.feedapp

import android.app.Application
import android.util.Log
import com.example.feedapp.data.di.repositoryModule
import com.example.feedapp.domain.usecase.di.useCaseModule
import com.example.feedapp.networking.di.networkModule
import com.example.feedapp.ui.viewmodel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FeedApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // init Koin
        startKoin{
            Log.d("FeedApp", "Starting Koin")
            androidContext(this@FeedApp)
            // add modules
            // init Datastore
            // init Retrofit
            modules(
                useCaseModule,
                repositoryModule,
                viewModelModule,
                networkModule
            )
        }
    }
}