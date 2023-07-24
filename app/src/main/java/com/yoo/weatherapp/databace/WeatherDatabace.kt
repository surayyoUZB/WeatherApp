package com.yoo.weatherapp.databace

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.model.LocationName


@Database(entities = [LocationName::class, FavoriteName::class], version = 2, exportSchema = false)
abstract class WeatherDatabase:RoomDatabase() {
    abstract val locationDao:LocationDao
    abstract val favoriteDao:FavoriteDao
}