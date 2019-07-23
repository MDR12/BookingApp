package com.example.meetingroombookingapp.mybooking.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        private var mRoomNameView: TextView = itemView.findViewById(R.id.tv_myRoomName)
        private var mMyFloorView: TextView = itemView.findViewById(R.id.tv_myFloor)
        private var mDateView: TextView = itemView.findViewById(R.id.tv_myDate)
        private var mTimeView: TextView = itemView.findViewById(R.id.tv_myTime)
        private var mItem: ConstraintLayout? = itemView.findViewById(R.id.itemMyBooking)
        private var mItemName: String? = null
        private var bookingID: String? = null

        fun bind(booking: MyBookingModel){

            val strRoomName = Constant.TEXT_ROOM + booking.roomName
            val strRoomFloor = Constant.TEXT_FLOOR + booking.roomFloor.toString()
            val strDate = Constant.TEXT_DATE + booking.dateText
            val strTime = Constant.TEXT_TIME + booking.timeText

            mRoomNameView.text = strRoomName
            mMyFloorView.text = strRoomFloor
            mDateView.text = strDate
            mTimeView.text = strTime
            bookingID = booking.bookingId
            mItemName = booking.roomName + Constant.TEXT_SPACE_ONE + booking.timeText

            mItem?.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
            menu?.setHeaderTitle(mItemName)
            menu?.add(this.adapterPosition, 99, 0, Constant.TEXT_DELETE)
        }

    }
}