package com.example.meetingroombookingapp.common

object  Constant {

    //MyPreferences
    const val PREF_NAME = "MyPreferences"
    const val PREF_DATE_PICK = "date_pick"
    const val PREF_PICK_START_TIME = "pick_start_time"
    const val PREF_PICK_END_TIME = "pick_end_time"
    const val PREF_USER_NAME = "user_name"
    const val PREF_USER_PHONE = "user_phone"
    const val PREF_USER_TEAM = "user_team"
    const val PREF_ROOM_ID = "room_id"
    const val PREF_ROOM_NAME = "room_name"
    const val PREF_ROOM_FLOOR = "floor"
    const val PREF_TIME_SLOT = "time_slot"

    //Format
    const val FORMAT_DATE = "dd-MM-yyyy"
    const val FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm"
    const val FORNAT_TIME = "HH:mm"

    //putExtra
    const val EXTRA_SHOW = "show"

    //Text
    const val TEXT_FILL_ALL_INFO = "Please fill all information"
    const val TEXT_ROOM_USED = "Someone used the room at that time"
    const val TEXT_TIME_PARADOX = "Time start can't be less than time end"
    const val TEXT_TIME_NOT_PICK = "Please pick the time"
    const val TEXT_TEL = "Tel."
    const val TEXT_ADD_SUCCESS = "Adding time booking successfully"
    const val TEXT_ADD_ERROR = "Error adding time booking"

    //Firebase collection
    const val FIREBASE_COLLECTION_MEETINGROOM = "MeetingRoom"
    const val FIREBASE_COLLECTION_BOOKING = "Booking"
    const val FIREBASE_COLLECTION_TIME = "Time"

    //Floor
    const val FLOOR_ALL = "All"
    const val FLOOR_11 = "11"
    const val FLOOR_10 = "10"
    const val FLOOR_9 = "9"
    const val FLOOR_8 = "8"
    const val FLOOR_7 = "7"

    //view Type
    const val TYPE_BOOKED = 0
    const val TYPE_AVALIABLE = 1



}