package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.MyBookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.model.TimeModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class MyBookingPresenter(private val view: MyBookingContract.View): MyBookingContract.Presenter {

    private val db = FirebaseFirestore.getInstance()

    private val queryTime = db.collection(Constant.FIREBASE_COLLECTION_TIME)
    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)
    private val queryRoom = db.collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)

    override fun onGetTimeList() {

        val timeList = mutableListOf<TimeModel>()

        queryTime
                .orderBy(Constant.FIREBASE_ID, Query.Direction.ASCENDING)
                .get().addOnSuccessListener {
                    for (doc in it.documents) {
                        val book = doc.toObject(TimeModel::class.java)
                        if (book != null) {
                            timeList.add(book)
                        }
                    }
                    view.onGetTimeListDone(timeList)
                }.addOnFailureListener { exception ->
                    view.onfailLoad(Constant.TEXT_TIME_LIST)
                }
    }

    override fun onGetRoomList() {

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
                    view.onGetRoomListDone(roomList)
                }.addOnFailureListener { exception ->
                    view.onfailLoad(Constant.TEXT_ROOM_LIST)
                }
    }

    override fun onGetMyBooking(userName: String?, userPhone: String?) {

        val bookingList = mutableListOf<BookingModel>()

        queryBooking
                .whereEqualTo(Constant.FIREBASE_USER_NAME, userName)
                .whereEqualTo(Constant.FIREBASE_USER_PHONE, userPhone)
                .orderBy(Constant.FIREBASE_DATE, Query.Direction.ASCENDING)
                .orderBy(Constant.FIREBASE_TIME_BOOKING, Query.Direction.ASCENDING)
                .get().addOnSuccessListener {
                    for (doc in it.documents) {
                        val book = doc.toObject(BookingModel::class.java)
                        book?.id = doc.id
                        if (book != null) {
                            bookingList.add(book)
                        }
                    }
                    view.onGetMyBookingDone(bookingList)
                }.addOnFailureListener { exception ->
                    view.onfailLoad(Constant.TEXT_MY_BOOKING_LIST)
                }
    }

    override fun onSetMyBooking(myBooking: MutableList<BookingModel>, timeList: MutableList<TimeModel>, roomList: MutableList<RoomModel>) {

        val myBookingList = mutableListOf<MyBookingModel>()

        for (i in myBooking) {
            myBookingList.add(MyBookingModel(
                    i.id,
                    roomList.filter { it.id == i.room_id }[0].name,
                    roomList.filter { it.id == i.room_id }[0].floor,
                    SimpleDateFormat(Constant.FORMAT_DATE, Locale("th")).format(i.date),
                    timeList.filter { it.id == i.time_booking }[0].text
            ))
        }

        view.onShowMyBooking(myBookingList)
    }

}