package com.example.weatheralarm.data.remote

import com.example.weatheralarm.data.model.WeatherData
import com.example.weatheralarm.util.ApiKey.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("getVilageFcst?serviceKey=$API_KEY")
    suspend fun getWeatherInfo(
        @Query("dataType") dataType : String,
        @Query("numOfRows") numOfRows : Int,
        @Query("pageNo") pageNo : Int,
        @Query("base_date") baseDate : Int,
        @Query("base_time") baseTime : Int,
        @Query("nx") nx : String,
        @Query("ny") ny : String
    ): Response<WeatherData>


}