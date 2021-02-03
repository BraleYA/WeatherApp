package com.ya.weatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ya.weatherapp.data.entities.WeatherModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getAll(): LiveData<MutableList<WeatherModel>>

    @Query("SELECT * FROM weather WHERE id LIKE :id")
    fun findByTitle(id: Int): WeatherModel

    @Insert
    fun insertAll(vararg weather: WeatherModel)

    @Delete
    fun delete(weather: WeatherModel)

    @Update
    fun updateTodo(weather: WeatherModel)

}