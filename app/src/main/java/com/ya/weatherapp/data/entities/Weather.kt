package com.ya.weatherapp.data.entities

import kotlin.math.roundToInt

data class Weather(
    val id: Int,
    val name: String?,
    val sys: Sys,
    val main: Main,
    val wind: Wind,
    val timezone: Int?,
    val coord: Coord,
) {
    fun toWeatherModel(): WeatherModel {
        return WeatherModel(
            this.id.toLong(),
            this.name,
            this.sys.country,
            this.main.temp,
            this.main.feels_like,
            this.wind.speed,
            this.timezone,
            this.coord.lat,
            this.coord.lon
        )
    }
}

data class Coord(
    val lat: Double?,
    val lon: Double?
)

data class Wind(
    val speed: Double?
)

data class Sys(
    val country: String?
)

data class Main(
    val temp: Double?,
    val feels_like: Double?
) {
    val tempValue: Double
        get() {
            return when {
                temp!! >= 100 -> {
                    twoDecimal(temp - 273.15)
                }
                temp <= -100 -> {
                    twoDecimal(temp + 273.15)
                }
                else -> {
                    temp
                }
            }
        }

    val feelValue: Double
        get() {
            return when {
                feels_like!! >= 100 -> {
                    twoDecimal(feels_like - 273.15)
                }
                feels_like <= -100 -> {
                    twoDecimal(feels_like + 273.15)
                }
                else -> {
                    feels_like
                }
            }
        }

    private fun twoDecimal(double: Double): Double {
        return (double * 100.0).roundToInt() / 100.0
    }

}