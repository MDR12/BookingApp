package com.example.meetingroombookingapp.byroom

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.byroom.adapter.CheckboxAdapter
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.common.Constant.NOTHING
import com.example.meetingroombookingapp.common.Constant.ONE_HOUR
import com.example.meetingroombookingapp.common.Constant.TEXT_NEW_LINE
import com.example.meetingroombookingapp.home.HomeActivity
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import kotlinx.android.synthetic.main.activity_book_by_room.*
import java.text.SimpleDateFormat
import java.util.*

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
            visibility = View.VISIBLE
        }
        ProgressBar_BookByRoom.visibility = View.GONE
    }

    override fun onShowSuccess() {
        Toast.makeText(this, Constant.TEXT_ADD_SUCCESS, Toast.LENGTH_SHORT).show()
    }

    override fun onShowFail() {
        Toast.makeText(this, Constant.TEXT_ADD_ERROR, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        val getRoomId = sharePref.getString(Constant.PREF_ROOM_ID, null)
        val roomName = sharePref.getString(Constant.PREF_ROOM_NAME, null)
        val floor = sharePref.getInt(Constant.PREF_ROOM_FLOOR, NOTHING)
        val userName = sharePref.getString(Constant.PREF_USER_NAME, null)
        val userPhone = sharePref.getString(Constant.PREF_USER_PHONE, null)
        val userTeam = sharePref.getString(Constant.PREF_USER_TEAM, null)
        val datePick = sharePref.getString(Constant.PREF_DATE_PICK, null)

        val strRoomName = Constant.TEXT_ROOM + roomName
        tv_BookByRoom_RoomName.text = strRoomName

        val strRoomFloor = Constant.TEXT_FLOOR + floor.toString()
        tv_BookByRoom_RoomFloor.text = strRoomFloor

        btn_book_byroom.setOnClickListener {

            val checkList = timeSlotPick.filter { it.isCheck }

            if (datePick != null && checkList.isNotEmpty()) {
                val date = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(datePick)
                val allData = mutableListOf<BookingDataModel>()
                val timeText: ArrayList<String?> = ArrayList()

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

                val builder = AlertDialog.Builder(this)
                var str =
                    Constant.TEXT_NAME + userName + Constant.TEXT_SPACE_ONE +
                            Constant.TEXT_TEL + userPhone + TEXT_NEW_LINE +
                            Constant.TEXT_ROOM + roomName + Constant.TEXT_SPACE_ONE +
                            Constant.TEXT_FLOOR + floor + TEXT_NEW_LINE +
                            Constant.TEXT_DATE + datePick + TEXT_NEW_LINE +
                            Constant.TEXT_TIME_SLOT_YOU_PICK

                if (timeText.size == ONE_HOUR) {
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
                Toast.makeText(this, Constant.TEXT_PLS_SELECT_DATE_TIME, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDatePickerDialog() {
        val roomId = sharePref.getString(Constant.PREF_ROOM_ID, null)
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)

        val toDay = GregorianCalendar(mYear, mMonth + 1, mDay, 0, 0,0)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, pYear, pMonth, pDayOfMonth ->

            val pickDay = GregorianCalendar(pYear, pMonth + 1, pDayOfMonth, 0, 0,0)

            if (toDay.before(pickDay) || toDay == pickDay){
                val date =
                        pDayOfMonth.toString() + Constant.TEXT_DATH + (pMonth + 1).toString() + Constant.TEXT_DATH + pYear.toString()
                date_picker.text = date

                ProgressBar_BookByRoom.visibility = View.VISIBLE

                dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(date)
                presenter.fetchTimeCheckBox(Constant.ARR_TIME_ALL_TEXT, dateFormat, roomId)

                val editor = sharePref.edit()
                editor.putString(Constant.PREF_DATE_PICK, date)
                editor.apply()
            }else{
                Toast.makeText(this, Constant.TEXT_CANT_PICK_DAY_AFTER, Toast.LENGTH_SHORT).show()
            }

        }, mYear, mMonth, mDay)

        date_picker.setOnClickListener {
            dpd.show()
        }
    }
}