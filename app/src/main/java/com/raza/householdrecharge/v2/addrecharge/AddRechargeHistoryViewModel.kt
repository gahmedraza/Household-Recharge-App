package com.raza.householdrecharge.v2.addrecharge

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddRechargeHistoryViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var rechargedBy by mutableStateOf("")

    fun addRechargeHistory(
        memberId: String,
        mobileNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val rechargeHistory = RechargeHistory(
                amount = amount,
                date = date,
                rechargedBy = rechargedBy
            )

            val userId = sessionManager.userId.first()

            if(userId.isEmpty()) {
                onFailure("user not found")
                return@launch
            }

            val householdId = sessionManager.householdId.first()

            if(householdId.isEmpty()) {
                onFailure("household not found")
                return@launch
            }

            if(memberId.isNullOrEmpty()) {
                onFailure("No member found")
            }

            Log.d("TAG", "memberId: $memberId")

            val mobileNumberNotFound = mobileNumber.isEmpty()

            if(mobileNumberNotFound) {
                onFailure("No mobile number found")
            }

            FirebaseFirestore
                .getInstance()

                .collection("users")
                .document(userId)

                .collection("households")
                .document(householdId)

                .collection("members")
                .document(memberId.toString())

                .collection("mobileNumbers")
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

}