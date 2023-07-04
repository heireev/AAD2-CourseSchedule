package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_course)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]
        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                val message = getString(R.string.input_empty_message)
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        val startTimeButton = findViewById<ImageButton>(R.id.ib_start_time)
        val endTimeButton = findViewById<ImageButton>(R.id.ib_end_time)

        startTimeButton.setOnClickListener { showTimePicker("startTime", it) }
        endTimeButton.setOnClickListener { showTimePicker("endTime", it) }

        val spinnerDay = findViewById<Spinner>(R.id.spinner_day)
        val calendar = Calendar.getInstance()
        val todayIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1
        spinnerDay.setSelection(todayIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                insertCourseHandler()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTimePicker(tag: String, view: View) {
        TimePickerFragment().show(
            supportFragmentManager, tag
        )
        this.view = view
    }

    private fun insertCourseHandler() {
        val courseName = findViewById<TextInputEditText>(R.id.ed_course_name).text.toString().trim()
        val day = findViewById<Spinner>(R.id.spinner_day).selectedItemPosition
        val startTime = findViewById<TextView>(R.id.tv_start_time_value).text.toString().trim()
        val endTime = findViewById<TextView>(R.id.tv_end_time_value).text.toString().trim()
        val lecturer = findViewById<TextInputEditText>(R.id.ed_lecturer).text.toString().trim()
        val note = findViewById<TextInputEditText>(R.id.ed_note).text.toString().trim()
        viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val tvEndTimeValue = findViewById<TextView>(R.id.tv_end_time_value)
        val tvStartTimeValue = findViewById<TextView>(R.id.tv_start_time_value)

        val calender = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (view.id) {
            R.id.ib_start_time -> {
                tvStartTimeValue.text = timeFormat.format(calender.time)
            }

            R.id.ib_end_time -> {
                tvEndTimeValue.text = timeFormat.format(calender.time)
            }
        }
    }
}