package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.MyBookingModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class MyBookingPresenter(private val view: MyBookingContract.View): MyBookingContract.Presenter {

    private val db = FirebaseFirestore.getInstance()

    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)

    override fun onGetMyBooking(userName: String?, userPhone: String?) {
        val bookingList = mutableListOf<BookingModel>()
        val myBookingList = mutableListOf<MyBookingModel>()

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

                for (i in bookingList) {
                    myBookingList.add(
                        MyBookingModel(
                            i.id,
                            i.room_name,
                            i.room_floor,
                            SimpleDateFormat(Constant.FORMAT_DATE, Locale(Constant.TH)).format(i.date),
                            Constant.ARR_TIME_ALL_TEXT[i.time_booking]
                        )
                    )
                }
                view.onShowMyBooking(myBookingList)

                }.addOnFailureListener {
                view.onFailLoad(Constant.TEXT_MY_BOOKING_LIST)
                }
    }

    override fun deleteBooking(id: String?, groupId: Int) {

        queryBooking
            .document(id.toString())
            .delete()
            .addOnSuccessListener {
                view.onDeleteOK(groupId) }
            .addOnFailureListener {
                view.onFailLoad(Constant.TEXT_MY_BOOKING_LIST) }
    }

}