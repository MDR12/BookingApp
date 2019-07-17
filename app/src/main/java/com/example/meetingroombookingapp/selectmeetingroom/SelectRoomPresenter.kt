package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class SelectRoomPresenter(private val view: SelectRoomContract.View) : SelectRoomContract.Presenter {

    private val db = FirebaseFirestore.getInstance()
    private val queryRoom = db.collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)
    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)

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

    override fun setRoomListByTime(date: String, timeStart: Int, timeEnd: Int) {
        val dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(date)
        val arrTimeSlot = mutableListOf<Int>()

        val m = timeEnd - timeStart
        var start = timeStart
        if (m == 1) {
            arrTimeSlot.add(timeStart)
        } else {
            for (i in 0 until m) {
                arrTimeSlot.add(start++)
            }
        }

        val bookingRemoveList = mutableListOf<BookingModel>()
        val myRoomList = mutableListOf<RoomModel>()

        queryBooking
            .whereEqualTo(Constant.FIREBASE_DATE, dateFormat)
            .get().addOnSuccessListener { it ->
                for (doc in it.documents) {
                    val book = doc.toObject(BookingModel::class.java)
                    book?.id = doc.id
                    if (book != null && arrTimeSlot.contains(book.time_booking)) {
                        bookingRemoveList.add(book)
                    }
                }

                queryRoom
                    .orderBy(Constant.FIREBASE_NAME, Query.Direction.ASCENDING)
                    .get()
                    //.continueWith {}

                    .addOnSuccessListener {
                        for (doc in it.documents) {
                            val room = doc.toObject(RoomModel::class.java)
                            room?.id = doc.id
                            if (room != null) {
                                myRoomList.add(room)
                            }
                        }

                        if (bookingRemoveList.isNotEmpty()) {
                            for (removeThisBook in bookingRemoveList) {
                                val removeRoom = myRoomList.find { it.id == removeThisBook.room_id }
                                myRoomList.remove(removeRoom)
                            }
                        }
                        view.onGetRoomDone(myRoomList)
                        view.onShowRoomList(myRoomList)
                    }

            }
    }

    override fun setFloorSpinner() {
        val category = arrayOf(Constant.FLOOR_ALL, Constant.FLOOR_11, Constant.FLOOR_10, Constant.FLOOR_9, Constant.FLOOR_8, Constant.FLOOR_7)
        view.onShowFloorSpinner(category)
    }

    override fun addBookingToDataBase(allData: MutableList<BookingDataModel>) {
        for (i in allData){
            db.collection(Constant.FIREBASE_COLLECTION_BOOKING)
                .add(i)
        }
        view.onShowSuccess()
    }

}