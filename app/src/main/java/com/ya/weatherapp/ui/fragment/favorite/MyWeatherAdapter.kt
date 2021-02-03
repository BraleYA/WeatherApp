package com.ya.weatherapp.ui.fragment.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.databinding.CardFavoriteBinding

class MyWeatherAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MyWeatherAdapter.MyViewHolder>() {

    private var weatherList: MutableList<Weather?>? = null
    private var data: Boolean = false

    fun setData(weatherList: MutableList<Weather?>?, data: Boolean) {
        this.weatherList = weatherList
        this.data = data
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onAddClick(weatherList: MutableList<Weather?>?)
        fun onDelClick(position: Int)
        fun onItemClick(lat: Double?, lon: Double?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CardFavoriteBinding =
            CardFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        weatherList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = weatherList?.size ?: 0

    inner class MyViewHolder(private val binding: CardFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        @SuppressLint("SetTextI18n")
        fun bind(item: Weather) {
            binding.apply {
                nameCity.text = item.name
                nameCountry.text = item.sys.country
                tempNow.text = "Now\n" + item.main.tempValue + " ℃"
                tempFeel.text = "Feel\n" + item.main.feelValue + " ℃"
                speed.text = "Speed\n" + item.wind.speed + " km/h"
                timezone.text = "Timezona\n" + item.timezone
            }
            if (data) {
                binding.delButton.visibility = View.VISIBLE
                binding.addButton.visibility = View.GONE
            } else if (!data) {
                binding.addButton.visibility = View.VISIBLE
                binding.delButton.visibility = View.GONE
            }
            binding.addButton.setOnClickListener(this)
            binding.delButton.setOnClickListener(this)
            itemView.setOnClickListener {
                listener.onItemClick(item.coord.lat, item.coord.lon)
            }
        }

        override fun onClick(v: View?) {
            if (v == binding.addButton) {
                listener.onAddClick(weatherList)
            } else if (v == binding.delButton) {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDelClick(position)
                }
            }
        }

    }
}