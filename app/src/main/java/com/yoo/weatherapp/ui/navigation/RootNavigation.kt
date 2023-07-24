package com.yoo.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yoo.weatherapp.ui.main.MainScreen
import com.yoo.weatherapp.util.Graph

@Composable
fun RootNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Graph.MAIN
    ){
        composable(route = Graph.MAIN){
            MainScreen()
        }

    }

}