package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.model.BookingModel

interface MyBookingContract {

    interface View{
        fun onShowMyBooking(bookingList: MutableList<BookingModel>)
    }

    interface Presenter{
        fun onGetMyBooking(userName: String?, userPhone: String?)
    }
}