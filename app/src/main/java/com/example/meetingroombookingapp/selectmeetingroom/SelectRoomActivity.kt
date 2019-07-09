package com.example.meetingroombookingapp.selectmeetingroom

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
    private lateinit var show: String

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)

        show = intent.getStringExtra(Constant.EXTRA_SHOW)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        presenter.setFloorSpinner()

        var roomList = presenter.getRoomFromFirebase()

        spinner_floor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                if (show == "roomAll") {
                    presenter.setRoomList(spinner_floor.getItemAtPosition(position).toString(), roomList as MutableList<RoomModel>)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                if (show == "roomAll") {
                    presenter.setRoomList("All", roomList as MutableList<RoomModel>)
                }
            }
        }
    }

    override fun onShowRoomList(data: MutableList<RoomModel>) {

        val adapt = RoomRecyclerViewAdapter(data, this)

        recyclerview_floor.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }
    }

    override fun onShowFloorSpinner(mAllFloor: Array<String>) {
        val floor = ArrayAdapter(this, android.R.layout.simple_spinner_item, mAllFloor)
        floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_floor?.adapter = floor
    }

    override fun onRoomClick(id: String?, itemName: String?, itemFloor: String?) {

        val sp = getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(Constant.PREF_ROOM_ID, id)
        editor.putString(Constant.PREF_ROOM_NAME, itemName)
        editor.putString(Constant.PREF_ROOM_FLOOR, itemFloor)
        editor.apply()

        if (show == "roomAll") {

            val intent = Intent(this, BookByRoomActivity::class.java)
            startActivity(intent)

        } else if (show == "roomByTime") {
//
//            val intent = Intent(this, UserInfoActivity::class.java)
//            startActivity(intent)

        }

    }

    override fun onShowRoomListByTime(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date) {

    }


}