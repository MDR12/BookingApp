package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.model.MyBookingModel

interface MyBookingContract {

    interface View{
        fun onShowMyBooking(bookingList: MutableList<MyBookingModel>)
        fun onfailLoad(type:String)
        fun onDeleteOK(groupId: Int)
    }

    interface Presenter{
        fun onGetMyBooking(userName: String?, userPhone: String?)
        fun deleteBooking(id: String?, groupId: Int)
    }
}