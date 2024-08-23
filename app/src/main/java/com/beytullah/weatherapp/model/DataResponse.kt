package com.beytullah.weatherapp.model

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val hourly: HourlyData
)

data class DailyWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val daily: DailyData
)

data class HourlyData(
    val time: List<String>,
    val temperature_2m: List<Double>
)
data class DailyData(
    val time: List<String>,
    val temperature_2m_min: List<Double>,
    val temperature_2m_max: List<Double>,

    )
