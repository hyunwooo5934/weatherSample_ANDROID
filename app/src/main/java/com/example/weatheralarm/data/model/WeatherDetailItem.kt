package com.example.weatheralarm.data.model

data class WeatherDetailItem(
    val baseData: Int,
    val baseTime: Int,
    val category: String,
    val fcstDate : Int,
    val fcstTime : Int,
    val fcstValue : String,
    val nx : Int,
    val ny : Int
)
