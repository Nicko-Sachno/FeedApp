package com.example.feedapp.data.di

import com.example.feedapp.data.datastore.DataStoreManager
import com.example.feedapp.data.repository.WelcomeScreenRepositoryImpl
import com.example.feedapp.domain.repository.WelcomeScreenRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {

    single<DataStoreManager> { DataStoreManager(context = androidContext()) }

    single<WelcomeScreenRepository> { WelcomeScreenRepositoryImpl(get<DataStoreManager>()) }
}