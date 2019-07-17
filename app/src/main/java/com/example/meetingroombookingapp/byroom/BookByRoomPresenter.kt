package com.example.meetingroombookingapp.byroom

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.CheckboxAdapterDataModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class BookByRoomPresenter(private val view: BookByRoomContract.View) : BookByRoomContract.Presenter {
    private val db = FirebaseFirestore.getInstance()
    private val queryBooking = db.collection(Constant.FIREBASE_COLLECTION_BOOKING)

    override fun fetchTimeCheckBox(
        timeList: Array<String>,
        dateFormat: Date,
        roomId: String?
    ) {

        val bookingList = mutableListOf<BookingModel>()

        queryBooking.get()
            .addOnSuccessListener {
                for (doc in it.documents) {
                    val book = doc.toObject(BookingModel::class.java)
                    book?.id = doc.id
                    if (book != null) {
                        bookingList.add(book)
                    }
                }

                val checkTimeDate =
                    bookingList.filter { it.date == dateFormat && it.room_id == roomId } as MutableList<BookingModel>
                val timeCheckboxListData = mutableListOf<CheckboxAdapterDataModel>()

                if (checkTimeDate.isNotEmpty()) {

                    //create checkbox data model
                    for (i in 0 until timeList.size) {

                        val hasDateTimeBooking = checkTimeDate.filter { it.time_booking == i }

                        if (hasDateTimeBooking.isNotEmpty()) {

                            hasDateTimeBooking.size

                            timeCheckboxListData.add(
                                CheckboxAdapterDataModel(
                                    timeList[i],
                                    hasDateTimeBooking[0].user_name + Constant.TEXT_SPACE_ONE + Constant.TEXT_V1 + hasDateTimeBooking[0].user_team + Constant.TEXT_V2 ,
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
                    view.onShowListCheckBox(timeCheckboxListData)

                } else {
                    for (i in 0 until timeList.size) {
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
                    view.onShowListCheckBox(timeCheckboxListData)
                }

            }
    }

    override fun addBookingToDataBase(allData: MutableList<BookingDataModel>) {
          for (i in allData){
            db.collection(Constant.FIREBASE_COLLECTION_BOOKING)
                    .add(i)
          }
        view.onShowSuccess()
    }

}
