package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.addroom.AddRoomActivity
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

        tv_show_user_name.text = Constant.TEXT_HI+ name + Constant.TEXT_FROM + team
        tv_show_user_phone.text = Constant.TEXT_TEL + phone

        bt_room.setOnClickListener {
            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra(Constant.EXTRA_SHOW, Constant.EXTRA_SHOW_ROOMALL)
            startActivity(intent)
        }

        bt_add.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }

//        bt_time.setOnClickListener {
//            val intent = Intent(this, SelectDateTimeActivity::class.java)
//            startActivity(intent)
//        }

        bt_view_room.setOnClickListener {
            val intent = Intent(this, MyBookingActivity::class.java)
            startActivity(intent)
        }

        bt_logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        

        // Toast.makeText(this@CalendarActivity, msg, Toast.LENGTH_SHORT).show()
        // Log.d("TAG", "-------------------000000000000----------------------------")
    }
}
