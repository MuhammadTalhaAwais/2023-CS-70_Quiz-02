package com.example.quiz2

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseHelper {

    private val database: DatabaseReference =
        FirebaseDatabase.getInstance("https://quiz2app-680dc-default-rtdb.firebaseio.com")
            .getReference("complaints")

    fun saveComplaint(complaint: Complaint, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val id = database.push().key ?: return
        val complaintWithId = complaint.copy(id = id, timestamp = System.currentTimeMillis())
        database.child(id).setValue(complaintWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it.message ?: "Unknown error") }
    }

    fun getAllComplaints(onResult: (List<Complaint>) -> Unit, onFailure: (String) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Complaint>()
                for (child in snapshot.children) {
                    val complaint = child.getValue(Complaint::class.java)
                    if (complaint != null) list.add(complaint)
                }
                list.sortByDescending { it.timestamp }
                onResult(list)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }
        })
    }
}