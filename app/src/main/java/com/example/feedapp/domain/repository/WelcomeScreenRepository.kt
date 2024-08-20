package com.example.feedapp.domain.repository

import com.example.feedapp.domain.model.WelcomeScreenInfo
import kotlinx.coroutines.flow.Flow

interface WelcomeScreenRepository {
    fun fetchWelcomeScreenData(): Flow<WelcomeScreenInfo>
}