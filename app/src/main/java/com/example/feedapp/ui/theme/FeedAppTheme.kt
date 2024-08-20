package com.example.feedapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FeedAppTheme(content: @Composable () -> Unit) {
    //TODO: get color scheme from theme
    MaterialTheme(
        content = content
    )
}