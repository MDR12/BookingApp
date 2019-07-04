package com.example.meetingroombookingapp.userinfo

import com.example.meetingroombookingapp.model.BookingModel

interface AddBookingContract {

    interface View{
        fun onShowSuccess()
        fun onShowFail()
    }

    interface Presenter{
        fun addBookingToDataBase(allData: BookingModel)
    }
}