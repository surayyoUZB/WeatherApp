package com.yoo.weatherapp.ui.detail

import com.yoo.weatherapp.model.LocationName

sealed interface DetailEvent {
    data class OnLoadWeather(val query:String):DetailEvent
    data class OnDeleteWeather(val locationName:LocationName):DetailEvent
 }