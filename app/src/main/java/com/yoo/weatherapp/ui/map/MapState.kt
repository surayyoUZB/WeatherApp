package com.yoo.weatherapp.ui.map

import com.google.android.gms.maps.model.LatLng

data class MapState(
    val query: String = "",
    val currentLocation: LatLng = LatLng(40.7176, 72.6329),
    val isSomeUIsVisible: Boolean = true,
    val zoom: Float = 4f
)
