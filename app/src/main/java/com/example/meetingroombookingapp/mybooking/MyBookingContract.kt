package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.MyBookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.model.TimeModel

interface MyBookingContract {

    interface View{
        fun onShowMyBooking(bookingList: MutableList<MyBookingModel>)
        fun onGetMyBookingDone(myBookingList: MutableList<BookingModel>)
        fun onGetTimeListDone(timeList: MutableList<TimeModel>)
        fun onGetRoomListDone(roomList: MutableList<RoomModel>)
    }

    interface Presenter{
        fun onGetMyBooking(userName: String, userPhone: String)
        fun onGetTimeList()
        fun onGetRoomList()
        fun onSetMyBooking(myBooking: MutableList<BookingModel>, timeList: MutableList<TimeModel>, roomList: MutableList<RoomModel>)
    }
}