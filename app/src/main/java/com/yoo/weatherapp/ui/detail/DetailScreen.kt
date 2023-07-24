package com.yoo.weatherapp.ui.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.coil.rememberCoilPainter
import com.yoo.weatherapp.R
import com.yoo.weatherapp.model.LocationName
import com.yoo.weatherapp.ui.component.DeleteDialog
import com.yoo.weatherapp.util.Graph
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navHostController: NavHostController,
    name:String,
    id:Int,
    isView:Boolean,
    vm:DetailViewModel= hiltViewModel()
) {
    LaunchedEffect(key1 = Unit){
        vm.onEvent(DetailEvent.OnLoadWeather(name))
    }
    val state by vm.state.collectAsState()
    var isDeleteDialogOpen by remember { mutableStateOf(false) }
    val scope= rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = name)
                },
                navigationIcon = {
                    IconButton(onClick = {navHostController.popBackStack()}) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isDeleteDialogOpen=true
                    }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete")
                    }
                }
            )
        }
    ) {padding->

        if (isDeleteDialogOpen) {
            DeleteDialog(
                onDelete = {
                    scope.launch {
                        vm.onEvent(
                            DetailEvent.OnDeleteWeather(
                                LocationName(
                                    id = id,
                                    name = name,
                                    isSaved = false,
                                    savedDate = Graph.getCurrentTime()
                                )
                            )
                        )
                        isDeleteDialogOpen = false
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                        navHostController.popBackStack()
                    }
                },
                onDismiss = { isDeleteDialogOpen = false },
                text = name
            )
        }



        if (state.isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(modifier = Modifier.size(50.dp), color = Color.Red)
            }
        }

        state.success?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = Graph.getCurrentTime())
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val painter =
                        rememberCoilPainter(request = Graph.getImageUrl(data.weather[0].icon))
                    Image(
                        painter = painter,
                        contentDescription = "painter",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(text = "${(data.main.temp - 273.15).toInt()}" + "°C", fontSize = 30.sp)
                }
                Text(text = data.weather[0].main, fontSize = 20.sp)
                Divider()
                Spacer(modifier = Modifier.height(10.dp))
                RowIconText(icon = R.drawable.wind, text = "wind ${data.wind.speed} m/s")
                RowIconText(icon = R.drawable.hum, text = "humidity ${data.main.humidity} %")
                RowIconText(icon = R.drawable.tire, text = "pressure ${data.main.pressure} hpa")
            }
        }
    }

}


@Composable
fun RowIconText(
    icon:Int,
    text:String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = "icon")
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text)

    }
    
}
