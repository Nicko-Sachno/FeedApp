package com.example.feedapp.ui.viewmodel.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.data.model.RssItem
import com.example.feedapp.domain.usecase.FetchNewRssUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class NewsAndCultureViewModel(
    private val fetchNewRssUseCase: FetchNewRssUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<NewsAndCultureUiState>(NewsAndCultureUiState.Loading)
    val state: StateFlow<NewsAndCultureUiState> = _state

    private var pollingJob: Job? = null
    private val delay: Long = 5000 // 5 seconds by default

    fun startPolling() {
        cancelPolling() // Cancel any existing job before starting a new one
        pollingJob = viewModelScope.launch {
            while (true) {
                try {
                    // Fetch both feeds concurrently
                    val sportFeedDeferred = async { fetchNewRssUseCase("https://www.ynet.co.il/Integration/StoryRss3.xml").firstOrNull() }
                    val cultureFeedDeferred = async { fetchNewRssUseCase("https://www.ynet.co.il/Integration/StoryRss538.xml").firstOrNull() }

                    val sportFeed = sportFeedDeferred.await() ?: emptyList()
                    val cultureFeed = cultureFeedDeferred.await() ?: emptyList()

                    val combinedFeed = (sportFeed + cultureFeed).sortedBy { it.title } // Sort by title or any other criteria

                    _state.value = if (combinedFeed.isNotEmpty()) {
                        NewsAndCultureUiState.Ready(combinedFeed)
                    } else {
                        NewsAndCultureUiState.Error("No articles available at the moment.")
                    }
                } catch (e: Exception) {
                    _state.value = NewsAndCultureUiState.Error("Failed to fetch combined feeds: ${e.message}")
                }
                delay(delay)
            }
        }
    }

    fun cancelPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}

sealed class NewsAndCultureUiState {
    data object Loading : NewsAndCultureUiState()
    data class Error(val message: String) : NewsAndCultureUiState()
    data class Ready(val messages: List<RssItem>) : NewsAndCultureUiState()
}