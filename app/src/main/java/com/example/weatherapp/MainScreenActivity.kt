package com.example.weatherapp // Replace with your actual package name

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainScreenActivity : AppCompatActivity() {

    // Sample Hardcoded Data (as per requirements)
    private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private val maxTemps = arrayOf(25, 29, 22, 24, 20, 18, 16) // Example temperatures

    // You can use the example from the image if preferred:
    // private val days = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    // private val maxTemps = arrayOf(25, 29, 22, 24, 20, 18, 16)
    // Or the other example:
    // private val days = arrayOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    // private val maxTemps = arrayOf(22, 24, 19, 21, 23, 20, 18)


    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val textViewTemperatures: TextView = findViewById(R.id.textViewTemperatures)
        val textViewAverageTemp: TextView = findViewById(R.id.textViewAverageTemp)
        val buttonBack: Button = findViewById(R.id.buttonBack)

        // 1. Use two parallel arrays (already defined as class members)

        // 2. Display each day with its max temperature using a loop
        val dailyTempsText = StringBuilder()
        for (i in days.indices) { // Loop through the arrays
            if (i < maxTemps.size) { // Ensure we don't go out of bounds for maxTemps
                dailyTempsText.append("${days[i]}: ${maxTemps[i]}°C\n")
            }
        }
        textViewTemperatures.text = dailyTempsText.toString()

        // 3. Calculate and show the average max temperature using a loop
        var sumOfTemps = 0
        for (temp in maxTemps) { // Loop to calculate sum
            sumOfTemps += temp
        }

        val averageTemp: Double = if (maxTemps.isNotEmpty()) {
            sumOfTemps.toDouble() / maxTemps.size
        } else {
            0.0
        }
        textViewAverageTemp.text = getString(R.string.average_max_temperature, averageTemp)


        // 4. Include a Back button to return to the welcome screen
        buttonBack.setOnClickListener {
            finish() // This will close the current activity and go back to the previous one (MainActivity)
        }
    }
}