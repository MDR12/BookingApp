package com.example.meetingroombookingapp.byroom

interface BookByRoomContract {

    interface View{

    }
    interface Presenter{
        fun getTimeCheckBox(date: String?)
    }
}