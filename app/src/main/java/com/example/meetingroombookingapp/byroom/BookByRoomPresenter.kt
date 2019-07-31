package com.example.meetingroombookingapp.byroom

import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.repo.BookByRoomRepo
import java.util.*

class BookByRoomPresenter(private val repo: BookByRoomRepo) : BookByRoomContract.Presenter {
    private var view: BookByRoomContract.View? = null

    override fun subscribe(view: BookByRoomContract.View) {
        this.view = view
    }

    override fun unSubscribe() {
        view = null
    }

    override fun fetchTimeCheckBox(
        timeList: Array<String>,
        dateFormat: Date,
        roomId: String?
    ) {
        repo.fetchTimeCheckBox(
            timeList,
            dateFormat,
            roomId,
            onSuccess = { timeCheckboxListData ->
                view?.onShowListCheckBox(timeCheckboxListData)
            },
            onFail = { })

    }

    override fun addBookingToDataBase(allData: MutableList<BookingDataModel>) {
        repo.addBookingToDataBase(
            allData,
            onSuccess = {
                view?.onShowSuccess()
            },
            onFail = {

            })
    }

}
