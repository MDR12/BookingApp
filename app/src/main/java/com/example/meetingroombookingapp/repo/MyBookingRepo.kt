package com.example.meetingroombookingapp.repo

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.MyBookingModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

interface MyBookingRepo {
    fun getMyBooking(
        userName: String,
        userPhone: String,
        onSuccess: (MutableList<MyBookingModel>) -> Unit,
        onFail: () -> Unit
    )

    fun deleteBooking(
        id: String?,
        groupId: Int,
        onSuccess: (Int) -> Unit,
        onFail: () -> Unit
    )

}

class MyBookingRepoImpl(private val firestore: FirebaseFirestore) : MyBookingRepo {

    override fun getMyBooking(
        userName: String,
        userPhone: String,
        onSuccess: (MutableList<MyBookingModel>) -> Unit,
        onFail: () -> Unit
    ) {
        val bookingList = mutableListOf<BookingModel>()
        val myBookingList = mutableListOf<MyBookingModel>()

        firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
            .whereEqualTo(Constant.FIREBASE_USER_NAME, userName)
            .whereEqualTo(Constant.FIREBASE_USER_PHONE, userPhone)
            .orderBy(Constant.FIREBASE_DATE, Query.Direction.DESCENDING)
            .orderBy(Constant.FIREBASE_TIME_BOOKING, Query.Direction.DESCENDING)
            .orderBy(Constant.FIREBASE_ROOM_FLOOR)
            .orderBy(Constant.FIREBASE_ROOM_NAME)
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
                onSuccess.invoke(myBookingList)

            }.addOnFailureListener {
                onFail.invoke()
            }
    }

    override fun deleteBooking(id: String?, groupId: Int, onSuccess: (Int) -> Unit, onFail: () -> Unit) {
        firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
            .document(id.toString())
            .delete()
            .addOnSuccessListener {
                onSuccess.invoke(groupId)
            }
            .addOnFailureListener {
                onFail.invoke()
            }
    }

}