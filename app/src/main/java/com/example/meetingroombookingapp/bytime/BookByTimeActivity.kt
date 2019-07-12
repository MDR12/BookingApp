package com.example.meetingroombookingapp.bytime

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import kotlinx.android.synthetic.main.activity_book_by_time.*
import java.text.SimpleDateFormat
import java.util.*

class BookByTimeActivity : AppCompatActivity(),BookByTimeContract.View {

    val presenter : BookByTimeContract.Presenter = BookByTimePersenter(this)
    private lateinit var dateFormat: Date
    var positionTimeStart: Int = 99
    var positionTimeEnd: Int = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_time)

        val sp = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        presenter.setTimeStartSpinner()
        presenter.setTimeEndSpinner()

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            var date = dayOfMonth.toString() + Constant.TEXT_DATH + (month + 1).toString() +  Constant.TEXT_DATH + year.toString()
            book_by_time_get_date.text = date

            dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)  ).parse(date)

            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)


        book_by_time_get_date.setOnClickListener{
            dpd.show()
        }

        spinner_timeStart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionTimeStart = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                positionTimeStart = 0
            }
        }

        spinner_timeEnd.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionTimeEnd = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                positionTimeStart = 1
            }
        }

        bt_book_by_time_find_room.setOnClickListener {

//            val arrTimeSlot = mutableListOf<Int>()

            if (positionTimeStart != 99 && positionTimeEnd != 99 && positionTimeStart < positionTimeEnd) {



//                val m = positionTimeEnd - positionTimeStart
//                var start = positionTimeStart
//
//                if (m == 1) {
//                    arrTimeSlot.add(positionTimeStart)
//                } else {
//                    for (i in 0 until m) {
//                        arrTimeSlot.add(start++)
//                    }
//                }
//                Log.d("TAG", m.toString() + "<<<<<<<<<<")
//                for (element in arrTimeSlot) {
//                    Log.d("TAG", element.toString() + " ----------------------------------")
//                }

            } else {
                Toast.makeText(this, Constant.TEXT_CANT_SELECT_TIME, Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onShowStartTimeSpinner(allTimeStart: Array<String>) {
        val timeStart = ArrayAdapter(this, android.R.layout.simple_spinner_item, allTimeStart)
        timeStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_timeStart?.adapter = timeStart
    }

    override fun onShowEndTimeSpinner(allTimeEnd: Array<String>) {
        val timeEnd = ArrayAdapter(this, android.R.layout.simple_spinner_item, allTimeEnd)
        timeEnd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_timeEnd?.adapter = timeEnd
    }
}
