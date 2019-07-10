package com.example.meetingroombookingapp.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.common.Constant


class MainEmptyActivity : AppCompatActivity() {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)

            val userName = sp.getString(Constant.PREF_USER_NAME, null)
            val userPhone = sp.getString(Constant.PREF_USER_PHONE, null)

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