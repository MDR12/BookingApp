package com.example.meetingroombookingapp.userinfo

import com.example.meetingroombookingapp.model.BookingModel
import com.google.firebase.firestore.FirebaseFirestore

class AddBookingPresenter(private val view: AddBookingContract.View) : AddBookingContract.Presenter {

    var db = FirebaseFirestore.getInstance()

    override fun addBookingToDataBase(allData: BookingModel) {

        db.collection("Booking")
                .add(allData)
                .addOnSuccessListener { documentReference ->
                    view.onShowSuccess()
                }
                .addOnFailureListener {
                    view.onShowFail()
                }

    }
}