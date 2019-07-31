package com.example.meetingroombookingapp.byroom

import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import java.util.*

interface BookByRoomContract {

    interface View{
        fun onShowListCheckBox(timeList: MutableList<CheckboxAdapterDataModel>)
        fun onShowSuccess()
        fun onShowFail()

    }
    interface Presenter{
        fun subscribe(view: View)
        fun unSubscribe()
        fun fetchTimeCheckBox(timeList: Array<String>, dateFormat: Date, roomId: String?)
        fun addBookingToDataBase(allData: MutableList<BookingDataModel>)
    }
}