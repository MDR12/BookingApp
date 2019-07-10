package com.example.meetingroombookingapp.selectmeetingroom

import android.content.ContentValues.TAG
import android.util.Log
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.RoomModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import java.util.*

@Suppress("NAME_SHADOWING", "LABEL_NAME_CLASH")
class SelectRoomPresenter(private val view: SelectRoomContract.View) : SelectRoomContract.Presenter {

    private val queryRoom = FirebaseFirestore.getInstance().collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)

    private var fireStoreListenerRoom: ListenerRegistration? = null

    override fun getRoomFromFirebase(){

        val roomList = mutableListOf<RoomModel>()

        fireStoreListenerRoom = queryRoom
                .orderBy("name", Query.Direction.ASCENDING)
                .addSnapshotListener(EventListener { documentSnapshots, e ->
                    if (e != null) {
                        Log.e(TAG, "Listen failed!", e)
                        return@EventListener
                    }

                    if (documentSnapshots != null) {
                        roomList.clear()
                        for (doc in documentSnapshots) {
                            val room = doc.toObject(RoomModel::class.java)
                            room.id = doc.id
                            roomList.add(room)
                        }

                        view.onGetRoomDone(roomList)
                    }
                })
    }

    override fun setRoomList(floorSelect: String, roomList: MutableList<RoomModel>) {

        if (floorSelect == "All"){
            view.onShowRoomList(roomList)
        } else {
            val newList = roomList.filter { it.floor == floorSelect.toInt() }
            view.onShowRoomList(newList as MutableList<RoomModel>)
        }

    }

    override fun fetchRoomByTime(floorSelect: String, date: Date, dateTimeStart: Date, dateTimeEnd: Date) {

        val timeList = mutableListOf<BookingModel>()
        val roomList = mutableListOf<RoomModel>()

//        fireStoreListenerTime = queryBookingTime
//                .whereEqualTo("date", date)
//                .orderBy("date_time_start", Query.Direction.ASCENDING)
//                .addSnapshotListener(EventListener { documentSnapshots, e ->
//                    if (e != null) {
//                        Log.e(TAG, "Listen failed!", e)
//                        return@EventListener
//                    }
//
//                    if (documentSnapshots != null) {
//                        timeList.clear()
//                        for (doc in documentSnapshots) {
//                            val book = doc.toObject(BookingModel::class.java)
//                            book.id = doc.id
//                            timeList.add(book)
//                        }
//                    }
//                })
//
//        if (floorSelect == "All") {
//            fireStoreListenerRoom = queryRoom
//                    .orderBy("name", Query.Direction.ASCENDING)
//                    .addSnapshotListener(EventListener { documentSnapshots, e ->
//                        if (e != null) {
//                            Log.e(TAG, "Listen failed!", e)
//                            return@EventListener
//                        }
//
//                        if (documentSnapshots != null) {
//                            roomList.clear()
//                            for (doc in documentSnapshots) {
//                                val room = doc.toObject(RoomModel::class.java)
//                                room.id = doc.id
//                                roomList.add(room)
//                            }
//
//                            if (timeList.size != 0) {
//                                view.onShowRoomList(checkRoomAvaliable(roomList, timeList, dateTimeStart, dateTimeEnd))
//                            } else {
//                                view.onShowRoomList(roomList)
//                            }
//
//                        }
//                    })
//
//        } else {
//            fireStoreListenerRoom = queryRoom
//                    .orderBy("name", Query.Direction.ASCENDING)
//                    .whereEqualTo("floor", floorSelect.toInt())
//                    .addSnapshotListener(EventListener { documentSnapshots, e ->
//                        if (e != null) {
//                            Log.e(TAG, "Listen failed!", e)
//                            return@EventListener
//                        }
//
//                        if (documentSnapshots != null) {
//                            roomList.clear()
//                            for (doc in documentSnapshots) {
//                                val room = doc.toObject(RoomModel::class.java)
//                                room.id = doc.id
//                                roomList.add(room)
//                            }
//
//                            if (timeList.size != 0) {
//                                view.onShowRoomList(checkRoomAvaliable(roomList, timeList, dateTimeStart, dateTimeEnd))
//                            } else {
//                                view.onShowRoomList(roomList)
//                            }
//                        }
//                    })
//        }

    }

    override fun checkRoomAvaliable(roomList: MutableList<RoomModel>, timeList: MutableList<BookingModel>, dateTimeStart: Date, dateTimeEnd: Date): MutableList<RoomModel> {

//               val arrTimeStart = arrayListOf<Date?>()
//        val arrTimeEnd = arrayListOf<Date?>()
//        val arrID = arrayListOf<String?>()
//
//        for (time in timeList) {
//            arrTimeStart.add(time.date_time_start)
//            arrTimeEnd.add(time.date_time_end)
//            arrID.add(time.room_id)
//        }
//
//        for (i in arrTimeStart.indices) {
//                if (dateTimeStart.after(arrTimeStart[i]) && dateTimeStart.before(arrTimeEnd[i])
//                        || dateTimeEnd.after(arrTimeStart[i]) && dateTimeEnd.before(arrTimeEnd[i])) {
//                    roomList.removeAll { it.id.equals(arrID[i]) }
////
////                     Log.d("TAG", "-------------------0000${arrID[i]}000----------------------------")
//                }
//            }

        return roomList
    }

    override fun setFloorSpinner() {
        val category = arrayOf(Constant.FLOOR_ALL, Constant.FLOOR_11, Constant.FLOOR_10, Constant.FLOOR_9, Constant.FLOOR_8, Constant.FLOOR_7)
        view.onShowFloorSpinner(category)
    }

}