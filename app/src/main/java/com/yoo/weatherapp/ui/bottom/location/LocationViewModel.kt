package com.yoo.weatherapp.ui.bottom.location


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoo.weatherapp.model.FavoriteName
import com.yoo.weatherapp.model.LocationName
import com.yoo.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: WeatherRepository
):ViewModel() {
    var locationList= mutableStateListOf<LocationName>()
    var cachedList= mutableStateListOf<LocationName>()

    init {
        viewModelScope.launch {
            repository.getAllLocations().collectLatest {
                cachedList.clear()
                cachedList.addAll(it)
                locationList.clear()
                locationList.addAll(it)
            }
        }
    }

    fun onSearch(query:String){
        if (query.isNotBlank()){
            val filteredList=cachedList.filter { it.name.lowercase().trim().contains(query.lowercase()) }
            locationList.clear()
            locationList.addAll(filteredList)
        }else{
            locationList.clear()
            locationList.addAll(cachedList)
        }


    }

    fun changeFavourite(locationName: LocationName){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateLocation(locationName.id, !locationName.isSaved)
            if (!locationName.isSaved){
                repository.saveFavourite(FavoriteName(locationName.id, locationName.name))
            }else{
                repository.deleteFavourite(locationName.name)
            }
        }
    }


}