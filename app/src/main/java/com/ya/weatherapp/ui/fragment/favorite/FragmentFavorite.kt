package com.ya.weatherapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ya.weatherapp.R
import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.data.entities.WeatherModel
import com.ya.weatherapp.databinding.FragmentFavoriteBinding

class FragmentFavorite : Fragment(), MyWeatherAdapter.OnItemClickListener {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: MyWeatherAdapter
    private val mutableList: MutableList<Weather?> = ArrayList()
    lateinit var viewModel: FragmentFavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MyWeatherAdapter(this)
        viewModel =
            ViewModelProvider(this).get(
                FragmentFavoriteViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewFavorite.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFavorite.layoutManager = layoutManager
        binding.recyclerViewFavorite.adapter = adapter

        viewModel.mutableList.observe(viewLifecycleOwner, {
            if (it != null) {
                binding.progressBar.isVisible = false
                mutableList.clear()
                mutableList.add(it)
                adapter.setData(mutableList, false)
            }
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.isVisible = true
                if (query != null) {
                    viewModel.setCity(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        binding.favoriteImage.setOnClickListener {
            findNavController().navigate(
                R.id.action_fragmentFavorite_to_fragmentLocation
            )
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.readAllData.observe(viewLifecycleOwner, {
            if (it != null) {
                lateinit var weather: Weather
                mutableList.clear()
                for (x in it.indices) {
                    weather = it[x].toModelWeahter()
                    mutableList.add(weather)
                }
                adapter.setData(mutableList, true)
            }
        })
    }

    override fun onAddClick(weatherList: MutableList<Weather?>?) {
        if (weatherList != null) {
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
            binding.searchView.isActivated = false
            binding.searchView.isFocusable = false
            viewModel.addWeather(weatherModel)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onDelClick(position: Int) {
        val weather: Weather? = mutableList[position]
        mutableList.clear()
        weather?.toWeatherModel()?.let { viewModel.delWeather(it) }
        Toast.makeText(requireContext(), "Successfully delete!", Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(lat: Double?, lon: Double?) {
        val bundle = Bundle()
        if (lat != null && lon != null) {
            bundle.putDouble("lat", lat)
            bundle.putDouble("lon", lon)
            findNavController().navigate(
                R.id.action_fragmentFavorite_to_fragmentFuture,
                bundle
            )
        }
    }

}
