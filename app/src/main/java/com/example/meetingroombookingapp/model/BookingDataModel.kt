package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class BookingDataModel(

        var room_id: String?,

        @ServerTimestamp
        var date: Date?,
        var time_booking: Int?,

        @ServerTimestamp
        var date_time_start: Date?,
        @ServerTimestamp
        var date_time_end: Date?,

        var user_name: String?,
        var user_phone: String?

        )