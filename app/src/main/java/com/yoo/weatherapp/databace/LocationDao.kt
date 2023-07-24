package com.yoo.weatherapp.databace

import android.location.Location
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoo.weatherapp.model.LocationName
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveLocation(location:LocationName)

    @Delete
    suspend fun deleteLocation(location: LocationName)

    @Query("UPDATE LocationName SET isSaved = :isSaved WHERE id= :id ")
    suspend fun updateFavLocation(id:Int, isSaved:Boolean)

    @Query("select * from LocationName order by id desc")
    fun getAllLocations(): Flow<List<LocationName>>

}