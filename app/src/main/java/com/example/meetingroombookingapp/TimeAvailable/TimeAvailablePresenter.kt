package com.example.meetingroombookingapp.TimeAvailable

import android.content.ContentValues
import android.util.Log
import com.example.meetingroombookingapp.model.Booking
import com.example.meetingroombookingapp.model.TimeInt
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.*

class TimeAvailablePresenter(private val view: TimeAvailableContract.View) : TimeAvailableContract.Presenter {

    private val query = FirebaseFirestore.getInstance().collection("Booking")
    private var fireStoreListener: ListenerRegistration? = null
    private val timeList = mutableListOf<Booking>()

    override fun fetchTimeBooked(roomId: String?, date: Date) {

        fireStoreListener = query
                .whereEqualTo("room_id", roomId)
                .whereEqualTo("date", date)
                .orderBy("date_time_start", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(ContentValues.TAG, "Listen failed!", e)
                        return@EventListener
                    }

                    if (documentSnapshots != null) {
                        timeList.clear()
                        for (doc in documentSnapshots) {
                            val room = doc.toObject(Booking::class.java)
                            room.id = doc.id
                            timeList.add(room)
                        }
                    }

                    if (timeList.size != 0)
                        view.onShowTimeList(timeList)
                    else
                        view.onShowNoListTime()

                })
    }

    override fun checkTimeNotMinus(startTime: TimeInt?, endTime: TimeInt?): Boolean {

        return if (endTime!!.hours > startTime!!.hours) {
            true
        } else endTime.minutes > startTime.minutes
    }

    override fun checkTimeAvailable(pickStartTime: Date?, pickEndTime: Date?): Boolean {

        var x = 0
        val arrTimeStart = arrayListOf<Date?>()
        val arrTimeEnd = arrayListOf<Date?>()

        if (timeList.isNotEmpty()) {

            for (time in timeList) {
                arrTimeStart.add(time.date_time_start)
                arrTimeEnd.add(time.date_time_end)
            }

            if ((pickStartTime!!.before(arrTimeStart[0]) && pickEndTime!!.before(arrTimeStart[0])) || (pickStartTime.after(arrTimeEnd.last()) && pickEndTime!!.after(arrTimeEnd.last()))) {
                x = 99
            }else{
                for (i in 0..(arrTimeStart.size - 2)){
                    if (pickStartTime.after(arrTimeEnd[i]) && pickEndTime!!.after(arrTimeEnd[i])
                            && pickStartTime.before(arrTimeStart[i+1]) && pickEndTime.before(arrTimeStart[i+1])){
                        x = 99
                    }
                }
            }

        }else{
            x = 99
        }

        return x == 99
    }
}
