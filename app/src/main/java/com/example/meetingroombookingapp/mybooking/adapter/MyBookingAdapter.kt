package com.example.meetingroombookingapp.mybooking.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.model.MyBookingModel

class MyBookingAdapter(private val bookingList: MutableList<MyBookingModel>): RecyclerView.Adapter<MyBookingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_booking, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookingList[position]
        holder.bind(book)
    }

    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var mRoomNameView: TextView? = itemView.findViewById(R.id.tv_my_room_name)
        var mMyFloorView: TextView? = itemView.findViewById(R.id.tv_my_floor)
        var mDateView: TextView? = itemView.findViewById(R.id.tv_my_date)
        var mTimeView: TextView? = itemView.findViewById(R.id.tv_my_time)

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(booking: MyBookingModel){
            mRoomNameView?.text = "Room: " + booking.roomName
            mMyFloorView?.text = "Floor: " + booking.roomFloor.toString()
            mDateView?.text = "Date: "+ booking.dateText
            mTimeView?.text = "Time: "+ booking.timeText

        }

    }
}