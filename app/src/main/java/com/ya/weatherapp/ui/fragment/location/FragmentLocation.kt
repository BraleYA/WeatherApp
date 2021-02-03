package com.ya.weatherapp.ui.fragment.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ya.weatherapp.data.entities.Weather
import com.ya.weatherapp.data.entities.WeatherModel
import com.ya.weatherapp.databinding.FragmentLocationBinding


class FragmentLocation : Fragment(), LocationListener {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    private lateinit var viewModel: FragmentLocationViewModel
    private val mutableList: MutableList<Weather?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this).get(
                FragmentLocationViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            switchCompat.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    getLocation()
                } else {
                    binding.apply {
                        constraintCityWeather.visibility = View.GONE
                        progressBar.visibility = View.GONE
                    }
                }
            }
            addButton.setOnClickListener {
                onAddClick()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.mutableList.observe(this, {
            if (it != null) {
                mutableList.clear()
                mutableList.add(it)
                dataFilling(mutableList)
            } else {
                binding.apply {
                    progressBar.visibility = View.GONE
                    switchCompat.isChecked = false
                }
            }
        })
    }

    private fun getLocation() {
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(
                context as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
            binding.switchCompat.isChecked = true
            getLocation()
        } else {
            binding.progressBar.visibility = View.VISIBLE
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5f, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        viewModel.getCityName(location.latitude, location.longitude)
    }

    @SuppressLint("SetTextI18n")
    private fun dataFilling(mutableList: MutableList<Weather?>) {
        binding.apply {
            progressBar.visibility = View.GONE
            nameCity.text = mutableList[0]?.name
            nameCountry.text = mutableList[0]?.sys?.country
            tempNow.text = "Now\n" + mutableList[0]?.main?.tempValue.toString() + " ℃"
            tempFeel.text = "Feel\n" + mutableList[0]?.main?.feelValue.toString() + " ℃"
            speed.text = "Speed\n" + mutableList[0]?.wind?.speed.toString() + " km/h"
            timezone.text = "Timezona\n" + mutableList[0]?.timezone.toString()
            constraintCityWeather.visibility = View.VISIBLE
        }
    }

    private fun onAddClick() {
        val weatherModel = WeatherModel(
            0,
            mutableList[0]?.name,
            mutableList[0]?.sys?.country,
            mutableList[0]?.main?.tempValue,
            mutableList[0]?.main?.feelValue,
            mutableList[0]?.wind?.speed,
            mutableList[0]?.timezone,
            mutableList[0]?.coord?.lat,
            mutableList[0]?.coord?.lon
        )
        viewModel.addWeather(weatherModel)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}