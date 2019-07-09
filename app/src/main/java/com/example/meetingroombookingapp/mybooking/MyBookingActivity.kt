package com.example.meetingroombookingapp.mybooking

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.MyBookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.model.TimeModel
import com.example.meetingroombookingapp.mybooking.adapter.MyBookingAdapter
import kotlinx.android.synthetic.main.activity_my_booking.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MyBookingActivity : AppCompatActivity(),MyBookingContract.View {

    private val presenter: MyBookingContract.Presenter = MyBookingPresenter(this)
    private var myBooking = mutableListOf<BookingModel>()
    private var myTimeList = mutableListOf<TimeModel>()
    private var myRoomList = mutableListOf<RoomModel>()
    private var countQuery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        val sp = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        val userName = sp.getString(Constant.PREF_USER_NAME, null)
        val userPhone = sp.getString(Constant.PREF_USER_PHONE, null)

        presenter.onGetMyBooking(userName, userPhone)
        presenter.onGetTimeList()
        presenter.onGetRoomList()
    }

    override fun onGetMyBookingDone(myBookingList: MutableList<BookingModel>) {
        myBooking = myBookingList
        countQuery += 1
        handleFinishQuery()
    }

    override fun onGetTimeListDone(timeList: MutableList<TimeModel>) {
        myTimeList = timeList
        countQuery += 1
        handleFinishQuery()
    }

    override fun onGetRoomListDone(roomList: MutableList<RoomModel>) {
        myRoomList = roomList
        countQuery += 1
        handleFinishQuery()
    }

    private fun handleFinishQuery() {
        if(countQuery == 3){
            presenter.onSetMyBooking(myBooking, myTimeList, myRoomList)
        }
    }

    override fun onShowMyBooking(bookingList: MutableList<MyBookingModel>) {

        val adapt = MyBookingAdapter(bookingList)

        recyclerview_my_booking.apply {
            layoutManager = LinearLayoutManager(this@MyBookingActivity)
            adapter = adapt
        }
    }
}
