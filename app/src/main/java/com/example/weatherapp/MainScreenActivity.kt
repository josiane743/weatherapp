package com.example.weatherapp // Replace with your package name

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainScreenActivity : AppCompatActivity() {

    private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    // Make maxTemps mutable and use ArrayList for easier updates
    private var maxTemps = arrayListOf(25, 29, 22, 24, 20, 18, 16)
    // Third array for weather conditions
    private var weatherConditions = arrayListOf(
        "Sunny", "Rainy", "Cloudy", "Partly Cloudy", "Sunny", "Windy", "Cloudy"
    ) // Example conditions

    private lateinit var textViewTemperatures: TextView
    private lateinit var textViewAverageTemp: TextView

    // ActivityResultLauncher for getting results from EditTempsActivity
    private val editTempsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedTemps = result.data?.getIntegerArrayListExtra("updatedTemperatures")
            if (updatedTemps != null && updatedTemps.size == maxTemps.size) {
                maxTemps = updatedTemps
                updateWeatherDisplay() // Refresh the display
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        textViewTemperatures = findViewById(R.id.textViewTemperatures)
        textViewAverageTemp = findViewById(R.id.textViewAverageTemp)
        val buttonBack: Button = findViewById(R.id.buttonBack)
        val buttonEditTemperatures: Button = findViewById(R.id.buttonEditTemperatures)

        updateWeatherDisplay()

        buttonBack.setOnClickListener {
            finish()
        }

        buttonEditTemperatures.setOnClickListener {
            val intent = Intent(this, EditTempsActivity::class.java)
            intent.putExtra("currentTemperatures", maxTemps) // Pass current temps
            intent.putExtra("days", days) // Pass days for context
            editTempsLauncher.launch(intent)
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun updateWeatherDisplay() {
        // Display each day with its max temperature and condition using a loop
        val dailyInfoText = StringBuilder()
        for (i in days.indices) {
            val temp = if (i < maxTemps.size) maxTemps[i] else "N/A"
            val condition = if (i < weatherConditions.size) weatherConditions[i] else "N/A"
            dailyInfoText.append("${days[i]}: $temp°C - $condition\n")
        }
        textViewTemperatures.text = dailyInfoText.toString().trimEnd()

        // Calculate and show the average max temperature using a loop
        var sumOfTemps = 0
        for (temp in maxTemps) {
            sumOfTemps += temp
        }

        val averageTemp: Double = if (maxTemps.isNotEmpty()) {
            sumOfTemps.toDouble() / maxTemps.size
        } else {
            0.0
        }
        textViewAverageTemp.text = getString(R.string.average_max_temperature, averageTemp)
    }
}