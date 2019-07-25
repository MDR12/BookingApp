package com.example.meetingroombookingapp.di

import com.example.meetingroombookingapp.repo.RoomRepo
import com.example.meetingroombookingapp.repo.RoomRepoImpl
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomContract
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomPresenter
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module


val applicationModule = module {

    factory { SelectRoomPresenter(get()) as SelectRoomContract.Presenter }

    single<RoomRepo> { RoomRepoImpl(FirebaseFirestore.getInstance())}
}

val myApp = listOf(applicationModule)