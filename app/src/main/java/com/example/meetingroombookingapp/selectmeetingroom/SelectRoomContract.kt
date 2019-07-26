package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.model.RoomModel
import java.util.*

interface SelectRoomContract {

    interface View{
        fun onShowRoomList(data: MutableList<RoomModel>)
        fun onShowFloorSpinner(mAllFloor: Array<String>)
        fun onGetRoomDone(roomList: MutableList<RoomModel>)
        fun onShowSuccess()
        fun onShowFail()
    }
    interface Presenter{
        fun subscribe(view: View)
        fun unSubscribe()
        fun setFloorSpinner()
        fun getRoomByTime(date: String, timeStart: Int, timeEnd: Int)
        fun setRoomList(floorSelect : String, roomList: MutableList<RoomModel>)
        fun getRoomAll()
        fun addBooking(
            dateFormat: Date,
            roomId: String?,
            floor: Int,
            roomName: String?,
            userName: String?,
            userPhone: String?,
            userTeam: String?,
            timeStart: Int,
            timeEnd: Int
        )
    }
}