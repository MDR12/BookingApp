package com.example.meetingroombookingapp.selectmeetingroom

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingDataModel
import com.example.meetingroombookingapp.model.RoomModel
import com.example.meetingroombookingapp.repo.SelectRoomRepo
import java.util.*

class SelectRoomPresenter(private val repo: SelectRoomRepo) : SelectRoomContract.Presenter {
    private var view: SelectRoomContract.View? = null

    override fun subscribe(view: SelectRoomContract.View) {
        this.view = view
    }

    override fun unSubscribe() {
        view = null
    }

    override fun getRoomAll(){
        repo.getRoomAll(
            onSuccess = { roomList ->
                view?.onGetRoomDone(roomList)
                view?.onShowRoomList(roomList)
            },
            onFail = {}
        )
    }

    override fun getRoomByTime(date: String, timeStart: Int, timeEnd: Int) {
        repo.getRoomByTime(
            date,
            timeStart,
            timeEnd,
            onSuccess = {
                    RoomList ->
                view?.onGetRoomDone(RoomList)
                view?.onShowRoomList(RoomList)
            },
            onFail = {}
        )
    }

    override fun addBooking(dateFormat: Date,
                            roomId: String?,
                            floor: Int,
                            roomName: String?,
                            userName: String?,
                            userPhone: String?,
                            userTeam: String?,
                            timeStart: Int,
                            timeEnd: Int ) {

        val data = mutableListOf<BookingDataModel>()
        val arrTimeSlot = setArrTimeSlot(timeStart, timeEnd)

        for (i in 0 until arrTimeSlot.size) {
            data.add(
                i, BookingDataModel(
                    dateFormat,
                    roomId,
                    floor,
                    roomName,
                    userName,
                    userPhone,
                    userTeam,
                    arrTimeSlot[i],
                    Constant.ARR_TIME_ALL_TEXT[arrTimeSlot[i]]
                )
            )
        }

        repo.addBooking(data,
            onSuccess = {
                view?.onShowSuccess()
            },
            onFail = {
                view?.onShowFail()
            })
    }

    override fun setRoomList(floorSelect: String, roomList: MutableList<RoomModel>) {
        if (floorSelect == Constant.FLOOR_ALL){
            view?.onShowRoomList(roomList)
        } else {
            val newList = roomList.filter { it.floor == floorSelect.toInt() }
            view?.onShowRoomList(newList as MutableList<RoomModel>)
        }
    }

    override fun setFloorSpinner() {
        val category = arrayOf(Constant.FLOOR_ALL, Constant.FLOOR_11, Constant.FLOOR_10, Constant.FLOOR_9, Constant.FLOOR_8, Constant.FLOOR_7)
        view?.onShowFloorSpinner(category)
    }

    private fun setArrTimeSlot(timeStart: Int, timeEnd: Int): MutableList<Int> {
        val hour = timeEnd - timeStart
        var start = timeStart
        val arrTimeSlot = mutableListOf<Int>()

        for (i in 0 until hour) {
            arrTimeSlot.add(start++)
        }

        return arrTimeSlot
    }
}