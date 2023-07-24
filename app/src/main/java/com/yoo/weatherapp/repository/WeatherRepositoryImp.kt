package com.yoo.weatherapp.repository


import com.yoo.weatherapp.databace.FavoriteDao
import com.yoo.weatherapp.databace.LocationDao
import com.yoo.weatherapp.manager.DataStoreManager
import com.yoo.weatherapp.model.CurrentWeather
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.model.LocationName
import com.yoo.weatherapp.network.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImp @Inject constructor(
    private val locationDao: LocationDao,
    private val favoriteDao: FavoriteDao,
    private val api:WeatherService,
    private val dataStoreManager: DataStoreManager
) : WeatherRepository {
    override suspend fun saveLocation(locationName: LocationName) {
        locationDao.saveLocation(locationName)
    }

    override suspend fun deleteLocation(locationName: LocationName) {
        locationDao.deleteLocation(locationName)
    }

    override fun getAllLocations(): Flow<List<LocationName>> {
        return locationDao.getAllLocations()
    }

    override suspend fun updateLocation(id: Int, isSaved: Boolean) {
        locationDao.updateFavLocation(id, isSaved)
    }

    override suspend fun getCurrentWeather(query: String): Flow<CurrentWeather> = flow {
        val response=api.getCurrentWeather(query)
        response.body()?.let {
            emit(it)
        }
    }

    override suspend fun saveFavourite(favoriteName: FavoriteName) {
        favoriteDao.saveFavorite(favoriteName)
    }

    override suspend fun deleteFavourite(name:String) {
        favoriteDao.deleteByName(name)
    }

    override fun getAllFavourites(): Flow<List<FavoriteName>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun saveTheme(index: Int) {
        dataStoreManager.saveTheme(index)
    }

    override fun getTheme(): Flow<Int> {
        return dataStoreManager.getTheme()
    }
}