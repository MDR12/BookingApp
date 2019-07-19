package com.example.meetingroombookingapp.model

import com.example.meetingroombookingapp.common.Constant.NOTHING

class RoomModel {
    lateinit var id: String
    var capacity: Int = NOTHING
    var floor: Int = NOTHING
    lateinit var name: String
}