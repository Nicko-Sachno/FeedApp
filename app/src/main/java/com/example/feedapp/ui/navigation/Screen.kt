package com.example.feedapp.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object FeedContainer : Screen("feedContainer")
    object CarFeed : Screen("cardFeed")
    object NewsAndCulture : Screen("newsAndCulture")
}