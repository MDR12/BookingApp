package com.example.meetingroombookingapp.TimeAvailable

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import java.text.SimpleDateFormat


class TimeAvailableAdapter(private val notesList: MutableList<BookingModel>)
    : RecyclerView.Adapter<TimeAvailableAdapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_non_ava_room, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = notesList[position]
        holder.bind(list)
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view){

        var mUserNameView: TextView? = itemView.findViewById(R.id.tv_user_booking_name)
        var mUserTelView: TextView? = itemView.findViewById(R.id.tv_user_booking_tel)
        var mTimeView: TextView? = itemView.findViewById(R.id.tv_show_time)


        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(room: BookingModel){

            val dateFormat = SimpleDateFormat(Constant.FORNAT_TIME)
            val dateTimeStart = dateFormat.format(room.date_time_start)
            val dateTimeEnd = dateFormat.format(room.date_time_end)

            mTimeView?.text = "Booked $dateTimeStart to $dateTimeEnd"
            mUserNameView?.text = "By ${room.user_name}"
            mUserTelView?.text = "Tel. ${room.user_phone}"

        }
    }
}