package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.*

class BookByRoomPresenter(private val view: BookByRoomContract.View) : BookByRoomContract.Presenter {

    private val queryTime = FirebaseFirestore.getInstance().collection(Constant.FIREBASE_COLLECTION_TIME)
    private val queryBooking = FirebaseFirestore.getInstance().collection(Constant.FIREBASE_COLLECTION_BOOKING)
    private var fireStoreListenerTime: ListenerRegistration? = null
    private var fireStoreListenerBooking: ListenerRegistration? = null

    @SuppressLint("SimpleDateFormat")
    override fun getTimeList(): MutableList<String> {

        val timeList = mutableListOf<String>()

        fireStoreListenerTime = queryTime
                .orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(ContentValues.TAG, "Listen failed!", e)
                        return@EventListener
                    }

                    if (documentSnapshots != null) {
                        for (doc in documentSnapshots) {
                            val room = doc["text"].toString()
                            timeList.add(room)
                        }
                    }
                })

        return timeList
    }

    override fun getBookingList(): MutableList<BookingModel> {

        val bookingList = mutableListOf<BookingModel>()

        fireStoreListenerBooking = queryBooking
                .orderBy("text", Query.Direction.ASCENDING)
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

        return bookingList
    }

//    override fun getBookListInDate(date: Date, roomId: String?): MutableList<CheckBoxModel> {
//
//        val bookListInDate= mutableListOf<CheckBoxModel>()
//
//        fireStoreListenerBooking = queryBooking
//                .whereEqualTo("date", date)
//                .whereEqualTo("room_id", roomId)
//                .addSnapshotListener(EventListener { documentSnapshots, e ->
//                    if (e != null) {
//                        Log.e(ContentValues.TAG, "Listen failed!", e)
//                        return@EventListener
//                    }
//
//                    if (documentSnapshots != null) {
//                        bookListInDate.clear()
//
//                        for (doc in documentSnapshots) {
//                            val time: IntArray = doc["booking_time"] as IntArray
//                            val name: String = doc["user_name"] as String
//                            val phone: String = doc["user_phone"] as String
//                            bookListInDate.add(CheckBoxModel(time, name, phone))
//                        }
//                    }
//                })
//
//        return bookListInDate
//    }

    override fun fetchTimeCheckBox(timeList: MutableList<String>, bookingList: MutableList<BookingModel>, dateFormat: Date, roomId: String) {

        val checkTimeDate = bookingList.filter { (it.date == dateFormat) && (it.room_id == roomId) }

        val timeCheckboxListData = mutableListOf<CheckboxAdapterDataModel>()
        val arrTimeSlot: IntArray? = null

        if (checkTimeDate.isNotEmpty()) {

            //collect all booking time
            for (i in 0..checkTimeDate.size) {
                arrTimeSlot!!.plus(checkTimeDate[i].booking_time!!)
            }

            //create checkbox data model
            for (i in 0..timeList.size) {
                if (i in arrTimeSlot!!) {
                    val str = bookingList.filter { arrTimeSlot contentEquals it.booking_time!! }
                    timeCheckboxListData.add(CheckboxAdapterDataModel(timeList[i], "Booked by " + str[5].toString(), "Tel. " + str[6].toString(), "x"))
                } else {
                    timeCheckboxListData.add(CheckboxAdapterDataModel(timeList[i], "bob", null, "0"))
                }
            }

            //send data to adapter
            view.onShowListCheckBox(timeCheckboxListData)

        } else {
            for (i in 0 until timeList.size) {
                timeCheckboxListData.add(CheckboxAdapterDataModel(timeList[i], null, null, "0"))
            }
            view.onShowListCheckBox(timeCheckboxListData)
        }

    }

}
