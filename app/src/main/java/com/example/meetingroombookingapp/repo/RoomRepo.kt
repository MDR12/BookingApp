package com.example.meetingroombookingapp.repo

import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.RoomModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

interface RoomRepo{
    fun getRoom(
        onSuccess:(MutableList<RoomModel>) -> Unit,
        onFail:() -> Unit
    )
}

class RoomRepoImpl(private val firestore: FirebaseFirestore): RoomRepo{
    override fun getRoom(onSuccess: (MutableList<RoomModel>) -> Unit, onFail: () -> Unit) {
        val roomList = mutableListOf<RoomModel>()

        firestore.collection(Constant.FIREBASE_COLLECTION_MEETINGROOM)
            .orderBy( Constant.FIREBASE_NAME, Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                for (doc in it.documents) {
                    val room = doc.toObject(RoomModel::class.java)
                    room?.id = doc.id
                    if (room != null) {
                        roomList.add(room)
                    }
                }
                onSuccess.invoke(roomList)
            }
    }
}