package com.example.meetingroombookingapp.byroom

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import com.example.meetingroombookingapp.model.TimeModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.*

class BookByRoomPresenter(private val view: BookByRoomContract.View) : BookByRoomContract.Presenter {

    private val db = FirebaseFirestore.getInstance()
    private val queryTime = db.collection(Constant.FIREBASE_COLLECTION_TIME)
    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)
    private var fireStoreListenerTime: ListenerRegistration? = null
    private var fireStoreListenerBooking: ListenerRegistration? = null

    @SuppressLint("SimpleDateFormat")
    override fun getTimeList(): MutableList<TimeModel> {

        val timeList = mutableListOf<TimeModel>()

        fireStoreListenerTime = queryTime
                .orderBy("id", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(ContentValues.TAG, "Listen failed!", e)
                        return@EventListener
                    }

                    if (documentSnapshots != null) {
                        for (doc in documentSnapshots) {
                            val time = doc.toObject(TimeModel::class.java)
                            timeList.add(time)
                        }
                    }
                })

        return timeList
    }

    override fun getBookingList(): MutableList<BookingModel> {

        val bookingList = mutableListOf<BookingModel>()

        fireStoreListenerBooking = queryBooking
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

    override fun fetchTimeCheckBox(
        timeList: MutableList<TimeModel>,
        bookingList: MutableList<BookingModel>,
        dateFormat: Date,
        roomId: String?
    ) {

        val checkTimeDate =
            bookingList.filter { it.date == dateFormat && it.room_id == roomId } as MutableList<BookingModel>
        val timeCheckboxListData = mutableListOf<CheckboxAdapterDataModel>()

        if (checkTimeDate.isNotEmpty()) {

            //create checkbox data model
            for (i in 0 until timeList.size) {

                var hasDateTimeBooking = bookingList.filter { it.time_booking == i }

                if (hasDateTimeBooking.isNotEmpty()) {

                    hasDateTimeBooking.size

                    timeCheckboxListData.add(
                        CheckboxAdapterDataModel(
                            timeList[i].text,
                            "Booked by " + hasDateTimeBooking[0].user_name,
                            "Tel. " + hasDateTimeBooking[0].user_phone,
                            hasDateTimeBooking[0].time_booking,
                            Constant.TYPE_BOOKED
                        )
                    )
                } else {
                    timeCheckboxListData.add(
                        CheckboxAdapterDataModel(
                            timeList[i].text,
                            null,
                            null,
                            i,
                            Constant.TYPE_AVALIABLE
                        )
                    )
                }
            }

            //send data to adapter
            view.onShowListCheckBox(timeCheckboxListData)

        } else {
            for (i in 0 until timeList.size) {
                timeCheckboxListData.add(
                    CheckboxAdapterDataModel(
                        timeList[i].text,
                        null,
                        null,
                        i,
                        Constant.TYPE_AVALIABLE
                    )
                )
            }
            view.onShowListCheckBox(timeCheckboxListData)
        }

    }

    override fun addBookingToDataBase(allData: MutableList<BookingDataModel>) {

        for (i in allData){
            db.collection("Booking")
                    .add(i)
//                    .addOnSuccessListener { documentReference ->
//                        view.onShowSuccess()
//                    }
//                    .addOnFailureListener {
//                        view.onShowFail()
//                    }
        }
    }

}
