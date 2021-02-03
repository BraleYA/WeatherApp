package com.ya.weatherapp.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "country")
    var country: String?,

    @ColumnInfo(name = "temp")
    var temp: Double?,

    @ColumnInfo(name = "feels_like")
    var feels_like: Double?,

    @ColumnInfo(name = "speed")
    var speed: Double?,

    @ColumnInfo(name = "timezone")
    var timezone: Int?,

    @ColumnInfo(name = "lat")
    var lat: Double?,

    @ColumnInfo(name = "lon")
    var lon: Double?
) {
    fun toModelWeahter(): Weather {
        return Weather(
            this.id.toInt(),
            this.name,
            Sys(this.country),
            Main(this.temp, this.feels_like),
            Wind(this.speed),
            this.timezone,
            Coord(this.lat, this.lon)
        )
    }
}
