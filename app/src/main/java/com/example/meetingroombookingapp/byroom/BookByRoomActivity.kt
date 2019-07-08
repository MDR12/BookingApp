package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.byroom.adapter.CheckboxAdapter
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.home.HomeActivity
import com.example.meetingroombookingapp.model.BookingDataModel
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

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            date = "$dayOfMonth-"+(month + 1)+"-$year"
            date_picker.text = date

            val dateFormat = SimpleDateFormat(Constant.FORMAT_DATE).parse(date)

            presenter.fetchTimeCheckBox(timeList, bookingList, dateFormat, roomId)

            //           arrTimeChecked = presenter.getBookListInDate(dateFormat, roomId)

            //           presenter.fetchTimeCheckBox(timeList, bookingList)

            val sp = getSharedPreferences(Constant.PREF_NAME , Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)

        date_picker.setOnClickListener {
            dpd.show()
        }

        bt_book_byroom.setOnClickListener {

            val roomId = sp.getString("room_id", "")
            val roomName = sp.getString("room_name", "")
            val floor = sp.getString("floor", "")
            val userName = sp.getString("user_name", "No Name")
            val userPhone = sp.getString("user_phone", "No Telephone")

            val datePick = sp.getString("date_pick", "")
            val pickStartTime = sp.getString("pick_start_time", "")
            val pickEndTime = sp.getString("pick_end_time", "")

            val date = SimpleDateFormat("dd-MM-yyyy").parse(datePick)
            val start = "$datePick $pickStartTime"
            val dateTimeStart = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(start)
            val end = "$datePick $pickEndTime"
            val dateTimeEnd = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(end)

            val allData = BookingDataModel(
                roomId,
                date,
                0,
                dateTimeStart,
                dateTimeEnd,
                userName,
                userPhone
            )

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Confirm BookingModel")
            builder.setMessage(
                "Name: $userName Tel.: $userPhone\n" +
                        "Room: $roomName Floor $floor\n" +
                        "Date: $datePick\n" +
                        "Time: $pickStartTime to $pickEndTime"
            )

            builder.setPositiveButton("YES") { dialog, which ->
                // Do something when user press the positive button
                Toast.makeText(applicationContext, "Adding your time booking", Toast.LENGTH_SHORT).show()

                presenter.addBookingToDataBase(allData)

                val i = Intent(this, HomeActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }

            builder.setNegativeButton("No") { dialog, which -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

    override fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>) {

        val adapt = CheckboxAdapter(timeList, this)

        recyclerview_checkbox.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }
    }
}
