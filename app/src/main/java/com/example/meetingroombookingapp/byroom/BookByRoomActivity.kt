package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
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
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class BookByRoomActivity : AppCompatActivity(),BookByRoomContract.View {

    private val presenter: BookByRoomContract.Presenter = BookByRoomPresenter(this)

    private lateinit var dateFormat: Date
    private var timeSlotPick = mutableListOf<CheckboxAdapterDataModel>()

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_room)

        val sp = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val roomId = sp.getString(Constant.PREF_ROOM_ID, "")

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val timeList = presenter.getTimeList()
        val bookingList = presenter.getBookingList()

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

            var date = "$dayOfMonth-" + (month + 1) + "-$year"
            date_picker.text = date

            dateFormat = SimpleDateFormat(Constant.FORMAT_DATE).parse(date)

            presenter.fetchTimeCheckBox(timeList, bookingList, dateFormat, roomId)

            val editor = sp.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)

        date_picker.setOnClickListener {
            dpd.show()
        }

        bt_book_byroom.setOnClickListener {

            val roomId = sp.getString(Constant.PREF_ROOM_ID, null)
            val roomName = sp.getString(Constant.PREF_ROOM_NAME, null)
            val floor = sp.getString(Constant.PREF_ROOM_FLOOR, null)
            val userName = sp.getString(Constant.PREF_USER_NAME, null)
            val userPhone = sp.getString(Constant.PREF_USER_PHONE, null)

            val datePick = sp.getString(Constant.PREF_DATE_PICK, null)
            val date = SimpleDateFormat(Constant.FORMAT_DATE).parse(datePick)

//            val pickStartTime = sp.getString("pick_start_time", "")
//            val pickEndTime = sp.getString("pick_end_time", "")

//            val start = "$datePick $pickStartTime"
//            val dateTimeStart = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(start)
//            val end = "$datePick $pickEndTime"
//            val dateTimeEnd = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(end)

            var allData = mutableListOf<BookingDataModel>()
            var timeText: ArrayList<String?> = ArrayList()
            var checkList =  timeSlotPick.filter { it.isCheck }

            for (i in 0 until checkList.size) {

                allData.add(i, BookingDataModel(
                        date,
                        roomId,
                        userName,
                        userPhone,
                        checkList[i].timeSlotID

                ))

                timeText.add(checkList[i].timeText)
            }

            if (datePick != null && allData.isNotEmpty()) {

                val builder = AlertDialog.Builder(this)
                var str = "Name: $userName Tel.: $userPhone\n" +
                        "Room: $roomName Floor $floor\n" +
                        "Date: $datePick\n" +
                        "Time slot you pick:\n"

                if (timeText != null) {
                    for (element in timeText)
                        str += "    $element \n"
                }

                builder.setTitle("Confirm Booking")

                builder.setMessage(str)
                builder.setPositiveButton("Confirm Booking") { _, _ ->

                    Toast.makeText(applicationContext, "Adding your time booking", Toast.LENGTH_SHORT).show()

                    presenter.addBookingToDataBase(allData)

                    val i = Intent(this, HomeActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                }

                builder.setNegativeButton("No") { _, _ -> }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            }else{
                Toast.makeText(this, Constant.TEXT_FILL_ALL_INFO, Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>) {
        timeSlotPick = timeList
        val adapt = CheckboxAdapter(timeSlotPick, this)
        recyclerview_checkbox.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }
    }

    override fun onShowSuccess() {
        Toast.makeText(this, Constant.TEXT_ADD_SUCCESS, Toast.LENGTH_SHORT).show()
    }

    override fun onShowFail() {
        Toast.makeText(this, Constant.TEXT_ADD_ERROR, Toast.LENGTH_SHORT).show()
    }
}

