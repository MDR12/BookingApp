package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class BookingModel {

    var id: String? = null

    @ServerTimestamp
    var date: Date? = null

    var room_id: String? = null
    var room_floor: Int = 99
    var room_name: String? = null

    var time_booking: Int = 99
    var time_text: String? = null

    var user_name: String? = null
    var user_phone: String? = null
    var user_team: String? = null
}