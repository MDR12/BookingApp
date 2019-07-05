package com.example.meetingroombookingapp.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.TimeAvailable.TimeAvailableActivity
import com.example.meetingroombookingapp.common.Constant
import kotlinx.android.synthetic.main.activity_calendar.*


class CalendarActivity : AppCompatActivity() {


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

            val sp = getSharedPreferences(Constant.PREF_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

            val intent = Intent(this, TimeAvailableActivity::class.java)
            startActivity(intent)

        }
    }
}
