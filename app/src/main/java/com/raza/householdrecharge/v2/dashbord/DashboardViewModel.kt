package com.raza.householdrecharge.v2.dashbord

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.BaseViewModel

class DashboardViewModel : BaseViewModel() {
    var items by mutableStateOf<List<Member>>(emptyList())

    val householdId = "11001"

    fun onAddMember(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        val member = Member(
            id = 1002,
            name = "test2",
            mobileNumber = "9886198861",
            planDurationDays = 31,
            planExpiryDate = null,
            lastRechargeDate = null
        )

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .collection("members")
            .add(member)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }

    fun loadMembers(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .collection("members")
            .get()
            .addOnSuccessListener { result ->
                val members = result.documents.mapNotNull { document ->
                    document.toObject(Member::class.java)
                }

                items = members
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}