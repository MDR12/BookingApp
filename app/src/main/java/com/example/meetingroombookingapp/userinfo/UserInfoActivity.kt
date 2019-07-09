package com.example.meetingroombookingapp.userinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.home.HomeActivity
import kotlinx.android.synthetic.main.activity_user_info.*
import java.text.SimpleDateFormat


class UserInfoActivity : AppCompatActivity(), AddBookingContract.View {

    private val presenter: AddBookingContract.Presenter = AddBookingPresenter(this)

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        bt_add_username.setOnClickListener {

            val roomId = sp.getString("room_id", "")
            val roomName = sp.getString("room_name", "")
            val floor = sp.getString("floor", "")
            val userName = sp.getString("user_name", "No Name")
            val userPhone = sp.getString("user_phone", "No Telephone")
            val comment = null
            val status = "B"
            val userCancelName = null
            val userCancelPhone = null

            val datePick = sp.getString("date_pick", "")
            val pickStartTime = sp.getString("pick_start_time", "")
            val pickEndTime = sp.getString("pick_end_time", "")

            val date = SimpleDateFormat("dd-MM-yyyy").parse(datePick)
            val start = "$datePick $pickStartTime"
            val dateTimeStart = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(start)
            val end = "$datePick $pickEndTime"
            val dateTimeEnd = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(end)

//            val allData = BookingDataModel(
//                    roomId,
//                    date,
//                0,
//                    dateTimeStart,
//                    dateTimeEnd,
//                    userName,
//                    userPhone
//            )

            val builder = AlertDialog.Builder(this)

            builder.setTitle("Confirm BookingModel")
            builder.setMessage(
                    "Name: $userName Tel.: $userPhone\n" +
                            "Room: $roomName Floor $floor\n" +
                            "Date: $datePick\n" +
                            "Time: $pickStartTime to $pickEndTime"
            )

            builder.setPositiveButton("YES") { dialog, which ->
                // Do something when user press the positive button
                Toast.makeText(applicationContext, "Adding your time booking", Toast.LENGTH_SHORT).show()

                //presenter.addBookingToDataBase(allData)

                val i = Intent(this, HomeActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
            }

            builder.setNegativeButton("No") { dialog, which -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    override fun onShowSuccess() {
        Toast.makeText(this, "Adding time booking successfully", Toast.LENGTH_SHORT).show()
    }

    override fun onShowFail() {
        Toast.makeText(this, "Error adding time booking", Toast.LENGTH_SHORT).show()
    }

}
