package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class BookingModel {

    var id: String? = null

    @ServerTimestamp
    var date: Date? = null

    var room_id: String? = null

    var time_booking: Int? = null

    var user_name: String? = null
    var user_phone: String? = null
    var user_team: String? = null
}