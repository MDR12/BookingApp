package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class BookingDataModel(

        @ServerTimestamp
        var date: Date?,
        @ServerTimestamp
        var date_time_start: Date?,
        @ServerTimestamp
        var date_time_end: Date?,

        var user_name: String?,
        var user_phone: String?,
        var comment: String?,
        var room_id: String?,
        var status: String?,
        var user_cancel_name: String?,
        var user_cancel_phone: String?

        )