package com.example.meetingroombookingapp.selectbyroom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.model.RoomModel

class RoomRecyclerViewAdapter(private val roomList: MutableList<RoomModel>, private var mOnRoomClickListener: OnRoomListener)
    : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_select_room, parent, false)

        return ViewHolder(view, mOnRoomClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room = roomList[position]
        holder.bind(room)
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    inner class ViewHolder internal constructor(view: View, private val onRoomClickListener: OnRoomListener?) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var mRoomNameView: TextView? = itemView.findViewById(R.id.tv_room_name)
        var mCapacityView: TextView? = itemView.findViewById(R.id.tv_capacity)
        var mFloorView: TextView? = itemView.findViewById(R.id.tv_floor)
        var itemID: String? = null
        var itemName: String? = null
        var itemFloor: String? = null

        init {
            this.onRoomClickListener
            itemView.setOnClickListener(this)
        }

        @SuppressLint("SetTextI18n")
        fun bind(room: RoomModel){
            mRoomNameView?.text = room.name
            mCapacityView?.text = "${room.capacity.toString()} people"
            mFloorView?.text = "Floor ${room.floor.toString()}"
            itemID = room.id.toString()
            itemName = room.name.toString()
            itemFloor = room.floor.toString()
        }

        override fun onClick(v: View?) {
            mOnRoomClickListener.onRoomClick(itemID, itemName, itemFloor)
        }
    }

    interface OnRoomListener {
        fun onRoomClick(position: String?, itemName: String?, itemFloor: String?)
    }
}