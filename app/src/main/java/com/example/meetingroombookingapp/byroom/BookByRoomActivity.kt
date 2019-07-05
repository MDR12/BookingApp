package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import kotlinx.android.synthetic.main.activity_book_by_room.*
import java.util.*

class BookByRoomActivity : AppCompatActivity(),BookByRoomContract.View {

    private val presenter: BookByRoomContract.Presenter = BookByRoomPresenter(this)
    private val PREFNAME = "MyPreferences"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_room)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date: String? = null

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            date = "$dayOfMonth-"+(month + 1)+"-$year"
            date_picker.text = date

            val sp = getSharedPreferences(PREFNAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString("date_pick", date)
            editor.apply()

        }, year, month, day)

        date_picker.setOnClickListener {
            dpd.show()
            presenter.getTimeCheckBox(date)
        }


    }
}
