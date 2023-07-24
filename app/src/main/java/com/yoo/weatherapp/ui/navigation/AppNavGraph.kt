package com.yoo.weatherapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yoo.weatherapp.ui.bottom.favourite.FavouriteScreen
import com.yoo.weatherapp.ui.bottom.location.LocationScreen
import com.yoo.weatherapp.ui.bottom.settings.SettingsScreen
import com.yoo.weatherapp.ui.component.BottomBar
import com.yoo.weatherapp.ui.detail.DetailScreen
import com.yoo.weatherapp.ui.map.MapDetailScreen
import com.yoo.weatherapp.ui.map.MapScreen
import com.yoo.weatherapp.util.Graph

@Composable
fun MainGraph(
    modifier: Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = BottomBar.Location.route
        ){
        composable(route =BottomBar.Location.route){
            LocationScreen(navHostController = navHostController)
        }
        composable(route =BottomBar.Favourite.route){
            FavouriteScreen(navHostController, )
        }
        composable(route =BottomBar.Settings.route){
            SettingsScreen()
        }
        mapGraph(navHostController)
        detailGraph(navHostController)
    }
    
}

fun NavGraphBuilder.mapGraph(navHostController: NavHostController){
    navigation(
        startDestination = "map_screen",
        route=Graph.MAP
    ){
        composable(route="map_screen"){
            MapScreen(navHostController = navHostController)
        }
        composable(route="map_detail"){
            MapDetailScreen(navHostController = navHostController)
        }
    }
}

fun NavGraphBuilder.detailGraph(navHostController: NavHostController){
    navigation(
        route = "${Graph.DETAIL}/{id}/{name}/{is_view}",
        startDestination = "detail_screen"
    ){
        composable(
            route="detail_screen",
            arguments = listOf(
                navArgument(
                    name="id",
                ){
                    type= NavType.StringType
                },
                navArgument(
                    name="name",
                ){
                    type= NavType.StringType
                },
                navArgument(
                    name="is_view",
                ){
                    type= NavType.StringType
                }
            )
        ){
            val id=it.arguments?.getString("id") ?: "0"
            val name=it.arguments?.getString("name") ?: "Andijan"
            val isView=it.arguments?.getString("is_view") ?: "false"
            DetailScreen(navHostController=navHostController, name=name, id=id.toInt(), isView=isView.toBoolean())
        }
    }
}