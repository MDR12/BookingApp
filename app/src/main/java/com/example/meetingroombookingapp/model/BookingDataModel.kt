package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class BookingDataModel(

        @ServerTimestamp
        var date: Date?,

        var room_id: String?,

        var user_name: String?,
        var user_phone: String?,

        var time_booking: Int?

        )