package com.example.quiz2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editStudentName: EditText
    private lateinit var editRollNumber: EditText
    private lateinit var editPhoneNumber: EditText
    private lateinit var editDepartment: EditText
    private lateinit var editComplaintTitle: EditText
    private lateinit var editDescription: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerPriority: Spinner
    private lateinit var buttonSubmit: Button
    private lateinit var buttonViewComplaints: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editStudentName = findViewById(R.id.editStudentName)
        editRollNumber = findViewById(R.id.editRollNumber)
        editPhoneNumber = findViewById(R.id.editPhoneNumber)
        editDepartment = findViewById(R.id.editDepartment)
        editComplaintTitle = findViewById(R.id.editComplaintTitle)
        editDescription = findViewById(R.id.editDescription)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        spinnerPriority = findViewById(R.id.spinnerPriority)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        buttonViewComplaints = findViewById(R.id.buttonViewComplaints)
        progressBar = findViewById(R.id.progressBar)

        setupSpinners()

        buttonSubmit.setOnClickListener {
            if (validateForm()) {
                submitComplaint()
            }
        }

        buttonViewComplaints.setOnClickListener {
            startActivity(Intent(this, ComplaintListActivity::class.java))
        }
    }

    private fun setupSpinners() {
        val categories = listOf(
            "Select Category",
            "IT",
            "Library",
            "Transport",
            "Hostel",
            "Accounts",
            "Examination",
            "Cafeteria",
            "Administration"
        )

        val priorities = listOf(
            "Select Priority",
            "Low",
            "Medium",
            "High",
            "Urgent"
        )

        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = categoryAdapter

        val priorityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorities)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = priorityAdapter
    }

    private fun validateForm(): Boolean {
        val studentName = editStudentName.text.toString().trim()
        val rollNumber = editRollNumber.text.toString().trim()
        val phoneNumber = editPhoneNumber.text.toString().trim()
        val department = editDepartment.text.toString().trim()
        val complaintTitle = editComplaintTitle.text.toString().trim()
        val description = editDescription.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val priority = spinnerPriority.selectedItem.toString()

        if (studentName.isEmpty()) {
            editStudentName.error = "Name is required"
            editStudentName.requestFocus()
            return false
        }

        if (studentName.length < 3) {
            editStudentName.error = "Name must be at least 3 characters"
            editStudentName.requestFocus()
            return false
        }

        if (!studentName.matches(Regex("^[a-zA-Z ]+$"))) {
            editStudentName.error = "Name can only contain letters"
            editStudentName.requestFocus()
            return false
        }

        if (rollNumber.isEmpty()) {
            editRollNumber.error = "Roll number is required"
            editRollNumber.requestFocus()
            return false
        }

        if (!rollNumber.matches(Regex("^[A-Za-z0-9\\-/]+$"))) {
            editRollNumber.error = "Enter a valid roll number (e.g. BSCS-101 or 2023-CS-70)"
            editRollNumber.requestFocus()
            return false
        }

        if (phoneNumber.isEmpty()) {
            editPhoneNumber.error = "Phone number is required"
            editPhoneNumber.requestFocus()
            return false
        }

        if (!phoneNumber.matches(Regex("^03[0-9]{9}$"))) {
            editPhoneNumber.error = "Enter a valid Pakistani number (e.g. 03001234567)"
            editPhoneNumber.requestFocus()
            return false
        }

        if (department.isEmpty()) {
            editDepartment.error = "Department is required"
            editDepartment.requestFocus()
            return false
        }

        if (department.length < 2) {
            editDepartment.error = "Enter a valid department name"
            editDepartment.requestFocus()
            return false
        }

        if (complaintTitle.isEmpty()) {
            editComplaintTitle.error = "Complaint title is required"
            editComplaintTitle.requestFocus()
            return false
        }

        if (complaintTitle.length < 5) {
            editComplaintTitle.error = "Title must be at least 5 characters"
            editComplaintTitle.requestFocus()
            return false
        }

        if (category == "Select Category") {
            Toast.makeText(this, "Please select a complaint category", Toast.LENGTH_SHORT).show()
            return false
        }

        if (priority == "Select Priority") {
            Toast.makeText(this, "Please select a priority level", Toast.LENGTH_SHORT).show()
            return false
        }

        if (description.isEmpty()) {
            editDescription.error = "Description is required"
            editDescription.requestFocus()
            return false
        }

        if (description.length < 20) {
            editDescription.error = "Please describe your complaint in at least 20 characters"
            editDescription.requestFocus()
            return false
        }

        return true
    }

    private fun submitComplaint() {
        val studentName = editStudentName.text.toString().trim()
        val rollNumber = editRollNumber.text.toString().trim()
        val phoneNumber = editPhoneNumber.text.toString().trim()
        val department = editDepartment.text.toString().trim()
        val complaintTitle = editComplaintTitle.text.toString().trim()
        val description = editDescription.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()
        val priority = spinnerPriority.selectedItem.toString()

        val complaint = Complaint(
            studentName = studentName,
            rollNumber = rollNumber,
            phoneNumber = phoneNumber,
            department = department,
            complaintTitle = complaintTitle,
            category = category,
            priority = priority,
            description = description,
            status = "Pending"
        )

        progressBar.visibility = View.VISIBLE
        buttonSubmit.isEnabled = false

        FirebaseHelper.saveComplaint(
            complaint,
            onSuccess = {
                progressBar.visibility = View.GONE
                buttonSubmit.isEnabled = true
                Toast.makeText(this, "Complaint submitted successfully!", Toast.LENGTH_LONG).show()
                clearForm()
            },
            onFailure = { error ->
                progressBar.visibility = View.GONE
                buttonSubmit.isEnabled = true
                Toast.makeText(this, "Failed to submit: $error", Toast.LENGTH_LONG).show()
            }
        )
    }

    private fun clearForm() {
        editStudentName.setText("")
        editRollNumber.setText("")
        editPhoneNumber.setText("")
        editDepartment.setText("")
        editComplaintTitle.setText("")
        editDescription.setText("")
        spinnerCategory.setSelection(0)
        spinnerPriority.setSelection(0)
        editStudentName.requestFocus()
    }
}