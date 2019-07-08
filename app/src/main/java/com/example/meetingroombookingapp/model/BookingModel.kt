package com.example.meetingroombookingapp.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class BookingModel {

    var id: String? = null

    @ServerTimestamp
    var date_time_end: Date? = null

    @ServerTimestamp
    var date_time_start: Date? = null

    @ServerTimestamp
    var date: Date? = null

    var booking_time: IntArray? = null

    var user_name: String? = null
    var user_phone: String? = null
    var comment: String? = null
    var room_id: String? = null
    var status: String? = null
    var user_cancel_name: String? = null
    var user_cancel_phone: String? = null


}