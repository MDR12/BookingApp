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

        bt_register.setOnClickListener {

            val name = edt_user_name.text.toString()
            val phone = edt_user_phone.text.toString()
            val team = edt_user_team.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && team.isNotEmpty()){

                val editor = sp.edit()
                editor.putString(Constant.PREF_USER_NAME, edt_user_name.text.toString())
                editor.putString(Constant.PREF_USER_PHONE, edt_user_phone.text.toString())
                editor.apply()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, TEXT_FILL_ALL_INFO, Toast.LENGTH_SHORT).show()
            }

        }

    }
}
