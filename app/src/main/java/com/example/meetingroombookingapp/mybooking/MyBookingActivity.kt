package com.example.meetingroombookingapp.mybooking

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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

        progressBar_myBook.visibility = View.VISIBLE

        presenter.onGetMyBooking(userName, userPhone)
        presenter.onGetTimeList()
        presenter.onGetRoomList()
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.order == 0){

            progressBar_myBook.visibility = View.VISIBLE
            recyclerview_my_booking.visibility = View.GONE

            presenter.deleteBooking(myBooking[item.groupId].id, item.groupId)
            Toast.makeText(this, Constant.TEXT_DELETEING, Toast.LENGTH_SHORT).show()
        }

        return super.onContextItemSelected(item)
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

        progressBar_myBook.visibility = View.GONE
        recyclerview_my_booking.visibility = View.VISIBLE

        val adapt = MyBookingAdapter(bookingList)

        recyclerview_my_booking.apply {
            layoutManager = LinearLayoutManager(this@MyBookingActivity)
            adapter = adapt
        }
    }

    override fun onfailLoad(type: String) {
        Toast.makeText(this, Constant.TEXT_FAIL + type, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteOK(groupId: Int) {
        Toast.makeText(this, Constant.TEXT_DELETE_COMPLETE, Toast.LENGTH_SHORT).show()
        myBooking.removeAt(groupId)
        handleFinishQuery()
    }
}
