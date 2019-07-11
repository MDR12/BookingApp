package com.example.meetingroombookingapp.bytime

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import kotlinx.android.synthetic.main.activity_book_by_time.*
import java.text.SimpleDateFormat
import java.util.*

class BookByTimeActivity : AppCompatActivity(),BookByTimeContract.View {

    val presenter : BookByTimeContract.Presenter = BookByTimePersenter(this)
    private lateinit var dateFormat: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_time)

        val sp = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        presenter.setTimeStartSpinner()
        presenter.setTimeEndSpinner()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            var date = dayOfMonth.toString() + Constant.TEXT_DATH + (month + 1).toString() +  Constant.TEXT_DATH + year.toString()
            book_by_time_get_date.text = date

            dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)  ).parse(date)

            val editor = sp.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)


        book_by_time_get_date.setOnClickListener{
            dpd.show()
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
