package com.yoo.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.yoo.weatherapp.ui.bottom.settings.SettingViewModel
import com.yoo.weatherapp.ui.navigation.RootNavigation
import com.yoo.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm = hiltViewModel<SettingViewModel>()
            val theme = when(vm.theme.value){
                0 -> isSystemInDarkTheme()
                1 -> false
                else -> true
                }
            WeatherAppTheme(
                darkTheme = theme
            ) {
                val navHostController= rememberNavController()
                RootNavigation(navHostController = navHostController)

            }
        }
    }
}

