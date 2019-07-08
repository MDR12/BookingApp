package com.example.meetingroombookingapp.byroom

import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import com.example.meetingroombookingapp.model.TimeModel
import java.util.*

interface BookByRoomContract {

    interface View{
        fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>)
        fun onShowSuccess()
        fun onShowFail()
        fun onAddTimeSlot()
        fun onRemoveTimeSlot()

    }
    interface Presenter{
        fun getTimeList(): MutableList<TimeModel>
        fun getBookingList(): MutableList<BookingModel>
        //fun getBookListInDate(date: Date, roomId: String?): MutableList<CheckBoxModel>
        fun fetchTimeCheckBox(
            timeList: MutableList<TimeModel>,
            bookingList: MutableList<BookingModel>,
            dateFormat: Date,
            roomId: String?
        )
        fun addBookingToDataBase(allData: BookingDataModel)
    }
}