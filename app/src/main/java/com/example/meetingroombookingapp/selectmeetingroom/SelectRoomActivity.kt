package com.example.meetingroombookingapp.selectmeetingroom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.byroom.BookByRoomActivity
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.common.Constant.NOTHING
import com.example.meetingroombookingapp.common.Constant.ONE_HOUR
import com.example.meetingroombookingapp.home.HomeActivity
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.selectmeetingroom.adapter.RoomRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_select_room.*
import java.text.SimpleDateFormat
import java.util.*

class SelectRoomActivity : AppCompatActivity(), SelectRoomContract.View, RoomRecyclerViewAdapter.OnRoomListener {

    private val presenter: SelectRoomContract.Presenter = SelectRoomPresenter(this)
    private var myRoomList = mutableListOf<RoomModel>()
    private val sharePref: SharedPreferences by lazy {
        getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    }
    private var startTime: Int = NOTHING
    private var endTime: Int = NOTHING
    lateinit var date: String

    private lateinit var show: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)
        initView()
        initSpinner()
    }

    override fun onShowRoomList(data: MutableList<RoomModel>) {
        val adapt = RoomRecyclerViewAdapter(data, this)

        Handler().postDelayed({
            recyclerview_floor.visibility = View.VISIBLE
            progressBar_select_room.visibility = View.GONE

            recyclerview_floor.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = adapt
            }
        }, 500)
    }

    override fun onGetRoomDone(roomList: MutableList<RoomModel>) {
        myRoomList = roomList
    }

    override fun onShowFloorSpinner(mAllFloor: Array<String>) {
        val floor = ArrayAdapter(this, android.R.layout.simple_spinner_item, mAllFloor)
        floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_floor?.adapter = floor
    }

    override fun onRoomClick(position: String?, itemName: String?, itemFloor: Int) {
        val editor = sharePref.edit()
        editor.putString(Constant.PREF_ROOM_ID, position)
        editor.putString(Constant.PREF_ROOM_NAME, itemName)
        editor.putInt(Constant.PREF_ROOM_FLOOR, itemFloor)
        editor.apply()

        if (show == Constant.EXTRA_SHOW_ROOMALL) {
            val intent = Intent(this, BookByRoomActivity::class.java)
            startActivity(intent)
        } else if (show == Constant.EXTRA_SHOW_ROOM_BY_TIME) {
            addToFireBase()
        }
    }

    override fun onShowSuccess() {
        Toast.makeText(this, Constant.TEXT_ADD_SUCCESS, Toast.LENGTH_SHORT).show()
    }

    override fun onShowFail() {
        Toast.makeText(this, Constant.TEXT_ADD_ERROR, Toast.LENGTH_SHORT).show()
    }

    private fun addToFireBase() {

        val roomId = sharePref.getString(Constant.PREF_ROOM_ID, null)
        val roomName = sharePref.getString(Constant.PREF_ROOM_NAME, null)
        val floor = sharePref.getInt(Constant.PREF_ROOM_FLOOR, NOTHING)
        val userName = sharePref.getString(Constant.PREF_USER_NAME, null)
        val userPhone = sharePref.getString(Constant.PREF_USER_PHONE, null)
        val userTeam = sharePref.getString(Constant.PREF_USER_TEAM, null)

        val dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(date)

        val allData = mutableListOf<BookingDataModel>()
        val arrTimeSlot = mutableListOf<Int>()

        val m = endTime - startTime
        var start = startTime

        if (m == 1) {
            arrTimeSlot.add(startTime)
        } else {
            for (i in 0 until m) {
                arrTimeSlot.add(start++)
            }
        }

        for (i in 0 until arrTimeSlot.size) {

            allData.add(
                i, BookingDataModel(
                    dateFormat,
                    roomId,
                    floor,
                    roomName,
                    userName,
                    userPhone,
                    userTeam,
                    arrTimeSlot[i],
                    Constant.ARR_TIME_ALL_TEXT[arrTimeSlot[i]]
                )
            )
        }

        val builder = AlertDialog.Builder(this)
        var str =
            Constant.TEXT_NAME + userName + Constant.TEXT_SPACE_ONE +
                    Constant.TEXT_TEL + userPhone + Constant.TEXT_NEW_LINE +
                    Constant.TEXT_ROOM + roomName + Constant.TEXT_SPACE_ONE +
                    Constant.TEXT_FLOOR + floor + Constant.TEXT_NEW_LINE +
                    Constant.TEXT_DATE + date + Constant.TEXT_NEW_LINE +
                    Constant.TEXT_TIME_SLOT_YOU_PICK

        if (arrTimeSlot.size == ONE_HOUR){
            str += Constant.TEXT_SPACE_ONE + Constant.ARR_TIME_ALL_TEXT[arrTimeSlot[0]]
        }else{
            for (element in arrTimeSlot)
                str += Constant.TEXT_NEW_LINE + Constant.TEXT_SPACE + Constant.ARR_TIME_ALL_TEXT[element]
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
    }

    private fun initSpinner() {
        spinner_floor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                presenter.setRoomList(spinner_floor.getItemAtPosition(position).toString(), myRoomList)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                presenter.setRoomList(Constant.FLOOR_ALL, myRoomList)
            }
        }
    }

    private fun initView() {
        progressBar_select_room.visibility = View.VISIBLE
        show = intent.getStringExtra(Constant.EXTRA_SHOW)

        presenter.setFloorSpinner()

        if (show == Constant.EXTRA_SHOW_ROOMALL) {
            presenter.getRoomFromFireBase()
        }

        if (show == Constant.EXTRA_SHOW_ROOM_BY_TIME) {
            startTime = intent.getIntExtra(Constant.EXTRA_TIME_START, NOTHING)
            endTime = intent.getIntExtra(Constant.EXTRA_TIME_END, NOTHING)
            date = intent.getStringExtra(Constant.EXTRA_DATE)
            presenter.setRoomListByTime(date, startTime, endTime)
        }
    }

}