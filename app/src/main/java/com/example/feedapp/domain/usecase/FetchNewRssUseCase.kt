package com.example.feedapp.domain.usecase

import com.example.feedapp.data.model.RssItem
import com.example.feedapp.networking.service.RssService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse

class FetchNewRssUseCase(private val rssService: RssService) {

    operator fun invoke(url: String): Flow<List<RssItem>> = flow {
        try {
            val response = rssService.fetchRssFeed(url).awaitResponse()  // Pass the URL to the service
            if (response.isSuccessful) {
                val rssFeed = response.body()
                rssFeed?.channel?.items?.let { items ->
                    emit(items.filter { it.tags != null })  // Optionally filter or categorize here
                }
            } else {
                throw Exception("Failed to fetch RSS feed: ${response.code()}")
            }
        } catch (e: Exception) {
            throw Exception("Error fetching RSS feed", e)
        }
    }
}