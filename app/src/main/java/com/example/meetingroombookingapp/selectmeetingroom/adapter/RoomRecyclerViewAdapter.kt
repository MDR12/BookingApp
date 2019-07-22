package com.example.meetingroombookingapp.selectmeetingroom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.common.Constant.NOTHING
import com.example.meetingroombookingapp.model.RoomModel

class RoomRecyclerViewAdapter(
    private val roomList: MutableList<RoomModel>,
    private var mOnRoomClickListener: (position: String?, itemName: String?, itemFloor: Int) -> Unit
)
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

    inner class ViewHolder internal constructor(
        view: View, private val onRoomClickListener: (position: String?, itemName: String?, itemFloor: Int) -> Unit
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {

        var mRoomNameView: TextView? = itemView.findViewById(R.id.tv_roomName)
        var mCapacityView: TextView? = itemView.findViewById(R.id.tv_capacity)
        var mFloorView: TextView? = itemView.findViewById(R.id.tv_floor)
        var itemID: String? = null
        var itemName: String? = null
        var itemFloor: Int = NOTHING

        init {
            this.onRoomClickListener
            itemView.setOnClickListener(this)
        }

        fun bind(room: RoomModel){
            mRoomNameView?.text = room.name
            mCapacityView?.text = room.capacity.toString() + Constant.TEXT_PEOPLE
            mFloorView?.text = Constant.TEXT_FLOOR + Constant.TEXT_SPACE_ONE + room.floor.toString()
            itemID = room.id
            itemName = room.name
            itemFloor = room.floor
        }

        override fun onClick(v: View?) {
            mOnRoomClickListener.invoke(itemID, itemName, itemFloor)
        }
    }
}