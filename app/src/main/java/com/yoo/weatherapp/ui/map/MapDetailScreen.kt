package com.yoo.weatherapp.ui.map

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yoo.weatherapp.MainActivity
import com.yoo.weatherapp.util.Graph
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapDetailScreen(
    navHostController: NavHostController,
    vm:MapViewModel= hiltViewModel()
) {
    val main= LocalContext.current as MainActivity
    var locationName by remember { mutableStateOf("") }
    val scope= rememberCoroutineScope()
    val host = remember {  SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = host)
        } ,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Enter Location title")
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = locationName,
                onValueChange = { locationName = it },
                placeholder = {
                    Text(text = "Location name")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (locationName.isNotBlank()){
                       scope.launch {
                         vm.onEvent(MapEvent.OnSaveLocationName(locationName))
                           host.showSnackbar("Saved")
                           main.startActivity(Intent(main, MainActivity::class.java))
                           main.finish()

//                           navHostController.navigate(Graph.MAIN){
//                               popUpTo(Graph.MAIN){
//                                   inclusive=true
//                               }
//                           }
                       }
                    }else{
                        scope.launch {
                            host.showSnackbar("Enter title")
                        }
                    }
                }
            ) {
                Text(text = "SAVE")
            }
        }
    }

}