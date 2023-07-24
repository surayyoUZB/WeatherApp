package com.yoo.weatherapp.ui.bottom.favourite

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherRepository
):ViewModel() {
    val favoriteList = mutableStateListOf<FavoriteName>()

    init {
        viewModelScope.launch {
            repository.getAllFavourites().collectLatest {
                favoriteList.clear()
                favoriteList.addAll(it)
            }
        }
    }

}