package com.example.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var birthDayOutput: TextView
    private lateinit var ageInMinutesOutput: TextView
    private lateinit var ageInDaysOutput: TextView
    private val millisInMinute = 1000 * 60
    private val millisInDay = 1000 * 60 * 60 * 24
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectDateBtn: Button = findViewById(R.id.select_date)
        birthDayOutput = findViewById(R.id.birthday_output)
        ageInMinutesOutput = findViewById(R.id.age_in_minutes_output)
        ageInDaysOutput = findViewById(R.id.age_in_day_output)

        selectDateBtn.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, {
                _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val dateText =  "${addLeadingZero(selectedDayOfMonth)}.${addLeadingZero(selectedMonth+1)}.$selectedYear"
            birthDayOutput.text = dateText
            val sdf = SimpleDateFormat("dd.mm.yyyy", Locale.ENGLISH)
            val date = sdf.parse(dateText)
            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
            ageInMinutesOutput.text = "${findAgeIn(date, currentDate, millisInMinute)}"
            ageInDaysOutput.text = "${findAgeIn(date, currentDate, millisInDay)}"

        }, year, month, dayOfMonth)
        dpd.datePicker.maxDate = Date().time - millisInDay
        dpd.show()
    }

    private fun addLeadingZero(text : Int ): String {
        return if(text < 10) "0$text" else "$text"
    }

    private fun findAgeIn(birthDate: Date?, currentDate: Date?,  unit: Int) : Long {
        val selectedDateInMinutes = birthDate!!.time / unit
        val currentDateInMinutes = currentDate!!.time / unit
        return currentDateInMinutes - selectedDateInMinutes
    }
}