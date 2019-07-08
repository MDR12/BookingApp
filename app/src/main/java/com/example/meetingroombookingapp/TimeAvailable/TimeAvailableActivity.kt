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
import com.example.meetingroombookingapp.common.Constant
import com.example.meetingroombookingapp.model.BookingModel
import com.example.meetingroombookingapp.model.TimeInt
import com.example.meetingroombookingapp.userinfo.UserInfoActivity
import kotlinx.android.synthetic.main.activity_time_available.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TimeAvailableActivity : AppCompatActivity(), TimeAvailableContract.View {

    private val presenter: TimeAvailableContract.Presenter = TimeAvailablePresenter(this)

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_available)

        val sp: SharedPreferences = this.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()

        val roomId = sp.getString(Constant.PREF_ROOM_ID, "")
        val datePick = sp.getString(Constant.PREF_DATE_PICK, "")
        val date = SimpleDateFormat(Constant.FORMAT_DATE).parse(datePick)

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
                val start = SimpleDateFormat(Constant.FORNAT_TIME).format(c.time)
                pick_start_time.text = start

                startTimeInt = TimeInt(h, m)
                startTime = c.time

                val mStart = "$datePick $start"
                startTime = SimpleDateFormat(Constant.FORMAT_DATE_TIME).parse(mStart)

                editor.putString(Constant.PREF_PICK_START_TIME, start)
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
                val end = SimpleDateFormat(Constant.FORNAT_TIME).format(c.time)
                pick_end_time.text = end

                endTimeInt = TimeInt(h, m)
                endTime = c.time

                val mEnd = "$datePick $end"
                endTime = SimpleDateFormat(Constant.FORMAT_DATE_TIME).parse(mEnd)

                editor.putString(Constant.PREF_PICK_END_TIME, end)
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
                        makeText(this@TimeAvailableActivity, Constant.TEXT_ROOM_USED, LENGTH_SHORT).show()
                    }

                } else {
                    makeText(this@TimeAvailableActivity, Constant.TEXT_TIME_PARADOX, LENGTH_SHORT).show()
                }

            } else {
                makeText(this@TimeAvailableActivity, Constant.TEXT_TIME_NOT_PICK, LENGTH_SHORT).show()
            }
        }
    }

    override fun onShowTimeList(data: MutableList<BookingModel>) {

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
