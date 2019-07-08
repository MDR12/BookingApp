package com.example.meetingroombookingapp.userinfo

import com.example.meetingroombookingapp.model.BookingDataModel
import com.google.firebase.firestore.FirebaseFirestore

class AddBookingPresenter(private val view: AddBookingContract.View) : AddBookingContract.Presenter {

    var db = FirebaseFirestore.getInstance()

    override fun addBookingToDataBase(allData: BookingDataModel) {

        db.collection("BookingModel")
                .add(allData)
                .addOnSuccessListener { documentReference ->
                    view.onShowSuccess()
                }
                .addOnFailureListener {
                    view.onShowFail()
                }

    }
}