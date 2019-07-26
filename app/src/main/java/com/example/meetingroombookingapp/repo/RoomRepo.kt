package com.example.meetingroombookingapp.repo

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

interface RoomRepo{
    fun getRoomAll(
        onSuccess:(MutableList<RoomModel>) -> Unit,
        onFail:() -> Unit
    )

    fun getRoomByTime(
        date: String,
        timeStart: Int,
        timeEnd: Int,
        onSuccess: (MutableList<RoomModel>) -> Unit,
        onFail: () -> Unit
    )

    fun addBooking(
        data: MutableList<BookingDataModel>,
        onSuccess: (Int) -> Unit,
        onFail: () -> Unit
    )
}

class RoomRepoImpl(private val firestore: FirebaseFirestore): RoomRepo{

    override fun getRoomAll(onSuccess: (MutableList<RoomModel>) -> Unit, onFail: () -> Unit) {
        val roomList = mutableListOf<RoomModel>()

        firestore.collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)
            .orderBy( Constant.FIREBASE_NAME, Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                for (doc in it.documents) {
                    val room = doc.toObject(RoomModel::class.java)
                    room?.id = doc.id
                    if (room != null) {
                        roomList.add(room)
                    }
                }
                onSuccess.invoke(roomList)
            }
    }

    override fun getRoomByTime(
        date: String,
        timeStart: Int,
        timeEnd: Int,
        onSuccess: (MutableList<RoomModel>) -> Unit,
        onFail: () -> Unit
    ) {
        val dateFormat = SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).parse(date)
        val arrTimeSlot = mutableListOf<Int>()

        val pickHours = timeEnd - timeStart
        var start = timeStart
        if (pickHours == Constant.ONE_HOUR) {
            arrTimeSlot.add(timeStart)
        } else {
            for (i in 0 until pickHours) {
                arrTimeSlot.add(start++)
            }
        }

        val bookingRemoveList = mutableListOf<BookingModel>()
        val myRoomList = mutableListOf<RoomModel>()

        firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
            .whereEqualTo(Constant.FIREBASE_DATE, dateFormat)
            .get().addOnSuccessListener { it ->
                for (doc in it.documents) {
                    val book = doc.toObject(BookingModel::class.java)
                    book?.id = doc.id
                    if (book != null && arrTimeSlot.contains(book.time_booking)) {
                        bookingRemoveList.add(book)
                    }
                }

                firestore.collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)
                    .orderBy(Constant.FIREBASE_NAME, Query.Direction.ASCENDING)
                    .get()
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
                                val removeRoom = myRoomList.find { thisRoom ->
                                    thisRoom.id == removeThisBook.room_id
                                }
                                myRoomList.remove(removeRoom)
                            }
                        }
                        onSuccess.invoke(myRoomList)
                    }
            }

    }

    override fun addBooking(
        data: MutableList<BookingDataModel>,
        onSuccess: (Int) -> Unit,
        onFail: () -> Unit
    ) {
        var n = 0

        for (i in data) {
            firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
                .add(i).addOnSuccessListener {
                    n++
                    onSuccess.invoke(n)
                }
        }
    }

}