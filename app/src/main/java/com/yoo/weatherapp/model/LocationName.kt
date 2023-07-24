package com.yoo.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationName(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isSaved: Boolean,
    val savedDate:String
)
