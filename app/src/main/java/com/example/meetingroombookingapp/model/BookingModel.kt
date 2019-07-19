package com.example.meetingroombookingapp.model

import com.example.meetingroombookingapp.common.Constant.NOTHING
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class BookingModel {

    lateinit var id: String

    @ServerTimestamp
    var date: Date? = null

    lateinit var room_id: String
    var room_floor: Int = NOTHING
    lateinit var room_name: String

    var time_booking: Int = NOTHING
    lateinit var time_text: String

    lateinit var user_name: String
    lateinit var user_phone: String
    lateinit var user_team: String
}