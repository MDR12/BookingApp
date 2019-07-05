package com.example.meetingroombookingapp.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.addroom.AddRoomActivity
import com.example.meetingroombookingapp.selectbyroom.SelectRoomActivity
import com.example.meetingroombookingapp.selectbytime.SelectDateTimeActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val PREFNAME = "MyPreferences"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sp: SharedPreferences = this.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE)

        val name = sp.getString("user_name", "")
        val phone = sp.getString("user_phone", "")

        tv_show_user_name.text = "Hi, $name"
        tv_show_user_phone.text = "Tel. $phone"

        bt_room.setOnClickListener {
            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra("show", "roomAll")
            startActivity(intent)
        }

        bt_add.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }

        bt_time.setOnClickListener {
            val intent = Intent(this, SelectDateTimeActivity::class.java)
            startActivity(intent)
        }

        bt_view_room.setOnClickListener {
//            val intent = Intent(this, ViewRoomActivity::class.java)
//            startActivity(intent)
        }
        

        // Toast.makeText(this@CalendarActivity, msg, Toast.LENGTH_SHORT).show()
        // Log.d("TAG", "-------------------000000000000----------------------------")
    }
}
