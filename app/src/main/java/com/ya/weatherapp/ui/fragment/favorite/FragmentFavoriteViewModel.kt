package com.ya.weatherapp.ui.fragment.favorite

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
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

class FragmentFavoriteViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<MutableList<WeatherModel>>
    private val repository: WeatherRepository

    val mutableList = MutableLiveData<Weather?>()
    private var services: RetrofitServices = RetrofitClient.retrofitService
    private lateinit var text: String

    init {
        val weatherDao = AppDatabase.getInstance(
            application
        ).weatherDao
        repository = WeatherRepository(weatherDao)
        readAllData = repository.readAllData
    }

    fun addWeather(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWeather(weatherModel)
        }
    }

    fun delWeather(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWeather(weatherModel)
        }
    }

    fun updateWeather(weatherModel: WeatherModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeather(weatherModel)
        }
    }

    fun setCity(text: String) {
        this.text = text
        if (text != "")
            getAllWeatherList(text)
    }

    private fun getAllWeatherList(city: String?) {
        if (city != null) {
            services.getWeather(city, "3bbc3f1878c1ff1f4492f34d91a79245")
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
        } else {
            Toast.makeText(getApplication(), "City name is empty", Toast.LENGTH_SHORT).show()
        }
    }

}