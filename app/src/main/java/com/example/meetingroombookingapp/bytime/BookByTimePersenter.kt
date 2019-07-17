package com.example.meetingroombookingapp.bytime

import com.example.meetingroombookingapp.common.Constant

class BookByTimePersenter(private val view: BookByTimeContract.View): BookByTimeContract.Presenter {

    override fun setTimeStartSpinner() {
        val time = Constant.ARR_TIME_START_TEXT
        view.onShowStartTimeSpinner(time)
    }

    override fun setTimeEndSpinner() {
        val time = Constant.ARR_TIME_END_TEXT
        view.onShowEndTimeSpinner(time)
    }

}