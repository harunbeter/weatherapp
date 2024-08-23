package com.beytullah.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DailyAdapter(private val minTemps: List<Double>, private val maxTemps: List<Double>,private val times: List<String>) : RecyclerView.Adapter<DailyAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_list_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val minTemp = minTemps[position]
        val maxTemp = maxTemps[position]
        val day = times[position]
        holder.minTempTextView.text = "$minTemp °C"
        holder.maxTempTextView.text = "$maxTemp °C"
        holder.dayTextView.text = "$day"
    }

    override fun getItemCount(): Int {
        return minTemps.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val minTempTextView: TextView = itemView.findViewById(R.id.txtMinTemp)
        val maxTempTextView: TextView = itemView.findViewById(R.id.txtMaxTemp)
        val dayTextView: TextView = itemView.findViewById(R.id.txtDay)
    }
}


