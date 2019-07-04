package com.example.meetingroombookingapp.addroom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meetingroombookingapp.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_room.*
import kotlinx.android.synthetic.main.activity_main.bt_add

class AddRoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)

        var db = FirebaseFirestore.getInstance()

        bt_add.setOnClickListener {

            val data = AddRoomModel(
                    edt_capacity.text.toString().toInt(),
                    edt_floor.text.toString().toInt(),
                    edt_room_name.text.toString()
            )

            db.collection("MeetingRoom")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(
                        this,
                        "DocumentSnapshot written with ID: ${documentReference.id}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error adding document", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
