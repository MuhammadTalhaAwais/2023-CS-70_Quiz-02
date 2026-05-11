package com.example.quiz2

data class Complaint(
    var id: String = "",
    val studentName: String = "",
    val rollNumber: String = "",
    val phoneNumber: String = "",
    val department: String = "",
    val complaintTitle: String = "",
    val category: String = "",
    val priority: String = "",
    val description: String = "",
    val status: String = "Pending",
    val timestamp: Long = 0L
)