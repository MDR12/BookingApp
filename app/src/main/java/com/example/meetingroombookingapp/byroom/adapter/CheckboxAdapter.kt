package com.example.meetingroombookingapp.byroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel

class CheckboxAdapter(private val timeList: MutableList<CheckboxAdapterDataModel>, private val c: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constant.TYPE_AVALIABLE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_select_time, parent, false)
                AvailableViewHolder(view)
            }
            Constant.TYPE_BOOKED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_select_time_booked, parent, false)
                BookedViewHolder(view)
            }
            else -> throw IllegalArgumentException(Constant.TEXT_INVALIID)
        }
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun getItemViewType(position: Int): Int {
        return timeList[position].status
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val time = timeList[position]

        if(holder is BookedViewHolder){

            holder.bind(time)

        } else if (holder is AvailableViewHolder) {

            holder.bind(time)
        }
    }

    class AvailableViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var myCheckbox: CheckBox = itemView.findViewById(R.id.checkbox_time)
        var itemID: Int? = null

        fun bind(item: CheckboxAdapterDataModel){
            myCheckbox.text = item.timeText
            itemID = item.timeSlotID

            myCheckbox.setOnCheckedChangeListener(null)
            myCheckbox.isChecked = item.isCheck
            myCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isCheck = isChecked
            }
        }
    }

    class BookedViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        private var mUserName: TextView = itemView.findViewById(R.id.show_book_by_user_name)
        private var mUserPhone: TextView = itemView.findViewById(R.id.show_book_by_user_phone)
        private var mTime: TextView = itemView.findViewById(R.id.tv_time)

        fun bind(item: CheckboxAdapterDataModel){

            mUserName.text = item.userName
            mUserPhone.text = item.userPhone
            mTime.text = item.timeText
        }
    }
}