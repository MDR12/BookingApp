package com.example.meetingroombookingapp.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
class MainEmptyActivity : AppCompatActivity() {

    private val PREFNAME = "MyPreferences"

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp: SharedPreferences = this.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE)

            val userName = sp.getString("user_name", null)
            val userPhone = sp.getString("user_phone", null)

        val activityIntent: Intent

        // go straight to main if a token is stored
        activityIntent = if (userName != null && userPhone != null) {
            Intent(this, HomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }

}