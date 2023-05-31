package com.example.weatheralarm.repository

import com.example.weatheralarm.data.model.WeatherData
import com.example.weatheralarm.data.model.WeatherDetailItem
import com.example.weatheralarm.data.remote.WeatherService
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val weatherService: WeatherService) {

    suspend fun getWeather(dataType : String, pageNo : Int, numOfRows : Int,  base_date : Int, base_time : Int, nx : String, ny : String)
    : List<WeatherDetailItem> {
        val data = weatherService.getWeatherInfo(dataType,pageNo, numOfRows,base_date,base_time,nx,ny).body()
        return data?.response?.body?.items?.item ?: listOf()
    }

}