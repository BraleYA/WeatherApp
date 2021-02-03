package com.ya.weatherapp.data.repository

import androidx.lifecycle.LiveData
import com.ya.weatherapp.data.entities.WeatherModel
import com.ya.weatherapp.data.local.WeatherDao

class WeatherRepository(private val weatherDao: WeatherDao) {

    val readAllData: LiveData<MutableList<WeatherModel>> = weatherDao.getAll()

    fun addWeather(weatherModel: WeatherModel) {
        weatherDao.insertAll(weatherModel)
    }

    fun updateWeather(weatherModel: WeatherModel) {
        weatherDao.updateTodo(weatherModel)
    }

    fun deleteWeather(weatherModel: WeatherModel) {
        weatherDao.delete(weatherModel)
    }

}