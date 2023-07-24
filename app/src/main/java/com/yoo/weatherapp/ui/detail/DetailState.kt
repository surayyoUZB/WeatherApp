package com.yoo.weatherapp.ui.detail

import com.yoo.weatherapp.model.CurrentWeather

data  class DetailState(
    val isLoading:Boolean=false,
    val success:CurrentWeather?=null,
    val error:String=""
)