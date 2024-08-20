package com.example.feedapp.ui.screens.feeds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feedapp.data.datastore.DataStoreManager
import com.example.feedapp.ui.viewmodel.feed.NewsAndCultureUiState
import com.example.feedapp.ui.viewmodel.feed.NewsAndCultureViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun NewsAndCultureScreen(
    modifier: Modifier,
    viewModel: NewsAndCultureViewModel
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val dataStoreManager: DataStoreManager = koinInject()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.startPolling()
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.cancelPolling()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            when (uiState) {
                is NewsAndCultureUiState.Ready -> {
                    items((uiState as NewsAndCultureUiState.Ready).messages.size) { index ->
                        val rssItem = (uiState as NewsAndCultureUiState.Ready).messages[index]
                        Text(
                            text = rssItem.title ?: "No Title",
                            modifier = Modifier
                                .padding(24.dp)
                                .clickable {
                                    scope.launch {
                                        dataStoreManager.saveWelcomeLabel(rssItem.title ?: "")
                                    }
                                }
                        )
                    }
                }
                is NewsAndCultureUiState.Loading -> {
                    item { Text("Loading...") }
                }
                is NewsAndCultureUiState.Error -> {
                    item { Text("Error: ${(uiState as NewsAndCultureUiState.Error).message}") }
                }
            }
        }
    }
}