package com.example.meetingroombookingapp.selectbyroom

import com.example.meetingroombookingapp.model.Booking
import com.example.meetingroombookingapp.model.RoomModel
import java.util.*

interface SelectRoomContract {

    interface View{
        fun onShowRoomList(data: MutableList<RoomModel>)
        fun onShowRoomListByTime(roomList: MutableList<RoomModel>, timeList: MutableList<Booking>, dateTimeStart: Date, dateTimeEnd: Date)
        fun onShowFloorSpinner(mAllFloor: Array<String>)
    }
    interface Presenter{
        fun setFloorSpinner()
        fun fetchRoomAll(floorSelect : String)
        fun fetchRoomByTime(floorSelect: String, date: Date, dateTimeStart: Date, dateTimeEnd: Date)
        fun checkRoomAvaliable(roomList: MutableList<RoomModel>, timeList: MutableList<Booking>, dateTimeStart: Date, dateTimeEnd: Date):  MutableList<RoomModel>
    }
}
