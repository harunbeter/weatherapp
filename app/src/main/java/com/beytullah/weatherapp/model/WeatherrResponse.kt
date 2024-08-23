package com.beytullah.weatherapp.model

import android.text.format.Time

data class WeatherrResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: Daily
)

data class Daily(
    val time: List<String>,
    val temperature_2m_min: List<Double>,
    val temperature_2m_max: List<Double>

)