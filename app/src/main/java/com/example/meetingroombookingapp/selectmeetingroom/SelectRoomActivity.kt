package com.example.meetingroombookingapp.selectmeetingroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.byroom.BookByRoomActivity
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.selectmeetingroom.adapter.RoomRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_select_room.*
import java.util.*


class SelectRoomActivity : AppCompatActivity(), SelectRoomContract.View, RoomRecyclerViewAdapter.OnRoomListener {

    private val presenter: SelectRoomContract.Presenter = SelectRoomPresenter(this)
    private var myRoomList = mutableListOf<RoomModel>()

    private lateinit var show: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)

        show = intent.getStringExtra(Constant.EXTRA_SHOW)

        progressBar_select_room.visibility = View.VISIBLE

        presenter.setFloorSpinner()
        presenter.getRoomFromFirebase()

        spinner_floor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (show == Constant.EXTRA_SHOW_ROOMALL) {
                    presenter.setRoomList(spinner_floor.getItemAtPosition(position).toString(), myRoomList)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

                if (show == Constant.EXTRA_SHOW_ROOMALL) {
                    presenter.setRoomList(Constant.FLOOR_ALL, myRoomList)
                }
            }
        }
    }

    override fun onShowRoomList(data: MutableList<RoomModel>) {

        progressBar_select_room.visibility = View.GONE

        val adapt = RoomRecyclerViewAdapter(data, this)

        recyclerview_floor.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
            visibility = View.VISIBLE
        }
    }


    override fun onShowFloorSpinner(mAllFloor: Array<String>) {
        val floor = ArrayAdapter(this, android.R.layout.simple_spinner_item, mAllFloor)
        floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_floor?.adapter = floor
    }

    override fun onRoomClick(position: String?, itemName: String?, itemFloor: String?) {

        val sp = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(Constant.PREF_ROOM_ID, position)
        editor.putString(Constant.PREF_ROOM_NAME, itemName)
        editor.putString(Constant.PREF_ROOM_FLOOR, itemFloor)
        editor.apply()

        if (show == Constant.EXTRA_SHOW_ROOMALL) {

            val intent = Intent(this, BookByRoomActivity::class.java)
            startActivity(intent)

        }
//        else if (show == Constant.EXTRA_SHOW_ROOM_BY_TIME) {
//            val intent = Intent(this, UserInfoActivity::class.java)
//            startActivity(intent)
//        }

    }

    override fun onGetRoomDone(roomList: MutableList<RoomModel>) {
        myRoomList = roomList
    }

    override fun onShowRoomListByTime(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date) {

    }


}