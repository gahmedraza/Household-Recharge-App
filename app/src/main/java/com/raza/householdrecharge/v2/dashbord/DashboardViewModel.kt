package com.raza.householdrecharge.v2.dashbord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.BaseViewModel

class DashboardViewModel : BaseViewModel() {
    var members by mutableStateOf<List<Member>>(emptyList())

    fun loadMembers(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(household.id ?: householdId)
            .collection("members")
            .get()
            .addOnSuccessListener { result ->
                val members = result.documents.mapNotNull { document ->
                    document.toObject(Member::class.java)
                }

                this.members = members

                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}