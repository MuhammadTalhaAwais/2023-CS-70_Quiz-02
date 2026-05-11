package com.example.quiz2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ComplaintListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var textEmpty: TextView
    private lateinit var layoutEmpty: LinearLayout
    private lateinit var adapter: ComplaintAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_list)

        supportActionBar?.title = "All Complaints"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        textEmpty = findViewById(R.id.textEmpty)
        layoutEmpty = findViewById(R.id.layoutEmpty)

        adapter = ComplaintAdapter(emptyList()) { complaint ->
            val intent = Intent(this, ComplaintDetailActivity::class.java)
            intent.putExtra("complaint_id", complaint.id)
            intent.putExtra("student_name", complaint.studentName)
            intent.putExtra("roll_number", complaint.rollNumber)
            intent.putExtra("phone_number", complaint.phoneNumber)
            intent.putExtra("department", complaint.department)
            intent.putExtra("complaint_title", complaint.complaintTitle)
            intent.putExtra("category", complaint.category)
            intent.putExtra("priority", complaint.priority)
            intent.putExtra("description", complaint.description)
            intent.putExtra("status", complaint.status)
            intent.putExtra("timestamp", complaint.timestamp)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        loadComplaints()
    }

    private fun loadComplaints() {
        FirebaseHelper.getAllComplaints(
            onResult = { complaints ->
                if (complaints.isEmpty()) {
                    recyclerView.visibility = View.GONE
                    layoutEmpty.visibility = View.VISIBLE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    layoutEmpty.visibility = View.GONE
                    adapter.updateList(complaints)
                }
            },
            onFailure = {
                recyclerView.visibility = View.GONE
                layoutEmpty.visibility = View.VISIBLE
                textEmpty.text = "Failed to load complaints"
            }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}