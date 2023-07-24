package com.yoo.weatherapp.ui.component


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val icon: ImageVector,
    val route: String
){
    object Location:BottomBar(
        Icons.Outlined.LocationOn,
        "location"
    )

    object Favourite:BottomBar(
        Icons.Outlined.Favorite,
        "favourite"
    )
    object Settings:BottomBar(
        Icons.Outlined.Settings,
    "settings"
    )
}
