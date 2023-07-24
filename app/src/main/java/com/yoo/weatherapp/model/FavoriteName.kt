package com.yoo.weatherapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
data class FavoriteName(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)
