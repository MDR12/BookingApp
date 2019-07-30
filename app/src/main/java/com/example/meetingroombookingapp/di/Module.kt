package com.example.meetingroombookingapp.di

import com.example.meetingroombookingapp.mybooking.MyBookingContract
import com.example.meetingroombookingapp.mybooking.MyBookingPresenter
import com.example.meetingroombookingapp.repo.MyBookingRepo
import com.example.meetingroombookingapp.repo.MyBookingRepoImpl
import com.example.meetingroombookingapp.repo.RoomRepoImpl
import com.example.meetingroombookingapp.repo.SelectRoomRepo
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomContract
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomPresenter
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

val applicationModule = module {
    factory { SelectRoomPresenter(get()) as SelectRoomContract.Presenter }
    factory { MyBookingPresenter(get()) as MyBookingContract.Presenter }

    single<SelectRoomRepo> { RoomRepoImpl(FirebaseFirestore.getInstance()) }
    single<MyBookingRepo> { MyBookingRepoImpl(FirebaseFirestore.getInstance()) }
}

val myApp = listOf(applicationModule)