package com.example.meetingroombookingapp.mybooking

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.MyBookingModel
import com.example.meetingroombookingapp.mybooking.adapter.MyBookingAdapter
import kotlinx.android.synthetic.main.activity_my_booking.*

class MyBookingActivity : AppCompatActivity(),MyBookingContract.View {

    private val presenter: MyBookingContract.Presenter = MyBookingPresenter(this)
    private var myBooking = mutableListOf<MyBookingModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_booking)

        val sp = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val userName = sp.getString(Constant.PREF_USER_NAME, null)
        val userPhone = sp.getString(Constant.PREF_USER_PHONE, null)

        progressBar_myBook.visibility = View.VISIBLE
        recyclerview_my_booking.visibility = View.GONE

        presenter.onGetMyBooking(userName, userPhone)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        if (item?.order == 0){

            val builder = AlertDialog.Builder(this)
            builder.setTitle(Constant.TEXT_CONFIRM_CANCEL_MYBOOKING)

            builder.setPositiveButton(Constant.TEXT_YES) { _, _ ->

                progressBar_myBook.visibility = View.VISIBLE
                recyclerview_my_booking.visibility = View.GONE

                presenter.deleteBooking(myBooking[item.groupId].bookingId, item.groupId)
                Toast.makeText(this, Constant.TEXT_DELETEING, Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton(Constant.TEXT_NO) { _, _ ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return super.onContextItemSelected(item)
    }

    override fun onShowMyBooking(bookingList: MutableList<MyBookingModel>) {
        progressBar_myBook.visibility = View.GONE
        recyclerview_my_booking.visibility = View.VISIBLE

        myBooking = bookingList
        val adapt = MyBookingAdapter(myBooking)

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
        onShowMyBooking(myBooking)
    }
}
