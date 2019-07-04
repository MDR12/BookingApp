package com.example.meetingroombookingapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.addroom.AddRoomActivity
import com.example.meetingroombookingapp.selectbyroom.SelectRoomActivity
import com.example.meetingroombookingapp.selectbytime.SelectDateTimeActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
