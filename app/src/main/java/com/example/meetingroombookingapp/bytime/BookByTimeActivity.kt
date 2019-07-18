package com.example.meetingroombookingapp.bytime

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomActivity
import kotlinx.android.synthetic.main.activity_book_by_time.*
import java.util.*

class BookByTimeActivity : AppCompatActivity(),BookByTimeContract.View {

    val presenter : BookByTimeContract.Presenter = BookByTimePresenter(this)
    private val sharePref: SharedPreferences by lazy {
        getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    }
    private var date: String? = null
    var positionTimeStart: Int = 99
    var positionTimeEnd: Int = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_time)
        initView()
        initDatePickerDialog()
        initSpinner()
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

    private fun initView(){
        btn_bookByTimeFindRoom.setOnClickListener {
            if (positionTimeStart != 99 && positionTimeEnd != 99 && positionTimeStart < positionTimeEnd && date != null) {

                val intent = Intent(this, SelectRoomActivity::class.java)
                intent.putExtra(Constant.EXTRA_SHOW, Constant.EXTRA_SHOW_ROOM_BY_TIME)
                intent.putExtra(Constant.EXTRA_TIME_START, positionTimeStart)
                intent.putExtra(Constant.EXTRA_TIME_END, positionTimeEnd)
                intent.putExtra(Constant.EXTRA_DATE, date)
                startActivity(intent)

            } else {
                Toast.makeText(this, Constant.TEXT_CANT_SELECT_TIME, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initDatePickerDialog(){
        val editor = sharePref.edit()

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        presenter.setTimeStartSpinner()
        presenter.setTimeEndSpinner()

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, pYear, pMonth, pDayOfMonth ->
            date = pDayOfMonth.toString() + Constant.TEXT_DATH + (pMonth + 1).toString() +  Constant.TEXT_DATH + pYear.toString()
            btn_bookByTimeGetDate.text = date
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)


        btn_bookByTimeGetDate.setOnClickListener{
            dpd.show()
        }
    }

    private fun initSpinner(){

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
    }
}
