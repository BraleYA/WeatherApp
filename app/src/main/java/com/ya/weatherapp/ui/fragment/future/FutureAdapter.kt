package com.ya.weatherapp.ui.fragment.future

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ya.weatherapp.data.entities.Daily
import com.ya.weatherapp.databinding.CardFutureBinding

class FutureAdapter : RecyclerView.Adapter<FutureAdapter.FutureViewHolder>() {

    private lateinit var weatherList: List<Daily>

    fun setData(weatherList: List<Daily>) {
        this.weatherList = weatherList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureViewHolder {
        val binding: CardFutureBinding =
            CardFutureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FutureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FutureViewHolder, position: Int) {
        weatherList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    class FutureViewHolder(private val binding: CardFutureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Daily) {
            binding.apply {
                time.text = item.date(item.dt)
                description.text = item.weather[0].description
                sunriseText.text = "Sunrise\n" + item.dateHour(item.sunrise)
                sunsetText.text = "Sunset\n" + item.dateHour(item.sunset)
                humidityText.text = "Humidity\n" + item.humidity.toString()
                cloudsText.text = "Clouds\n" + item.clouds.toString()

                tempMorn.text = "Morning\n" + item.temp.celsius(item.temp.morn)
                tempDay.text = "Day\n" + item.temp.celsius(item.temp.day)
                tempEve.text = "Evening\n" + item.temp.celsius(item.temp.eve)
                tempNight.text = "Night\n" + item.temp.celsius(item.temp.night)
            }

        }
    }
}