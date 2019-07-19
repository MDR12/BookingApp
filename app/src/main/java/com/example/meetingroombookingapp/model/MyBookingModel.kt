package com.example.meetingroombookingapp.model

data class MyBookingModel(
        var bookingId: String,
        var roomName: String,
        var roomFloor: Int,
        var dateText: String,
        var timeText: String
)
