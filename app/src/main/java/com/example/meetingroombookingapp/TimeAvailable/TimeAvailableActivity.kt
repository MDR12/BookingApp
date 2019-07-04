package com.example.meetingroombookingapp.TimeAvailable

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetingroombookingapp.R
import com.example.meetingroombookingapp.model.Booking
import com.example.meetingroombookingapp.model.TimeInt
import com.example.meetingroombookingapp.userinfo.UserInfoActivity
import kotlinx.android.synthetic.main.activity_time_available.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TimeAvailableActivity : AppCompatActivity(), TimeAvailableContract.View {

    private val PREFNAME = "MyPreferences"
    private val presenter: TimeAvailableContract.Presenter = TimeAvailablePresenter(this)

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_available)

        val sp: SharedPreferences = this.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE)
        val editor = sp.edit()

        val roomId = sp.getString("room_id", "")
        val datePick = sp.getString("date_pick", "")
        val date = SimpleDateFormat("dd-MM-yyyy").parse(datePick)

        presenter.fetchTimeBooked(roomId, date)

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        var startTimeInt: TimeInt? = null
        var endTimeInt: TimeInt? = null

        var pickStartCheck: String? = null
        var pickEndCheck: String? = null

        var startTime: Date? = null
        var endTime: Date? = null

        pick_start_time.text = "Start Time"
        pick_end_time.text = "End Time"

        pick_start_time.setOnClickListener {

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)
                c.set(Calendar.SECOND,0)
                val start = SimpleDateFormat("HH:mm").format(c.time)
                pick_start_time.text = start

                startTimeInt = TimeInt(h, m)
                startTime = c.time

                val mStart = "$datePick $start"
                startTime = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(mStart)

                editor.putString("pick_start_time", start)
                pickStartCheck = "ok"
                editor.apply()

            }), hour, minute, true)

            tpd.show()
        }

        pick_end_time.setOnClickListener {

            val tpd = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener(function = { view, h, m ->

                c.set(Calendar.HOUR_OF_DAY, h)
                c.set(Calendar.MINUTE, m)
                c.set(Calendar.SECOND,0)
                val end = SimpleDateFormat("HH:mm").format(c.time)
                pick_end_time.text = end

                endTimeInt = TimeInt(h, m)
                endTime = c.time

                val mEnd = "$datePick $end"
                endTime = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(mEnd)

                editor.putString("pick_end_time", end)
                pickEndCheck = "ok"
                editor.apply()

            }), hour + 1, minute, true)

            tpd.show()
        }

        bt_book.setOnClickListener {

            if (pickStartCheck != null && pickEndCheck != null) {

                if (presenter.checkTimeNotMinus(startTimeInt, endTimeInt)) {

                    if (presenter.checkTimeAvailable(startTime, endTime)) {

                        makeText(this@TimeAvailableActivity, startTime.toString()+" and "+endTime.toString(), LENGTH_SHORT).show()

                        val intent = Intent(this, UserInfoActivity::class.java)
                        startActivity(intent)

                    } else {
                        makeText(this@TimeAvailableActivity, "Someone used the room at that time", LENGTH_SHORT).show()
                    }

                } else {
                    makeText(this@TimeAvailableActivity, "Time start can't be less than time end", LENGTH_SHORT).show()
                }

            } else {
                makeText(this@TimeAvailableActivity, "Please pick the time", LENGTH_SHORT).show()
            }
        }
    }

    override fun onShowTimeList(data: MutableList<Booking>) {

        val adapt = TimeAvailableAdapter(data)
        tv_text.visibility = View.GONE

        recyclerView_time.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = adapt
        }
    }

    override fun onShowNoListTime() {

        tv_text.visibility = View.VISIBLE
        recyclerView_time.visibility = View.GONE
    }

}
