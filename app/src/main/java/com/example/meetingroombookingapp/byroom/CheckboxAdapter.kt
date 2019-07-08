package com.example.meetingroombookingapp.byroom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel

class CheckboxAdapter(private val timeList: MutableList<CheckboxAdapterDataModel>, private val c: Context): RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_time, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return timeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = timeList[position]
        holder.mUserName.text = data.userName
        holder.mUserPhone.text = data.userPhone
        holder.mCheckbox.text = data.timeText

        if (data.status == "x"){
            holder.mCheckbox.isEnabled = false
        }

        holder.setItemClickListener(object : ItemClickListener{

            override fun onItemClick(v: View?, pos: Int) {
                val myCheckBox = v as CheckBox
                val currentTeacher = timeList[pos]

//                if (myCheckBox.isChecked) {
//                    currentTeacher.isSelected = true
//                } else if (!myCheckBox.isChecked) {
//                    currentTeacher.isSelected = false
//                }
            }

        })
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var mUserName: TextView = itemView.findViewById(R.id.show_book_by_user_name)
        var mUserPhone: TextView = itemView.findViewById(R.id.show_book_by_user_phone)
        var mCheckbox: CheckBox = itemView.findViewById(R.id.checkBox_time)

        lateinit var myItemClickListener: ItemClickListener

        init {
            itemView.setOnClickListener(this)
        }

        fun setItemClickListener(ic: ItemClickListener) {
            this.myItemClickListener = ic
        }

        override fun onClick(v: View?) {
            this.myItemClickListener.onItemClick(v, layoutPosition)
        }

    }

    interface ItemClickListener {

        fun onItemClick(v: View?, pos: Int)
    }
}