package com.ya.weatherapp.data.entities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

data class WeatherFuture(
    val current: Current,
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)

data class Current(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val wind_deg: Int,
    val wind_speed: Double
) {
    val tempValue: Double
        get() {
            return when {
                temp >= 100 -> {
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
                feels_like >= 100 -> {
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

data class Daily(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val sunrise: Long,
    val weather: List<WeatherX>,
    val sunset: Long,
    val temp: Temp,
    val uvi: Double,
    val wind_deg: Int,
    val wind_speed: Double
) {

    @SuppressLint("SimpleDateFormat")
    fun date(date: Long): String {
        val dateString = Date(date * 1000L)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        sdf.timeZone = TimeZone.getTimeZone("GMT-4")
        return sdf.format(dateString)
    }

    @SuppressLint("SimpleDateFormat")
    fun dateHour(date: Long): String {
        val dateString = Date(date * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT-4")
        return sdf.format(dateString)
    }
}

data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
) {
    fun celsius(temp: Double): String {
        return when {
            temp >= 100 -> {
                val celsius:String = twoDecimal(temp - 273.15).toString()
                "$celsius ℃"
            }
            temp <= -100 -> {
                val celsius:String = twoDecimal(temp + 273.15).toString()
                "$celsius ℃"
            }
            else -> {
                temp
                "$temp ℃"
            }
        }
    }

    private fun twoDecimal(double: Double): Double {
        return (double * 100.0).roundToInt() / 100.0
    }
}