package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.addroom.AddRoomActivity
import com.example.meetingroombookingapp.bytime.BookByTimeActivity
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.mybooking.MyBookingActivity
import com.example.meetingroombookingapp.selectmeetingroom.SelectRoomActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

        val name = sp.getString(Constant.PREF_USER_NAME, null)
        val phone = sp.getString(Constant.PREF_USER_PHONE, null)
        val team = sp.getString(Constant.PREF_USER_TEAM, null)

        bt_logout.text = Html.fromHtml(Constant.HTML_LOGOUT)

        tv_show_user_name.text = Constant.TEXT_HI + name
        tv_show_user_phone.text = Constant.TEXT_TEL + phone
        tv_show_user_team.text =  Constant.TEXT_TEAM + team

        bt_room.setOnClickListener {
            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra(Constant.EXTRA_SHOW, Constant.EXTRA_SHOW_ROOMALL)
            startActivity(intent)
        }

        bt_add.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }

        bt_time.setOnClickListener {
            val intent = Intent(this, BookByTimeActivity::class.java)
            startActivity(intent)
        }

        bt_view_room.setOnClickListener {
            val intent = Intent(this, MyBookingActivity::class.java)
            startActivity(intent)
        }

        bt_logout.setOnClickListener {

            val builder = AlertDialog.Builder(this)

            builder.setTitle(Constant.TEXT_CONFIRM_LOGOUT)

            builder.setPositiveButton(Constant.TEXT_LOGOUT) { _, _ ->

                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                val editor = sp.edit()
                editor.putString(Constant.PREF_USER_NAME, null)
                editor.putString(Constant.PREF_USER_PHONE, null)
                editor.putString(Constant.PREF_USER_TEAM, null)
                editor.apply()

                startActivity(intent)
                finish()

            }

            builder.setNegativeButton(Constant.TEXT_NO) { _, _ -> }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        }
        

        // Toast.makeText(this, "www", Toast.LENGTH_SHORT).show()
        // Log.d("TAG", "-------------------000000000000----------------------------")
    }
}
