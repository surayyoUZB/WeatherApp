package com.yoo.weatherapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yoo.weatherapp.ui.component.BottomAppBar
import com.yoo.weatherapp.ui.component.SearchField
import com.yoo.weatherapp.ui.map.MapScreen
import com.yoo.weatherapp.ui.navigation.MainGraph
import com.yoo.weatherapp.util.Graph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navHostController: NavHostController= rememberNavController()) {
    val current by navHostController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
                    BottomAppBar(navHostController = navHostController)
        },
        floatingActionButton = {
            if (current?.destination?.route=="location"){
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(Graph.MAP)
                    }
                ) 
                {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = "add")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {padding ->
        MainGraph(
            modifier = Modifier.padding(padding),
            navHostController = navHostController
        )
    }

}