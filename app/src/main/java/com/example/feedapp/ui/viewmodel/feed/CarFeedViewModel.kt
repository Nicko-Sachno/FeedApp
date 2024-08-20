package com.example.feedapp.ui.viewmodel.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feedapp.data.model.RssItem
import com.example.feedapp.domain.usecase.FetchNewRssUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CarFeedViewModel(
    val fetchNewRssUseCase: FetchNewRssUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CarFeedUiState>(CarFeedUiState.Loading)

    val state: StateFlow<CarFeedUiState>
        get() = _state

    private val refreshing = MutableStateFlow(false)
    private var pollingJob: Job? = null

    private val delay: Long = 5000 // 5 seconds by default

    fun startPolling() {
        cancelPolling() // Cancel any existing job before starting a new one
        pollingJob = viewModelScope.launch {
            while (true) {
                fetchNewRssUseCase("https://www.ynet.co.il/Integration/StoryRss550.xml")
                    .onStart {
                        refreshing.emit(true)
                    }
                    .catch { e ->
                        _state.value = CarFeedUiState.Error("Failed to fetch: ${e.message}")
                    }
                    .collect { feedInfo ->
                        _state.value = CarFeedUiState.Ready(feedInfo)
                    }
                refreshing.emit(false)
                delay(delay) // Delay for 2 seconds between fetches
            }
        }
    }

    fun cancelPolling() {
        pollingJob?.cancel()
        pollingJob = null
    }
}

sealed class CarFeedUiState {
    data object Loading : CarFeedUiState()
    data class Error(val message: String) : CarFeedUiState()
    data class Ready(val messages: List<RssItem>) : CarFeedUiState()
}