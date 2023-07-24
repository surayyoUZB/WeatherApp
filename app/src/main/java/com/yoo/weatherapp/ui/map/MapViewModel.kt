package com.yoo.weatherapp.ui.map

import android.location.Geocoder
import android.media.metrics.Event
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.yoo.weatherapp.model.LocationName
import com.yoo.weatherapp.repository.WeatherRepository
import com.yoo.weatherapp.util.Graph
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val geoCoder:Geocoder,
    private val repository: WeatherRepository
): ViewModel() {
    private val _mapState= MutableStateFlow(MapState())
    val mapState get() = _mapState.asStateFlow()

    fun onEvent(event: MapEvent){
        when(event){
            is MapEvent.OnTextChanged->{
                viewModelScope.launch(Dispatchers.IO){
                    _mapState.update {
                        it.copy(
                            query = event.text
                        )
                    }
                }
            }
            is MapEvent.OnSearch->{
                viewModelScope.launch(Dispatchers.IO){
                    try {
                        val locationList=geoCoder.getFromLocationName(
                            mapState.value.query,
                            1
                        )
                        _mapState.update {
                            it.copy(
                                currentLocation =
                                LatLng(
                                    locationList?.get(0)?.latitude ?:0.0,
                                    locationList?.get(0)?.longitude ?:0.0,
                                )
                            )
                        }
                    }catch (e:Exception){
                        println(e.message)
                    }
                }
            }
            is MapEvent.OnMapMoved->{
                viewModelScope.launch(Dispatchers.IO){
                    _mapState.update {
                        it.copy(
                            isSomeUIsVisible = event.isMoving
                        )
                    }
                }
            }
            is MapEvent.OnMapClicked->{
                viewModelScope.launch(Dispatchers.IO){
                    _mapState.update {
                        it.copy(
                            currentLocation = event.latLng,
                            zoom = it.zoom+5f
                        )
                    }
                }
            }
            is MapEvent.OnSaveLocationName->{
                viewModelScope.launch(Dispatchers.IO){
                    repository.saveLocation(
                        LocationName(
                            event.name,
                            0,
                            false,
                            Graph.getCurrentTime()
                        )
                    )
                }
            }
            else -> {}
        }
    }

}