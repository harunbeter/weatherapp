package com.beytullah.weatherapp.api

import com.beytullah.weatherapp.model.DailyWeatherResponse
import com.beytullah.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String
    ): Response<WeatherResponse>

    @GET("forecast")
    suspend fun getDailyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: List<String>

    ):Response<DailyWeatherResponse>


}