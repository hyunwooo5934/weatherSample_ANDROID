package com.example.weatheralarm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheralarm.data.model.WeatherData
import com.example.weatheralarm.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository)  : ViewModel(){

    val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    val _errData = MutableLiveData<String>()
    val errData: LiveData<String> get() = _errData


    fun getData(dataType : String, pageNo : Int, numOfRows : Int,  base_date : Int, base_time : Int, nx : String, ny : String){
        viewModelScope.launch {
            val data = weatherRepository.getWeather(dataType,pageNo, numOfRows,base_date,base_time,nx,ny)
            if(data.response.header.resultCode == "00"){
                _weatherData.value = data
            }else{
                _errData.value = data.response.header.resultMsg
            }

        }
    }

}
