package com.ya.weatherapp.ui.fragment.future

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ya.weatherapp.data.entities.WeatherFuture
import com.ya.weatherapp.databinding.FragmentFutureBinding


class FragmentFuture : Fragment() {
    private lateinit var binding: FragmentFutureBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: FutureAdapter
    private val mutableList: MutableList<WeatherFuture?> = ArrayList()
    private lateinit var viewModel: FragmentFutureViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FutureAdapter()
        viewModel =
            ViewModelProvider(this).get(
                FragmentFutureViewModel::class.java
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFutureBinding.inflate(layoutInflater, container, false)
        val latValue = arguments?.getDouble("lat")
        val longValue = arguments?.getDouble("lon")
        if (latValue != null && longValue != null) {
            viewModel.start(latValue, longValue)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerViewFuture.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFuture.layoutManager = layoutManager
        binding.recyclerViewFuture.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.mutableList.observe(viewLifecycleOwner, {
            if (it != null) {
                mutableList.clear()
                mutableList.add(it)
                dataFilling(mutableList)
                adapter.setData(it.daily)
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun dataFilling(mutableList: MutableList<WeatherFuture?>) {
        binding.apply {
            progressBar.visibility = View.GONE
            nameCity.text = mutableList[0]?.timezone
            tempNow.text = "Now\n" + mutableList[0]?.current?.tempValue + " ℃"
            tempFeel.text = "Feel\n" + mutableList[0]?.current?.feelValue + " ℃"
            speed.text = "Speed\n" + mutableList[0]?.current?.wind_speed + " km/h"
            constraintLayoutAll.visibility = View.VISIBLE
        }
    }
}