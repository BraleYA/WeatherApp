package com.ya.weatherapp.work

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.data.entities.WeatherModel
import com.ya.weatherapp.data.local.AppDatabase
import com.ya.weatherapp.data.repository.WeatherRepository
import com.ya.weatherapp.ui.fragment.favorite.FragmentFavoriteViewModel

class UpdateDataWork(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    lateinit var viewModel: FragmentFavoriteViewModel
    private lateinit var readAllData: LiveData<MutableList<WeatherModel>>
    private lateinit var repository: WeatherRepository

    override fun doWork(): Result {
        Thread {
            val weatherDao = AppDatabase.getInstance(
                applicationContext
            ).weatherDao
            repository = WeatherRepository(weatherDao)
            readAllData = repository.readAllData

            if (repository.readAllData.value != null) {
                for (x in readAllData.value?.indices!!) {
                    lateinit var weatherList: MutableList<Weather?>
                    val weatherModel = WeatherModel(
                        0,
                        weatherList[0]?.name,
                        weatherList[0]?.sys?.country,
                        weatherList[0]?.main?.tempValue,
                        weatherList[0]?.main?.feelValue,
                        weatherList[0]?.wind?.speed,
                        weatherList[0]?.timezone,
                        weatherList[0]?.coord?.lat,
                        weatherList[0]?.coord?.lon
                    )
                    viewModel.updateWeather(weatherModel)
                }
            }
        }.start()
        return Result.success()
    }

}