package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class BookByRoomPresenter(private val view: BookByRoomContract.View) : BookByRoomContract.Presenter {

    @SuppressLint("SimpleDateFormat")
    override fun getTimeCheckBox(date: String?) {

        val date = SimpleDateFormat("dd-MM-yyyy").parse(date)


    }


}