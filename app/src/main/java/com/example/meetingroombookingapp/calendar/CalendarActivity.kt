package com.example.meetingroombookingapp.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.TimeAvailable.TimeAvailableActivity
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {

    private val PREFNAME = "MyPreferences"

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.meetingroombookingapp.R.layout.activity_calendar)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$dayOfMonth-"+(month + 1)+"-$year"

//            val calendar = Calendar.getInstance()
//
//            val today = calendar.time
//            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
//            val todayAsString = dateFormat.format(today)

            val sp = getSharedPreferences(PREFNAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("date_pick", date)
            editor.apply()

            val intent = Intent(this, TimeAvailableActivity::class.java)
            startActivity(intent)

        }
    }
}
