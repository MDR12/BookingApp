package com.example.meetingroombookingapp.TimeAvailable

import com.example.meetingroombookingapp.model.Booking
import com.example.meetingroombookingapp.model.TimeInt
import java.util.*

interface TimeAvailableContract {

    interface View{
        fun onShowTimeList(data: MutableList<Booking>)
        fun onShowNoListTime()
    }

    interface Presenter{
        fun fetchTimeBooked(roomId: String?, date: Date)
        fun checkTimeNotMinus(startTime: TimeInt?, endTime: TimeInt?): Boolean
        fun checkTimeAvailable(pickStartTime: Date?, pickEndTime: Date?): Boolean
    }
}