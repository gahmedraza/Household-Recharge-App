package com.raza.householdrecharge.v2.addrecharge

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory

class AddRechargeHistoryViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var rechargedBy by mutableStateOf("")

    fun addRechargeHistory(
        memberId: Long,
        mobileNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        val rechargeHistory = RechargeHistory(
            amount = amount,
            date = date,
            rechargedBy = rechargedBy
        )

        val memberNotFound = memberId==0L

        if(memberNotFound) {
            onFailure("No member found")
        }

        val mobileNumberNotFound = mobileNumber.isEmpty()

        if(mobileNumberNotFound) {
            onFailure("No mobile number found")
        }

        FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .collection("members")
            .document(memberId.toString())
            .collection("phoneNumbers")
            .document(mobileNumber)
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