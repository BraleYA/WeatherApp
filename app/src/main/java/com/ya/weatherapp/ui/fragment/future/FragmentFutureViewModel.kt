package com.ya.weatherapp.ui.fragment.future

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ya.weatherapp.data.api.RetrofitClient
import com.ya.weatherapp.data.api.RetrofitServices
import com.ya.weatherapp.data.entities.WeatherFuture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentFutureViewModel(application: Application) : AndroidViewModel(application) {
    val mutableList = MutableLiveData<WeatherFuture?>()
    private var services: RetrofitServices = RetrofitClient.retrofitService

    fun start(lat: Double, lon: Double) {
        services.getWeatherDay(lat, lon, "hourly", "3bbc3f1878c1ff1f4492f34d91a79245")
            .enqueue(object : Callback<WeatherFuture> {
                override fun onResponse(
                    call: Call<WeatherFuture>,
                    response: Response<WeatherFuture>
                ) {
                    if (response.code() == 200) {
                        mutableList.value = response.body()
                    } else if (response.code() == 404) {
                        Toast.makeText(getApplication(), "Data not found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<WeatherFuture>, t: Throwable) {}

            })
    }

}