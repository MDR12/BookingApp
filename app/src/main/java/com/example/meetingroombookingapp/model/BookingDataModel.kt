package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class BookingDataModel(

        @ServerTimestamp
        var date: Date?,

        var room_id: String?,
        var room_floor: Int,
        var room_name: String?,

        var user_name: String?,
        var user_phone: String?,
        var user_team: String?,

        var time_booking: Int,
        var time_text: String?

        )