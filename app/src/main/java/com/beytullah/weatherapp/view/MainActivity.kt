package com.beytullah.weatherapp.view

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beytullah.weatherapp.DailyAdapter
import com.beytullah.weatherapp.R
import com.beytullah.weatherapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var adapter: WeatherAdapter
    private lateinit var dailyAdapter: DailyAdapter

    private var timeList : ArrayList<String> = ArrayList()
    private var temperatureList : ArrayList<Double> = ArrayList()

    private var dailyTimeList : ArrayList<String> = ArrayList()
    private var temperatureListMin : ArrayList<Double> = ArrayList()
    private var temperatureListMax : ArrayList<Double> = ArrayList()

    private lateinit var progressBar : ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        progressBar = binding.pBar
        val recyclerView = findViewById<RecyclerView>(R.id.weatherList)
        val dailyRecyclerView = findViewById<RecyclerView>(R.id.dailyWeatherList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = WeatherAdapter(temperatureList, timeList)
        dailyAdapter = DailyAdapter(temperatureListMin, temperatureListMax, dailyTimeList)

        recyclerView.adapter = adapter
        dailyRecyclerView.adapter = dailyAdapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        dailyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )

        viewModel.weatherData.observe(this@MainActivity, Observer { weatherDataResult ->
            val lastindex = weatherDataResult.body()?.hourly?.time?.size?.minus(1)
            for (i in 0..lastindex!!){
                timeList.add(weatherDataResult.body()!!.hourly.time[i])
                temperatureList.add(weatherDataResult.body()!!.hourly.temperature_2m[i])
            }
            adapter.notifyDataSetChanged()

        })

        viewModel.dailyWeatherData.observe(this, Observer { weatherDataResult ->
            val lastindex = weatherDataResult.body()?.daily?.time?.size?.minus(1)
            for (i in 0..lastindex!!){
                dailyTimeList.add(weatherDataResult.body()!!.daily.time[i])
                temperatureListMin.add(weatherDataResult.body()!!.daily.temperature_2m_min[i])
                temperatureListMax.add(weatherDataResult.body()!!.daily.temperature_2m_max[i])
            }
            println(weatherDataResult.body())
            dailyAdapter.notifyDataSetChanged()

        })

        viewModel.exceptionMessage.observe(this@MainActivity, Observer {
            Toast.makeText(this, "Hata", Toast.LENGTH_SHORT).show()
        })

        viewModel.isLoading.observe(this@MainActivity, Observer {value ->
            if (value == true) {
                binding.weatherList.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }
            else{
                progressBar.visibility = View.GONE
                binding.weatherList.visibility = View.VISIBLE
            }



        })

        binding.fetchWeatherButton.setOnClickListener {
            dailyTimeList.clear()
            temperatureListMin.clear()
            temperatureListMax.clear()
            dailyAdapter.notifyDataSetChanged()
            viewModel.fetchDailyWeather(39.0, 35.0)

        }
    }


    class WeatherAdapter(private val temps: List<Double>, private val times: List<String>) : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return WeatherViewHolder(view)
        }

        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            val temp = temps[position]
            val time = times[position]
            holder.tempTextView.text = "$temp °C"
            holder.timeTextView.text = time
        }

        override fun getItemCount(): Int {
            return temps.size
        }

        class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tempTextView: TextView = itemView.findViewById(R.id.temperature)
            val timeTextView: TextView = itemView.findViewById(R.id.hour)
        }
    }


}