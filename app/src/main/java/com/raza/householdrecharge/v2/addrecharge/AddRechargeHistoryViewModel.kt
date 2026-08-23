package com.raza.householdrecharge.v2.addrecharge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory

class AddRechargeHistoryViewModel : BaseViewModel() {
    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var rechargedBy by mutableStateOf("")

    fun addRechargeHistory(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        val rechargeHistory = RechargeHistory(
            amount = amount,
            date = date,
            rechargedBy = rechargedBy
        )

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .collection("members")
            .document("1002")
            .collection("recharges")
            .add(rechargeHistory)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener {
                onFailure(it.message)
            }
    }

}