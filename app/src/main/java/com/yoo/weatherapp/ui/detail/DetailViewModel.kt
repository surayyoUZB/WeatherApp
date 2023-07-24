package com.yoo.weatherapp.ui.detail

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoo.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailState())
    val state get() = _state.asStateFlow()

    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnLoadWeather -> {
                viewModelScope.launch {
                    repository.getCurrentWeather(event.query)
                        .onStart {
                            _state.update {
                                it.copy(isLoading = true)
                            }
                        }.catch { t ->
                            _state.update {
                                it.copy(isLoading = false, error = t.message.toString())
                            }
                        }.collectLatest { r ->
                            _state.update {
                                it.copy(isLoading = false, success = r)
                            }
                        }
                }
            }
            is DetailEvent.OnDeleteWeather -> {
                viewModelScope.launch {
                    repository.deleteLocation(event.locationName)
                }
            }
        }
    }
}