package com.example.weatheralarm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timeTable")
data class userData(
    @PrimaryKey val user : String,
    val time : String,
    val userYn : String
)
