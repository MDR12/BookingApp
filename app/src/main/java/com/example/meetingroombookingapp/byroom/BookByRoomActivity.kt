package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.CheckBoxModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import kotlinx.android.synthetic.main.activity_book_by_room.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BookByRoomActivity : AppCompatActivity(),BookByRoomContract.View {

    private val presenter: BookByRoomContract.Presenter = BookByRoomPresenter(this)

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_room)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        val roomId = sp.getString(Constant.PREF_ROOM_ID, "")

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        var date: String

        val timeList = presenter.getTimeList()
        val bookingList = presenter.getBookingList()
        var arrTimeChecked: MutableList<CheckBoxModel>

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            date = "$dayOfMonth-"+(month + 1)+"-$year"
            date_picker.text = date

            val dateFormat = SimpleDateFormat(Constant.FORMAT_DATE).parse(date)

            //presenter.fetchTimeCheckBox(timeList, bookingList.filter { it.date == dateFormat } as MutableList<BookingModel>)

            //arrTimeChecked = presenter.getBookListInDate(dateFormat, roomId)

            presenter.fetchTimeCheckBox(timeList, bookingList, dateFormat, roomId)

            val sp = getSharedPreferences(Constant.PREF_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)

        date_picker.setOnClickListener {
            dpd.show()
        }

    }

    override fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>) {

        val adapt = CheckboxAdapter(timeList, this)

        recyclerview_checkbox.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }
    }
}
