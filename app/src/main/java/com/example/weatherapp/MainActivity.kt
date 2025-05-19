package com.example.weatherapp // Replace with your actual package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // You can set your name and student number programmatically if needed
        // or directly in the XML via strings.xml as done here.
        // val textViewYourName: TextView = findViewById(R.id.textViewYourName)
        // val textViewStudentNumber: TextView = findViewById(R.id.textViewStudentNumber)
        // textViewYourName.text = "Your Actual Name"
        // textViewStudentNumber.text = "Your Actual Student Number"

        val buttonProceed: Button = findViewById(R.id.buttonProceed)
        buttonProceed.setOnClickListener {
            val intent = Intent(this, MainScreenActivity::class.java)
            startActivity(intent)
        }
    }
}