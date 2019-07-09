package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import java.util.*

interface SelectRoomContract {

    interface View{
        fun onShowRoomList(data: MutableList<RoomModel>)
        fun onShowRoomListByTime(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date)
        fun onShowFloorSpinner(mAllFloor: Array<String>)
    }
    interface Presenter{
        fun setFloorSpinner()
        fun fetchRoomByTime(floorSelect: String, date: Date, dateTimeStart: Date, dateTimeEnd: Date)
        fun checkRoomAvaliable(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date):  MutableList<RoomModel>
        fun getRoomFromFirebase(): List<RoomModel>
        fun setRoomList(floorSelect : String, roomList: MutableList<RoomModel>)
    }
}
