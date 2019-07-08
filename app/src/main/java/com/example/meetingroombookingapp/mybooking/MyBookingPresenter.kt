package com.example.meetingroombookingapp.mybooking

import android.content.ContentValues
import android.util.Log
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MyBookingPresenter(private val view: MyBookingContract.View): MyBookingContract.Presenter {

    private val db = FirebaseFirestore.getInstance()
    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)
    private var fireStoreListenerBooking: ListenerRegistration? = null

    override fun onGetMyBooking(userName: String?, userPhone: String?){

        val bookingList = mutableListOf<BookingModel>()

        fireStoreListenerBooking = queryBooking
                .whereEqualTo("user_name",userName)
                .whereEqualTo("user_phone",userPhone)
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(ContentValues.TAG, "Listen failed!", e)
                        return@EventListener
                    }

                    if (documentSnapshots != null) {
                        bookingList.clear()
                        for (doc in documentSnapshots) {
                            val room = doc.toObject(BookingModel::class.java)
                            room.id = doc.id
                            bookingList.add(room)
                        }
                    }
                })

        view.onShowMyBooking(bookingList)
    }

}