package com.example.feedapp.ui.screens.feeds

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel


@Composable
fun FeedContainerScreen(
    navHostController: NavHostController
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationFeedBar(navHostController)
        }
    ) { innerPadding ->
        CarFeedScreen(
            modifier = Modifier.padding(innerPadding),
            viewModel = koinViewModel(),
        )
//        NewsAndCultureScreen(
//            modifier = Modifier.padding(innerPadding),
//            viewModel = koinViewModel()
//        )
    }
}

@Composable
fun NavigationFeedBar(navHostController: NavHostController) {
    BottomAppBar {
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            icon = { Icon(Icons.Filled.Face, contentDescription = "Car") }
        )
    }
}