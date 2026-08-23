package com.raza.householdrecharge.v2.rechargehistory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel

class RechargeHistoryViewModel : BaseViewModel() {

    var items by mutableStateOf<List<RechargeHistory>>(emptyList())

    fun loadRechargeHistory(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .collection("members")
            .document(memberId)
            .collection("recharges")
            .get()
            .addOnSuccessListener { result ->
                val members = result.documents.mapNotNull { document ->
                    document.toObject(RechargeHistory::class.java)
                }

                items = members
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }
}