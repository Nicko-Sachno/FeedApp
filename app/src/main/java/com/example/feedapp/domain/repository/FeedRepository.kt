package com.example.feedapp.domain.repository

import com.example.feedapp.domain.model.FeedInfo
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun fetchFeedData(): Flow<FeedInfo>
}