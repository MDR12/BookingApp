package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val PREFNAME = "MyPreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sp = getSharedPreferences(PREFNAME , Context.MODE_PRIVATE)

        bt_register.setOnClickListener {

            val name = edt_user_name.text.toString()
            val phone = edt_user_phone.text.toString()
            val team = edt_user_team.text.toString()

            if (name.isNotEmpty() && phone.isNotEmpty() && team.isNotEmpty()){

                val editor = sp.edit()
                editor.putString("user_name", edt_user_name.text.toString())
                editor.putString("user_phone", edt_user_phone.text.toString())
                editor.apply()

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "please fill all information", Toast.LENGTH_SHORT).show()
            }

        }

    }
}
