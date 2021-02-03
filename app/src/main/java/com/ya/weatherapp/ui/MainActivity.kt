package com.ya.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ya.weatherapp.databinding.ActivityMainBinding
import com.ya.weatherapp.work.UpdateDataWork
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var mPeriodicWorkRequest: PeriodicWorkRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPeriodicWorkRequest = PeriodicWorkRequest.Builder(
            UpdateDataWork::class.java,
            15, TimeUnit.MINUTES
        )
            .addTag("periodicWorkRequest")
            .build()

        WorkManager.getInstance().enqueue(mPeriodicWorkRequest!!)

    }

}