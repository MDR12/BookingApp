package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
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

        btn_logout.text = HtmlCompat.fromHtml(Constant.HTML_LOGOUT, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val strName = Constant.TEXT_HI + name
        tv_showUserName.text = strName

        val strPhone = Constant.TEXT_TEL + phone
        tv_showUserPhone.text = strPhone

        val strTeam = Constant.TEXT_TEAM + team
        tv_showUserTeam.text =  strTeam

        btn_byRoom.setOnClickListener {
            val intent = Intent(this, SelectRoomActivity::class.java)
            intent.putExtra(Constant.EXTRA_SHOW, Constant.EXTRA_SHOW_ROOMALL)
            startActivity(intent)
        }

        btn_add.setOnClickListener {
            val intent = Intent(this, AddRoomActivity::class.java)
            startActivity(intent)
        }

        btn_byTime.setOnClickListener {
            val intent = Intent(this, BookByTimeActivity::class.java)
            startActivity(intent)
        }

        btn_viewRoom.setOnClickListener {
            val intent = Intent(this, MyBookingActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {

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
    }
}
