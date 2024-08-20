package com.example.feedapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.feedapp.ui.navigation.NavigationGraph
import com.example.feedapp.ui.theme.FeedAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FeedApp()
        }
    }

    @Composable
    private fun FeedApp() {
        val navController = rememberNavController()
        FeedAppTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
            ) { innerPadding ->
                NavigationGraph(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
    }

    @Preview
    @Composable
    private fun PreviewFeedApp() {
        FeedAppTheme {
            FeedApp()
        }
    }
}