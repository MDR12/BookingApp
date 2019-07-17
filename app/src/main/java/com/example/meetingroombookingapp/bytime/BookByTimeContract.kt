package com.example.meetingroombookingapp.bytime

interface BookByTimeContract {

    interface View{
        fun onShowStartTimeSpinner(allTimeStart: Array<String>)
        fun onShowEndTimeSpinner(allTimeEnd: Array<String>)
    }

    interface Presenter{
        fun setTimeStartSpinner()
        fun setTimeEndSpinner()
    }
}