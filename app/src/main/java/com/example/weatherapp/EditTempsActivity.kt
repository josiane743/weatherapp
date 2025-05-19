package com.example.weatherapp // Replace with your package name

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditTempsActivity : AppCompatActivity() {

    private lateinit var linearLayoutInputs: LinearLayout
    private val editTextList = mutableListOf<EditText>()
    private var days: Array<String>? = null

    @SuppressLint("MissingInflatedId", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_temps)

        linearLayoutInputs = findViewById(R.id.linearLayoutInputs)
        val buttonSave: Button = findViewById(R.id.buttonSaveTemperatures)

        val currentTemperatures = intent.getIntegerArrayListExtra("currentTemperatures")
        days = intent.getStringArrayExtra("days")

        if (days != null && currentTemperatures != null && days!!.size == currentTemperatures.size) {
            for (i in days!!.indices) {
                val day = days!![i]
                val temp = currentTemperatures[i]

                // Create TextView for the day label
                val dayLabel = TextView(this).apply {
                    text = getString(R.string.enter_temp_for_day, day)
                    textSize = 16f
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { topMargin = dpToPx(8) }
                }
                linearLayoutInputs.addView(dayLabel)

                // Create EditText for temperature input
                val editText = EditText(this).apply {
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
                    setText(temp.toString())
                    hint = "Temp for $day"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }
                linearLayoutInputs.addView(editText)
                editTextList.add(editText)
            }
        } else {
            Toast.makeText(this, "Error: Could not load temperature data.", Toast.LENGTH_LONG).show()
            finish() // Go back if data is inconsistent
            return
        }

        buttonSave.setOnClickListener {
            saveTemperatures()
        }
    }

    private fun saveTemperatures() {
        val updatedTemperatures = ArrayList<Int>()
        var isValid = true
        for (editText in editTextList) {
            try {
                val tempStr = editText.text.toString()
                if (tempStr.isBlank()) {
                    editText.error = "Cannot be empty"
                    isValid = false
                    break
                }
                updatedTemperatures.add(tempStr.toInt())
            } catch (e: NumberFormatException) {
                editText.error = "Invalid number"
                isValid = false
                break // Stop if one is invalid
            }
        }

        if (isValid) {
            val resultIntent = Intent()
            resultIntent.putIntegerArrayListExtra("updatedTemperatures", updatedTemperatures)
            setResult(Activity.RESULT_OK, resultIntent)
            finish() // Close this activity and return to MainScreenActivity
        } else {
            Toast.makeText(this, "Please correct the errors.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}