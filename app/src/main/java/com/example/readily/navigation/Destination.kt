package com.example.readily.navigation

sealed class Destination(val route: String) {
    object MainScreen: Destination("main")
    object AddEditScreen: Destination("add_edit")
    object DetailScreen: Destination("detail")
    object StatisticsScreen: Destination("statistics")
    object BookChallengeScreen: Destination("book_challenge")
    object SettingsScreen: Destination("settings")
}