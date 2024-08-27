package com.beytullah.weatherapp.repository

import com.beytullah.weatherapp.api.WeatherApi
import com.beytullah.weatherapp.model.DailyWeatherResponse
import com.beytullah.weatherapp.model.WeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getWeather(latitude: Double, longitude: Double, hourly: String): Response<WeatherResponse> {
        return weatherApi.getWeather(latitude, longitude, hourly)
    }

    suspend fun getDailyWeather(latitude: Double, longitude: Double, daily: List<String>): Response<DailyWeatherResponse> {
        return weatherApi.getDailyWeather(latitude, longitude, daily)
    }
}