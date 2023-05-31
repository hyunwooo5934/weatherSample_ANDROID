package com.example.weatheralarm.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatheralarm.data.model.WeatherDetailItem
import com.example.weatheralarm.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository)  : ViewModel(){

    val _weatherData = MutableLiveData<String>()
    val weatherData: LiveData<String> get() = _weatherData


    fun getData(dataType : String, pageNo : Int, numOfRows : Int,  base_date : Int, base_time : Int, nx : String, ny : String){
        viewModelScope.launch {
            Log.d("weatherData",weatherRepository.getWeather(dataType,pageNo, numOfRows,base_date,base_time,nx,ny).toString())
            _weatherData.value = weatherRepository.getWeather(dataType,pageNo, numOfRows,base_date,base_time,nx,ny).toString()
        }
    }

}
