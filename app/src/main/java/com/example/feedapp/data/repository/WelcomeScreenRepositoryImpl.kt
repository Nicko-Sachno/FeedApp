package com.example.feedapp.data.repository

import com.example.feedapp.data.datastore.DataStoreManager
import com.example.feedapp.domain.model.WelcomeScreenInfo
import com.example.feedapp.domain.repository.WelcomeScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WelcomeScreenRepositoryImpl(
    private val dataStoreManager: DataStoreManager
): WelcomeScreenRepository {
    override fun fetchWelcomeScreenData(): Flow<WelcomeScreenInfo> {
        return dataStoreManager.welcomeLabel.map {
            WelcomeScreenInfo(label = it ?: "")
        }
    }
}