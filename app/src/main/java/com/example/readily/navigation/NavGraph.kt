package com.example.readily.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readily.screens.AddEdit.AddEditScreen
import com.example.readily.screens.Challenge.BookChallengeScreen
import com.example.readily.screens.Detail.DetailScreen
import com.example.readily.screens.MainScreen
import com.example.readily.screens.SettingsScreen
import com.example.readily.screens.StatisticsScreen

@Composable
fun NavGraph(navHostController: NavHostController = rememberNavController(), navigationRouter: INavigationRouter = remember {
    NavigationRouterImpl(navHostController)
}, startDestination: String) {
    
    NavHost(navController = navHostController, startDestination = startDestination) {

        composable(Destination.MainScreen.route) {
            MainScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.AddEditScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ){
            val id = it.arguments?.getLong("id")
            AddEditScreen(
                navigationRouter = navigationRouter,
                id = id)
        }

        composable(Destination.AddEditScreen.route) {
            AddEditScreen(navigationRouter = navigationRouter, id = null)
        }

        composable(Destination.DetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ){
            val id = it.arguments?.getLong("id")
            DetailScreen(
                navigationRouter = navigationRouter,
                id = id)
        }

        composable(Destination.DetailScreen.route) {
            DetailScreen(navigationRouter = navigationRouter, id = null)
        }
        
        composable(Destination.StatisticsScreen.route) {
            StatisticsScreen(navigationRouter = navigationRouter)
        }
        
        composable(Destination.BookChallengeScreen.route) {
            BookChallengeScreen(navigationRouter = navigationRouter)
        }

        composable(Destination.SettingsScreen.route) {
            SettingsScreen(navigationRouter = navigationRouter)
        }
        
    }
    
}