package com.example.weatheralarm.util

import java.text.SimpleDateFormat

object util {

    const val BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"


    fun setDate() : List<Int>{
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyymmdd")
        val timeFormat = SimpleDateFormat("HHmm")

        return listOf(dateFormat.format(currentTime).toInt(), timeFormat.format(currentTime).toInt())
    }



}