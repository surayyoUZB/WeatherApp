package com.yoo.weatherapp.databace

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoo.weatherapp.model.FavoriteName
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFavorite(favoriteEntity: FavoriteName)

    @Query("delete from FavoriteName where name = :name")
    suspend fun deleteByName(name: String)

    @Query("select * from FavoriteName order by id desc")
    fun getAllFavorites(): Flow<List<FavoriteName>>
}