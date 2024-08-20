package com.example.feedapp.ui.screens.feeds

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feedapp.data.datastore.DataStoreManager
import com.example.feedapp.ui.screens.WebViewScreen
import com.example.feedapp.ui.viewmodel.feed.CarFeedUiState
import com.example.feedapp.ui.viewmodel.feed.CarFeedViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun CarFeedScreen(
    modifier: Modifier,
    viewModel: CarFeedViewModel,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val dataStoreManager: DataStoreManager = koinInject()
    val scope = rememberCoroutineScope()

    var selectedUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        Log.i("FeedScreen", "Calling startPolling")
        viewModel.startPolling()
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.i("FeedScreen", "Calling cancelPolling")
            viewModel.cancelPolling()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(42.dp)
    ) {
        selectedUrl?.let { url ->
            WebViewScreen(url = url)
        } ?: LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            when (uiState) {
                is CarFeedUiState.Ready -> {
                    val rssItems = (uiState as CarFeedUiState.Ready).messages
                    items(rssItems.size) { index ->
                        val rssItem = rssItems[index]
                        Row(
                            modifier = Modifier
                                .padding(24.dp)
                                .clickable {
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column {
                                Text(text = "Title: ${rssItem.title ?: "No Title"}")

                                rssItem.link?.let { link ->
                                    Text(
                                        text = "Link: $link",
                                        color = Color.Blue,
                                        modifier = Modifier.clickable {
                                            selectedUrl = link
                                            scope.launch {
                                                dataStoreManager.saveWelcomeLabel(rssItem.title ?: "")
                                            }
                                        }
                                    )
                                } ?: Text(text = "Link: No Link")

                                Text(text = "Description: ${rssItem.description ?: "No Description"}")
                                Text(text = "Published Date: ${rssItem.pubDate ?: "No Date"}")
                            }
                        }
                    }
                }
                is CarFeedUiState.Loading -> {
                    item {
                        Text(text = "Loading...")
                    }
                }
                is CarFeedUiState.Error -> {
                    item {
                        Text(text = (uiState as CarFeedUiState.Error).message)
                    }
                }
            }
        }
    }
}