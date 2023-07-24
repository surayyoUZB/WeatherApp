package com.yoo.weatherapp.di

import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.yoo.weatherapp.databace.FavoriteDao
import com.yoo.weatherapp.databace.LocationDao
import com.yoo.weatherapp.databace.WeatherDatabase
import com.yoo.weatherapp.manager.DataStoreManager
import com.yoo.weatherapp.network.WeatherService
import com.yoo.weatherapp.repository.WeatherRepository
import com.yoo.weatherapp.repository.WeatherRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGeoCoder(
        @ApplicationContext context: Context
    ): Geocoder {
        return Geocoder(context)
    }

    @[Provides Singleton]
    fun provideWeatherWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WeatherDatabase::class.java,
            "weather.db"
        ).fallbackToDestructiveMigration().build()
    }

    @[Provides Singleton]
    fun provideLocationDao(database: WeatherDatabase): LocationDao {
        return database.locationDao
    }
    @[Provides Singleton]
    fun provideFavoriteDao(database: WeatherDatabase): FavoriteDao {
        return database.favoriteDao
    }

    @Provides
    @Singleton
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        locationDao: LocationDao,
        favoriteDao: FavoriteDao,
        weatherService: WeatherService,
        dataStoreManager: DataStoreManager
    ): WeatherRepository {
        return WeatherRepositoryImp(locationDao, favoriteDao, weatherService, dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ):DataStoreManager{
        return DataStoreManager(context)
    }


}