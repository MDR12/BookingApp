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
    const val FIREBASE_ID = "id"

    //Format
    const val FORMAT_DATE = "dd-MM-yyyy"
    const val FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm"
    const val FORNAT_TIME = "HH:mm"

    //putExtra
    const val EXTRA_SHOW = "show"
    const val EXTRA_SHOW_ROOMALL = "roomAll"
    const val EXTRA_SHOW_ROOM_BY_TIME = "roomByTime"
    const val EXTRA_DATE = "date"
    const val EXTRA_TIME_START = "time_start"
    const val EXTRA_TIME_END = "time_end"

    //Text
    const val TEXT_CONFIRM_LOGOUT = "Are you sure you want to logout?"
    const val TEXT_CONFIRM_CANCEL_MYBOOKING = "Are you sure, you want to cancel this booking?"
    const val TEXT_CANT_PICK_DAY_AFTER = "Can not select the date of the past"
    const val TEXT_CANT_SELECT_TIME = "something wrong with your time"
    const val TEXT_LOGOUT = "Logout"
    const val TEXT_FILL_ALL_INFO = "Please fill all information"
    const val TEXT_ADD_SUCCESS = "Adding time booking successfully"
    const val TEXT_ADD_ERROR = "Error adding time booking"
    const val TEXT_INVALIID = "Invalid view type"
    const val TEXT_TIME_SLOT_YOU_PICK = "Time: "
    const val TEXT_PLS_SELECT_DATE_TIME ="Please select date and time"
    const val TEXT_DELETE_COMPLETE = "Delete Complete"
    const val TEXT_DELETEING = "Deleteing..."
    const val TEXT_ADDING = "Adding..."
    const val TEXT_ADDING_TITLE = "Add your booking"
    const val TEXT_YES = "Yes"
    const val TEXT_NO = "No"
    const val TEXT_CONFIRM_BOOKING = "Confirm Booking"
    const val TEXT_FAIL = "get failed with load"
    const val TEXT_TIME_LIST = "time list"
    const val TEXT_ROOM_LIST = "room list"
    const val TEXT_MY_BOOKING_LIST = "booking list"
    const val TEXT_CONFIRM = "Confirm"
    const val TEXT_PEOPLE = " people"
    const val TEXT_HI = "Hi, "
    const val TEXT_TEAM = "Team: "
    const val TEXT_FROM = "from "
    const val TEXT_ROOM = "Room: "
    const val TEXT_NAME = "Name: "
    const val TEXT_TEL = "Tel. "
    const val TEXT_FLOOR = "Floor "
    const val TEXT_DATE = "Date: "
    const val TEXT_TIME = "Time: "
    const val TEXT_NEW_LINE = "\n"
    const val TEXT_SPACE = "      "
    const val TEXT_V1 = "("
    const val TEXT_V2 = ")"
    const val TEXT_SPACE_ONE = " "
    const val TEXT_BOOK_BY = "Booked by: "
    const val TEXT_CANCEL = "Cancel"
    const val TEXT_DATH = "-"
    const val TEXT_DATH2 = " - "
    const val TEXT_DELETE = "Delete"

    //Int
    const val NOTHING = 99
    const val ONE_HOUR = 1

    //Firebase collection
    const val FIREBASE_COLLECTION_MEETINGROOM = "MeetingRoom"
    const val FIREBASE_COLLECTION_BOOKING = "Booking"
    const val FIREBASE_COLLECTION_TIME = "Time"

    //fire doc
    const val FIREBASE_USER_NAME = "user_name"
    const val FIREBASE_USER_PHONE = "user_phone"
    const val FIREBASE_NAME = "name"
    const val FIREBASE_ROOM_ID = "room_id"
    const val FIREBASE_DATE = "date"
    const val FIREBASE_TIME_BOOKING = "time_booking"
    const val FIREBASE_FLOOR = "floor"
    const val FIREBASE_ROOM_FLOOR = "room_floor"
    const val FIREBASE_ROOM_NAME = "room_name"

    //Floor
    const val FLOOR_SELECT = "Select floor"
    const val FLOOR_ALL = "All"
    const val FLOOR_11 = "11"
    const val FLOOR_10 = "10"
    const val FLOOR_9 = "9"
    const val FLOOR_8 = "8"
    const val FLOOR_7 = "7"

    //view Type
    const val TYPE_BOOKED = 0
    const val TYPE_AVALIABLE = 1

    //HTML
    const val HTML_LOGOUT = "<p><u>Logout</u></p>"

    //language
    const val TH = "th"

    //array
    val ARR_TIME_ALL_TEXT: Array<String> = arrayOf(
        "07:00 - 08:00",
        "08:00 - 09:00",
        "09:00 - 10:00",
        "10:00 - 11:00",
        "11:00 - 12:00",
        "12:00 - 13:00",
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00",
        "18:00 - 19:00",
        "19:00 - 20:00"
    )

    val ARR_TIME_START_TEXT: Array<String> = arrayOf(
        "07:00",
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00"
    )
    val ARR_TIME_END_TEXT: Array<String> = arrayOf(
        "08:00",
        "09:00",
        "10:00",
        "11:00",
        "12:00",
        "13:00",
        "14:00",
        "15:00",
        "16:00",
        "17:00",
        "18:00",
        "19:00",
        "20:00"
    )

}