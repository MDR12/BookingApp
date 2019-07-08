package com.example.meetingroombookingapp.mybooking

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.mybooking.adapter.MyBookingAdapter
import kotlinx.android.synthetic.main.activity_my_booking.*

class MyBookingActivity : AppCompatActivity(),MyBookingContract.View {

    private val presenter: MyBookingContract.Presenter = MyBookingPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        val sp = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        val userName = sp.getString(Constant.PREF_USER_NAME, null)
        val userPhone = sp.getString(Constant.PREF_USER_PHONE, null)

        presenter.onGetMyBooking(userName, userPhone)
    }

    override fun onShowMyBooking(bookingList: MutableList<BookingModel>) {

        val adapt = MyBookingAdapter(bookingList)

        recyclerview_my_booking.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }

    }
}
