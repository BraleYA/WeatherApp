package com.ya.weatherapp.data.api

import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.data.entities.WeatherFuture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitServices {

    @GET("data/2.5/weather")
    fun getWeather(
        @Query("q") query: String,
        @Query("appId") appId: String
    ): Call<Weather>

    @POST("data/2.5/weather")
    fun getWeatherCoordinate(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appId") appId: String
    ): Call<Weather>

    @POST("data/2.5/onecall")
    fun getWeatherDay(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appId") appId: String
    ): Call<WeatherFuture>
}
