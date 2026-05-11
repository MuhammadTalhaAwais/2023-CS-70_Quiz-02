package com.example.quiz2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ComplaintAdapter(
    private var complaints: List<Complaint>,
    private val onClick: (Complaint) -> Unit
) : RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    class ComplaintViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardComplaint)
        val textTitle: TextView = view.findViewById(R.id.textTitle)
        val textStudentName: TextView = view.findViewById(R.id.textStudentName)
        val textRollNumber: TextView = view.findViewById(R.id.textRollNumber)
        val textCategory: TextView = view.findViewById(R.id.textCategory)
        val textPriority: TextView = view.findViewById(R.id.textPriority)
        val textStatus: TextView = view.findViewById(R.id.textStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val complaint = complaints[position]

        holder.textTitle.text = complaint.complaintTitle
        holder.textStudentName.text = complaint.studentName
        holder.textRollNumber.text = complaint.rollNumber
        holder.textCategory.text = complaint.category
        holder.textPriority.text = complaint.priority
        holder.textStatus.text = complaint.status

        when (complaint.priority) {
            "Urgent" -> holder.textPriority.setBackgroundColor(Color.parseColor("#E74C3C"))
            "High" -> holder.textPriority.setBackgroundColor(Color.parseColor("#E67E22"))
            "Medium" -> holder.textPriority.setBackgroundColor(Color.parseColor("#F39C12"))
            "Low" -> holder.textPriority.setBackgroundColor(Color.parseColor("#27AE60"))
            else -> holder.textPriority.setBackgroundColor(Color.parseColor("#95A5A6"))
        }

        when (complaint.status) {
            "Pending" -> holder.textStatus.setBackgroundColor(Color.parseColor("#E74C3C"))
            "Resolved" -> holder.textStatus.setBackgroundColor(Color.parseColor("#27AE60"))
            else -> holder.textStatus.setBackgroundColor(Color.parseColor("#95A5A6"))
        }

        holder.cardView.setOnClickListener { onClick(complaint) }
    }

    override fun getItemCount() = complaints.size

    fun updateList(newList: List<Complaint>) {
        complaints = newList
        notifyDataSetChanged()
    }
}