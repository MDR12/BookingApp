package com.example.meetingroombookingapp.mybooking

import com.example.meetingroombookingapp.repo.MyBookingRepo

class MyBookingPresenter(private val repo: MyBookingRepo) : MyBookingContract.Presenter {
    private var view: MyBookingContract.View? = null

    override fun subscribe(view: MyBookingContract.View) {
        this.view = view
    }

    override fun unSubscribe() {
        view = null
    }

    override fun onGetMyBooking(userName: String, userPhone: String) {
        repo.getMyBooking(
            userName,
            userPhone,
            onSuccess = { myBookingList ->
                view?.onShowMyBooking(myBookingList)
            },
            onFail = {

            })
    }

    override fun deleteBooking(id: String?, groupId: Int) {
        repo.deleteBooking(
            id,
            groupId,
            onSuccess = { deleteID ->
                view?.onDeleteOK(deleteID)
            }, onFail = {

            })
    }

}