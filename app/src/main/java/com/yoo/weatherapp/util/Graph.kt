package com.yoo.weatherapp.util

import java.text.SimpleDateFormat
import java.util.*

object Graph {
    const val ROOT = "root_graph"
    const val MAIN = "main_graph"
    const val MAP = "map_graph"
    const val DETAIL ="detail_graph"


    const val BASE_URL = "https://api.openweathermap.org"
    fun getImageUrl(image: String): String {
        return "https://openweathermap.org/img/wn/$image@2x.png"
    }
    fun calculateCelsius(kelvin: Double): String {
        val celsius = (kelvin - 273.17).toInt()
        val addition = if (celsius >= 0) "+" else ""
        return "$addition$celsius"
    }
    fun calculateIntCelsius(kelvin: Double): Int {
        return (kelvin - 273.17).toInt()
    }

    fun getCurrentTime():String{
        return SimpleDateFormat("EEE,MMM dd", Locale.getDefault()).format(Date())
    }
}