package com.example.readily.navigation

import androidx.navigation.NavController

interface INavigationRouter {
    fun navigateToMain()
    fun navigateToAddEdit(id: Long?)
    fun navigateToStatistics()
    fun navigateToBookChallenge()
    fun navigateToDetail(id: Long?)
    fun returnBack()
    fun getNavController(): NavController
    fun navigateToSettings()

}