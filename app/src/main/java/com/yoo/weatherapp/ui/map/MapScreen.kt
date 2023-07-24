package com.yoo.weatherapp.ui.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.rememberCameraPositionState
import com.yoo.weatherapp.ui.component.SearchField
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MapScreen(
    navHostController: NavHostController, vm: MapViewModel = hiltViewModel()
) {
    val state by vm.mapState.collectAsState()
    val scope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current
    var isSearched by remember { mutableStateOf(false) }
    val uiSettings = MapUiSettings(
        zoomControlsEnabled = false
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.currentLocation, state.zoom)
    }
    LaunchedEffect(key1 = cameraPositionState.isMoving) {
        vm.onEvent(MapEvent.OnMapMoved(cameraPositionState.isMoving))
    }

    if (isSearched) {
        LaunchedEffect(key1 = state.currentLocation) {
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    state.currentLocation, 10f
                )
            )
            isSearched = false
        }
    }

    Scaffold(
        floatingActionButton = {
        FloatingActionButton(
            modifier = Modifier.padding(bottom = 50.dp),
            onClick = {
            scope.launch {
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLng(state.currentLocation)
                )
            }
        }) {
            Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "location")
        }
    }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = uiSettings,
                properties = MapProperties(
                    mapStyleOptions = if (isSystemInDarkTheme()) MapStyleOptions(
                        MapStyle.darkMap
                    ) else null
                ),
                onMapClick = {
                    vm.onEvent(MapEvent.OnMapClicked(it))
                },
                cameraPositionState = cameraPositionState
            ) {
                Marker(position = state.currentLocation,
                    title = state.query,
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_ROSE
                    ),
                    onClick = {
                        it.showInfoWindow()
                        true
                    })
            }
            AnimatedVisibility(
                visible = !state.isSomeUIsVisible,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                SearchField(onBack = { navHostController.popBackStack() }, onSearch = {
                    vm.onEvent(MapEvent.OnSearch)
                    keyboard?.hide()
                    isSearched = true
                }, onValueChanged = {
                    vm.onEvent(MapEvent.OnTextChanged(it))
                }, text = state.query, modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            AnimatedVisibility(
                visible = !state.isSomeUIsVisible,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {
                        navHostController.navigate("map_detail")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, bottom = 5.dp)
                        .height(48.dp),
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}