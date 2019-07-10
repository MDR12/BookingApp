package com.example.meetingroombookingapp.selectbytime

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomActivity
import kotlinx.android.synthetic.main.activity_select_date_time.*
import java.text.SimpleDateFormat
import java.util.*

class SelectDateTimeActivity : AppCompatActivity(){

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date_time)

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val sdf = SimpleDateFormat(Constant.FORMAT_DATE)
        var date= sdf.format(Date())

        lateinit var start: String
        lateinit var end: String


        calendarView3.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = dayOfMonth.toString() + Constant.TEXT_DATH + (month + 1).toString() +  Constant.TEXT_DATH + year.toString()
        }

        new_pick_start.setOnClickListener {

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)
                c.set(Calendar.SECOND,0)
                start = SimpleDateFormat(Constant.FORNAT_TIME).format(c.time)
                new_pick_start.text = start

            }), hour, minute, true)

            tpd.show()
        }

        new_pick_end.setOnClickListener {

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)
                c.set(Calendar.SECOND,0)
                end = SimpleDateFormat(Constant.FORNAT_TIME).format(c.time)
                new_pick_end.text = end

            }), hour + 1, minute, true)

            tpd.show()
        }

        bt_find_room.setOnClickListener {

            val sp = getSharedPreferences(Constant.PREF_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()

            editor.putString("date_pick", date)
            editor.putString("just_startTime", start)
            editor.putString("just_endTime", end)
            editor.apply()

//            Toast.makeText(this, "$date --- $start --- $end", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra("show", "roomByTime")
            startActivity(intent)

        }


    }
}
