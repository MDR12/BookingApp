package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.RoomModel

interface SelectRoomContract {

    interface View{
        fun onShowRoomList(data: MutableList<RoomModel>)
        fun onShowFloorSpinner(mAllFloor: Array<String>)
        fun onGetRoomDone(roomList: MutableList<RoomModel>)
    }
    interface Presenter{
        fun setFloorSpinner()
        fun setRoomListByTime(date: String, timeStart: Int, timeEnd: Int)
        fun setRoomList(floorSelect : String, roomList: MutableList<RoomModel>)
        fun getRoomFromFirebase()
        fun addBookingToDataBase(allData: MutableList<BookingDataModel>)
    }
}
