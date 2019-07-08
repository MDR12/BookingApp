package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.common.Constant.TEXT_FILL_ALL_INFO
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sp = getSharedPreferences(Constant.PREF_NAME , Context.MODE_PRIVATE)

        val name = sp.getString(Constant.PREF_USER_NAME, null)
        val phone = sp.getString(Constant.PREF_USER_PHONE, null)
        val team = sp.getString(Constant.PREF_USER_TEAM, null)

        edt_user_name.setText(name)
        edt_user_phone.setText(phone)
        edt_user_team.setText(team)

        bt_register.setOnClickListener {

            if (edt_user_name.text.isNotEmpty() && edt_user_phone.text.isNotEmpty() && edt_user_team.text.isNotEmpty()) {

                val editor = sp.edit()
                editor.putString(Constant.PREF_USER_NAME, edt_user_name.text.toString())
                editor.putString(Constant.PREF_USER_PHONE, edt_user_phone.text.toString())
                editor.putString(Constant.PREF_USER_TEAM, edt_user_team.text.toString())
                editor.apply()

                val i = Intent(this, HomeActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
                finish()

            }else{
                Toast.makeText(this, TEXT_FILL_ALL_INFO, Toast.LENGTH_SHORT).show()
            }

        }

    }
}
