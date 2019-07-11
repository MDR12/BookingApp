package com.example.meetingroombookingapp.bytime

import com.example.meetingroombookingapp.common.Constant

class BookByTimePersenter(private val view: BookByTimeContract.View): BookByTimeContract.Presenter {

    override fun setTimeStartSpinner() {
        val time = arrayOf(Constant.TS_1,Constant.TS_2,Constant.TS_3,Constant.TS_4,Constant.TS_5,Constant.TS_6,Constant.TS_7,Constant.TS_8,Constant.TS_9, Constant.TS_10,Constant.TS_11,Constant.TS_12,Constant.TS_13)
        view.onShowStartTimeSpinner(time)
    }

    override fun setTimeEndSpinner() {
        val time = arrayOf(Constant.TS_2,Constant.TS_3,Constant.TS_4,Constant.TS_5,Constant.TS_6,Constant.TS_7,Constant.TS_8,Constant.TS_9, Constant.TS_10,Constant.TS_11,Constant.TS_12,Constant.TS_13,Constant.TS_14)
        view.onShowEndTimeSpinner(time)
    }

}