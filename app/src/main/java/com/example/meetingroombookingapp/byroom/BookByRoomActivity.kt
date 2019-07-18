package com.example.meetingroombookingapp.byroom

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
import com.example.meetingroombookingapp.common.Constant.TEXT_NEW_LINE
import com.example.meetingroombookingapp.home.HomeActivity
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import kotlinx.android.synthetic.main.activity_book_by_room.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BookByRoomActivity : AppCompatActivity(), BookByRoomContract.View {

    private val presenter: BookByRoomContract.Presenter = BookByRoomPresenter(this)

    private lateinit var dateFormat: Date
    private var timeSlotPick = mutableListOf<CheckboxAdapterDataModel>()
    private val sharePref: SharedPreferences by lazy {
        getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_by_room)
        initView()
        initDatePickerDialog()
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

    private fun initView() {
        bt_book_byroom.setOnClickListener {
            val getRoomId = sharePref.getString(Constant.PREF_ROOM_ID, null)
            val roomName = sharePref.getString(Constant.PREF_ROOM_NAME, null)
            val floor = sharePref.getInt(Constant.PREF_ROOM_FLOOR, 99)
            val userName = sharePref.getString(Constant.PREF_USER_NAME, null)
            val userPhone = sharePref.getString(Constant.PREF_USER_PHONE, null)
            val userTeam = sharePref.getString(Constant.PREF_USER_TEAM, null)
            val datePick = sharePref.getString(Constant.PREF_DATE_PICK, null)
            val date = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(datePick)

            val allData = mutableListOf<BookingDataModel>()
            val timeText: ArrayList<String?> = ArrayList()
            val checkList = timeSlotPick.filter { it.isCheck }

            for (i in 0 until checkList.size) {
                allData.add(
                    i, BookingDataModel(
                        date,
                        getRoomId,
                        floor,
                        roomName,
                        userName,
                        userPhone,
                        userTeam,
                        checkList[i].timeSlotID,
                        checkList[i].timeText
                    )
                )
                timeText.add(checkList[i].timeText)
            }

            if (datePick != null && allData.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                var str =
                    Constant.TEXT_NAME + userName + Constant.TEXT_SPACE_ONE +
                            Constant.TEXT_TEL + userPhone + TEXT_NEW_LINE +
                            Constant.TEXT_ROOM + roomName + Constant.TEXT_SPACE_ONE +
                            Constant.TEXT_FLOOR + floor + TEXT_NEW_LINE +
                            Constant.TEXT_DATE + datePick + TEXT_NEW_LINE +
                            Constant.TEXT_TIME_SLOT_YOU_PICK

                if (timeText.size == 1) {
                    str += Constant.TEXT_SPACE_ONE + timeText[0]
                } else {
                    for (element in timeText)
                        str += TEXT_NEW_LINE + Constant.TEXT_SPACE + element
                }
                builder.setTitle(Constant.TEXT_CONFIRM_BOOKING)
                builder.setMessage(str)

                builder.setPositiveButton(Constant.TEXT_CONFIRM) { _, _ ->

                    presenter.addBookingToDataBase(allData)

                    val i = Intent(this, HomeActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)
                }

                builder.setNegativeButton(Constant.TEXT_NO) { _, _ -> }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            } else {
                Toast.makeText(this, Constant.TEXT_FILL_ALL_INFO, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDatePickerDialog() {
        val roomId = sharePref.getString(Constant.PREF_ROOM_ID, null)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, pYear, pMonth, pDayOfMonth ->
            val date =
                pDayOfMonth.toString() + Constant.TEXT_DATH + (pMonth + 1).toString() + Constant.TEXT_DATH + pYear.toString()
            date_picker.text = date

            dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(date)
            presenter.fetchTimeCheckBox(Constant.ARR_TIME_ALL_TEXT, dateFormat, roomId)

            val editor = sharePref.edit()
            editor.putString(Constant.PREF_DATE_PICK, date)
            editor.apply()

        }, year, month, day)

        date_picker.setOnClickListener {
            dpd.show()
        }
    }
}