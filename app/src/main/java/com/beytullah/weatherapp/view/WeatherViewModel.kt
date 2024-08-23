package com.beytullah.weatherapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beytullah.weatherapp.api.WeatherApi
import com.beytullah.weatherapp.model.DailyWeatherResponse
import com.beytullah.weatherapp.model.WeatherResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherApi: WeatherApi) : ViewModel() {

    private val _weatherData = MutableLiveData<Response<WeatherResponse>>()
    val weatherData: LiveData<Response<WeatherResponse>> get() = _weatherData

    private val _dailyWeatherData = MutableLiveData<Response<DailyWeatherResponse>>()
    val dailyWeatherData: LiveData<Response<DailyWeatherResponse>>get() = _dailyWeatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _exceptionMessage : MutableLiveData<String> = MutableLiveData()
    val exceptionMessage : LiveData<String> get() = _exceptionMessage

    fun fetchHourlyWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                val response : Response<WeatherResponse> = weatherApi.getWeather(latitude, longitude, "temperature_2m")
                if (!response.isSuccessful) {
                    _exceptionMessage.postValue(response.message())
                    return@launch
                }
                _weatherData.postValue(response)
            } catch (e: Exception) {
                _exceptionMessage.postValue("error: "+ e.message)
            }
            finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchDailyWeather(latitude: Double, longitude: Double) {
        val list =  listOf("temperature_2m_min","temperature_2m_max")
        viewModelScope.launch {
            val data = weatherApi.getDailyWeather(latitude, longitude, list)
            if (data != null) {
                _dailyWeatherData.postValue(data)
            }
        }
    }


}
