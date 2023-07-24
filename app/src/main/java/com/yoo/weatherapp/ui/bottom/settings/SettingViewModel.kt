package com.yoo.weatherapp.ui.bottom.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Insert
import com.yoo.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: WeatherRepository
):ViewModel(){

    var theme= mutableStateOf(0)

    init {
        viewModelScope.launch {
            repository.getTheme().collectLatest {
                theme.value=it
            }
        }
    }

    fun saveTheme(index:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveTheme(index)
        }
    }


}