package com.example.readily.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(private val navController: NavController): INavigationRouter {

    override fun navigateToMain() {
        navController.navigate(Destination.MainScreen.route)
    }
    override fun navigateToAddEdit(id: Long?) {
        if(id != null) {
            navController.navigate(Destination.AddEditScreen.route + "/" + id)
        } else {
            navController.navigate(Destination.AddEditScreen.route)
        }
    }

    override fun navigateToDetail(id: Long?) {
        if(id != null) {
            navController.navigate(Destination.DetailScreen.route + "/" + id)
        } else {
            navController.navigate(Destination.DetailScreen.route)
        }
    }

    override fun navigateToStatistics() {
        navController.navigate(Destination.StatisticsScreen.route)
    }

    override fun navigateToBookChallenge() {
        navController.navigate(Destination.BookChallengeScreen.route)
    }

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun getNavController(): NavController {
        return navController
    }

    override fun navigateToSettings() {
        navController.navigate(Destination.SettingsScreen.route)
    }
}