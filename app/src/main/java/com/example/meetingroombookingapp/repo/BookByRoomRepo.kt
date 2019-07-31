package com.example.meetingroombookingapp.repo

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

interface BookByRoomRepo {
    fun addBookingToDataBase(
        allData: MutableList<BookingDataModel>,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    )

    fun fetchTimeCheckBox(
        timeList: Array<String>,
        dateFormat: Date,
        roomId: String?,
        onSuccess: (MutableList<CheckboxAdapterDataModel>) -> Unit,
        onFail: () -> Unit
    )
}

class BookByRoomImpl(private val firestore: FirebaseFirestore) : BookByRoomRepo {
    override fun fetchTimeCheckBox(
        timeList: Array<String>,
        dateFormat: Date,
        roomId: String?,
        onSuccess: (MutableList<CheckboxAdapterDataModel>) -> Unit,
        onFail: () -> Unit
    ) {
        val bookingList = mutableListOf<BookingModel>()

        firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
            .whereEqualTo(Constant.FIREBASE_DATE, dateFormat)
            .whereEqualTo(Constant.FIREBASE_ROOM_ID, roomId)
            .get().addOnSuccessListener {
                for (doc in it.documents) {
                    val book = doc.toObject(BookingModel::class.java)
                    book?.id = doc.id
                    if (book != null) {
                        bookingList.add(book)
                    }
                }
                val timeCheckboxListData = mutableListOf<CheckboxAdapterDataModel>()

                for (i in 0 until timeList.size) {
                    val hasDateTimeBooking = bookingList.filter { timeSlot -> timeSlot.time_booking == i }

                    if (hasDateTimeBooking.isNotEmpty()) {
                        timeCheckboxListData.add(
                            CheckboxAdapterDataModel(
                                timeList[i],
                                hasDateTimeBooking[0].user_name + Constant.TEXT_SPACE_ONE + Constant.TEXT_V1 + hasDateTimeBooking[0].user_team + Constant.TEXT_V2,
                                Constant.TEXT_TEL + hasDateTimeBooking[0].user_phone,
                                hasDateTimeBooking[0].time_booking,
                                Constant.TYPE_BOOKED
                            )
                        )
                    } else {
                        timeCheckboxListData.add(
                            CheckboxAdapterDataModel(
                                timeList[i],
                                null,
                                null,
                                i,
                                Constant.TYPE_AVALIABLE
                            )
                        )
                    }
                }
                onSuccess.invoke(timeCheckboxListData)
            }
    }

    private var onSuccessAddBook: (() -> Unit)? = null
    private var onFailAddBook: (() -> Unit)? = null
    private var books = listOf<BookingDataModel>()
    private var countAddBook = 0
    override fun addBookingToDataBase(
        allData: MutableList<BookingDataModel>,
        onSuccess: () -> Unit,
        onFail: () -> Unit
    ) {
        onSuccessAddBook = onSuccess
        onFailAddBook = onFail
        books = allData
        countAddBook = 0

        loopAddBooking()
    }

    private fun loopAddBooking() {
        if (countAddBook == books.size) {
            onSuccessAddBook?.invoke()
        } else {
            firestore.collection(Constant.FIREBASE_COLLECTION_BOOKING)
                .add(books[countAddBook]).addOnSuccessListener {
                    countAddBook++
                    loopAddBooking()
                }.addOnFailureListener {
                    onFailAddBook?.invoke()
                }
        }
    }

}