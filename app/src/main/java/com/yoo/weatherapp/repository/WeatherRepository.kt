package com.yoo.weatherapp.repository

import com.yoo.weatherapp.model.CurrentWeather
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.model.LocationName
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun saveLocation(locationName:LocationName)
    suspend fun deleteLocation(locationName:LocationName)
    fun getAllLocations():Flow<List<LocationName>>
    suspend fun updateLocation(id:Int, isSaved:Boolean)
    suspend fun getCurrentWeather(query:String):Flow<CurrentWeather>


    suspend fun saveFavourite(favoriteName: FavoriteName)
    suspend fun deleteFavourite(name:String)
    fun getAllFavourites():Flow<List<FavoriteName>>

    suspend fun saveTheme(index:Int)
    fun getTheme():Flow<Int>


}