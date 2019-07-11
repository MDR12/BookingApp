package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class SelectRoomPresenter(private val view: SelectRoomContract.View) : SelectRoomContract.Presenter {

    private val queryRoom = FirebaseFirestore.getInstance().collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)

    override fun getRoomFromFirebase(){

        val roomList = mutableListOf<RoomModel>()

        queryRoom
            .orderBy( Constant.FIREBASE_NAME, Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                for (doc in it.documents) {
                    val book = doc.toObject(RoomModel::class.java)
                    book?.id = doc.id
                    if (book != null) {
                        roomList.add(book)
                    }
                }
                view.onGetRoomDone(roomList)
                view.onShowRoomList(roomList)
            }
    }

    override fun setRoomList(floorSelect: String, roomList: MutableList<RoomModel>) {

        if (floorSelect == Constant.FLOOR_ALL){
            view.onShowRoomList(roomList)
        } else {
            val newList = roomList.filter { it.floor == floorSelect.toInt() }
            view.onShowRoomList(newList as MutableList<RoomModel>)
        }

    }

    override fun fetchRoomByTime(floorSelect: String, date: Date, dateTimeStart: Date, dateTimeEnd: Date) {

    }

    override fun checkRoomAvaliable(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date): MutableList<RoomModel> {
        return roomList
    }

    override fun setFloorSpinner() {
        val category = arrayOf(Constant.FLOOR_ALL, Constant.FLOOR_11, Constant.FLOOR_10, Constant.FLOOR_9, Constant.FLOOR_8, Constant.FLOOR_7)
        view.onShowFloorSpinner(category)
    }

}