package com.example.feedapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feedapp.ui.screens.WelcomeScreen
import com.example.feedapp.ui.screens.feeds.FeedContainerScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                modifier = modifier,
                onNavigationToFeedContainer = {
                    navController.navigate(Screen.FeedContainer.route)
                },
                viewModel = koinViewModel()
            )
        }

        composable(Screen.FeedContainer.route) {
            FeedContainerScreen(
                navHostController = navController
            )
        }
    }
}