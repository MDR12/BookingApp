package com.example.meetingroombookingapp.byroom

import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import java.util.*

interface BookByRoomContract {

    interface View{
        fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>)

    }
    interface Presenter{
        fun getTimeList(): MutableList<String>
        fun getBookingList(): MutableList<BookingModel>
        //fun getBookListInDate(date: Date, roomId: String?): MutableList<CheckBoxModel>
        fun fetchTimeCheckBox(timeList: MutableList<String>, bookingList: MutableList<BookingModel>, dateFormat: Date, roomId: String)
    }
}