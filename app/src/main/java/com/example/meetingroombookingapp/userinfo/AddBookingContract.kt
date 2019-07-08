package com.example.meetingroombookingapp.userinfo

import com.example.meetingroombookingapp.model.BookingDataModel

interface AddBookingContract {

    interface View{
        fun onShowSuccess()
        fun onShowFail()
    }

    interface Presenter{
        fun addBookingToDataBase(allData: BookingDataModel)
    }
}