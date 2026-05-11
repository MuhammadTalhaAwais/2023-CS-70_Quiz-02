package com.example.quiz2

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComplaintDetailActivity : AppCompatActivity() {

    private lateinit var textStudentName: TextView
    private lateinit var textRollNumber: TextView
    private lateinit var textPhoneNumber: TextView
    private lateinit var textDepartment: TextView
    private lateinit var textComplaintTitle: TextView
    private lateinit var textCategory: TextView
    private lateinit var textPriority: TextView
    private lateinit var textStatus: TextView
    private lateinit var textDescription: TextView
    private lateinit var textTimestamp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_detail)

        supportActionBar?.title = "Complaint Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        textStudentName = findViewById(R.id.textDetailStudentName)
        textRollNumber = findViewById(R.id.textDetailRollNumber)
        textPhoneNumber = findViewById(R.id.textDetailPhoneNumber)
        textDepartment = findViewById(R.id.textDetailDepartment)
        textComplaintTitle = findViewById(R.id.textDetailComplaintTitle)
        textCategory = findViewById(R.id.textDetailCategory)
        textPriority = findViewById(R.id.textDetailPriority)
        textStatus = findViewById(R.id.textDetailStatus)
        textDescription = findViewById(R.id.textDetailDescription)
        textTimestamp = findViewById(R.id.textDetailTimestamp)

        val studentName = intent.getStringExtra("student_name") ?: ""
        val rollNumber = intent.getStringExtra("roll_number") ?: ""
        val phoneNumber = intent.getStringExtra("phone_number") ?: ""
        val department = intent.getStringExtra("department") ?: ""
        val complaintTitle = intent.getStringExtra("complaint_title") ?: ""
        val category = intent.getStringExtra("category") ?: ""
        val priority = intent.getStringExtra("priority") ?: ""
        val status = intent.getStringExtra("status") ?: "Pending"
        val description = intent.getStringExtra("description") ?: ""
        val timestamp = intent.getLongExtra("timestamp", 0L)

        textStudentName.text = studentName
        textRollNumber.text = rollNumber
        textPhoneNumber.text = phoneNumber
        textDepartment.text = department
        textComplaintTitle.text = complaintTitle
        textCategory.text = category
        textPriority.text = priority
        textStatus.text = status
        textDescription.text = description

        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        textTimestamp.text = sdf.format(Date(timestamp))

        when (priority) {
            "Urgent" -> textPriority.setBackgroundColor(Color.parseColor("#E74C3C"))
            "High" -> textPriority.setBackgroundColor(Color.parseColor("#E67E22"))
            "Medium" -> textPriority.setBackgroundColor(Color.parseColor("#F39C12"))
            "Low" -> textPriority.setBackgroundColor(Color.parseColor("#27AE60"))
            else -> textPriority.setBackgroundColor(Color.parseColor("#95A5A6"))
        }

        when (status) {
            "Pending" -> textStatus.setBackgroundColor(Color.parseColor("#E74C3C"))
            "Resolved" -> textStatus.setBackgroundColor(Color.parseColor("#27AE60"))
            else -> textStatus.setBackgroundColor(Color.parseColor("#95A5A6"))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}