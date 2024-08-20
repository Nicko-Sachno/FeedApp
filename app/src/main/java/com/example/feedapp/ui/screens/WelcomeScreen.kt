package com.example.feedapp.ui.screens

import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.feedapp.domain.model.WelcomeScreenInfo
import com.example.feedapp.domain.repository.WelcomeScreenRepository
import com.example.feedapp.ui.viewmodel.welcome_screen.WelcomeScreenViewModel
import com.example.feedapp.ui.viewmodel.welcome_screen.date
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigationToFeedContainer: () -> Unit,
    viewModel: WelcomeScreenViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Ensure the date is updated whenever the screen is recomposed
    androidx.compose.runtime.SideEffect {
        viewModel.updateDate()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Center,
        ) {
            Text(text = uiState.name)
            Text(text = uiState.date)  // Display the updated date from the ViewModel
            Text(text = uiState.label)
        }
        Button(
            onClick = onNavigationToFeedContainer,
            modifier = Modifier
                .align(BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Text("Go To Feeds")
        }
    }
}

@Composable
@Preview
fun PreviewWelcomeScreen() {
    val mockDataStoreManager = object {
        val welcomeLabel: Flow<String?> = flowOf("Welcome to the Mock Feed App")
    }

    val mockWelcomeScreenRepo = object : WelcomeScreenRepository {
        override fun fetchWelcomeScreenData(): Flow<WelcomeScreenInfo> {
            return mockDataStoreManager.welcomeLabel.map {
                WelcomeScreenInfo(label = it ?: "")
            }
        }
    }

    val viewModel = WelcomeScreenViewModel(repository = mockWelcomeScreenRepo)

    WelcomeScreen(
        onNavigationToFeedContainer = {},
        viewModel = viewModel
    )
}