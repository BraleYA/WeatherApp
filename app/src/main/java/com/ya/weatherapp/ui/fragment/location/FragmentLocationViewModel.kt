package com.ya.weatherapp.ui.fragment.location

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ya.weatherapp.data.api.RetrofitClient
import com.ya.weatherapp.data.api.RetrofitServices
import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.data.entities.WeatherModel
import com.ya.weatherapp.data.local.AppDatabase
import com.ya.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentLocationViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: WeatherRepository
    val mutableList = MutableLiveData<Weather?>()
    private var services: RetrofitServices = RetrofitClient.retrofitService

    init {
        val weatherDao = AppDatabase.getInstance(
            application
        ).weatherDao
        repository = WeatherRepository(weatherDao)
    }

    fun addWeather(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWeather(weatherModel)
        }
    }

    fun getCityName(lat: Double, long: Double) {
        services.getWeatherCoordinate(lat, long, "3bbc3f1878c1ff1f4492f34d91a79245")
            .enqueue(object : Callback<Weather> {
                override fun onFailure(call: Call<Weather>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<Weather>,
                    response: Response<Weather>
                ) {
                    if (response.code() == 200) {
                        mutableList.value = response.body()
                    } else if (response.code() == 404) {
                        Toast.makeText(getApplication(), "City not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

}