package com.raza.householdrecharge.v2.rechargehistory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toString
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RechargeHistoryViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var mobileRechargeHistory by mutableStateOf<List<RechargeHistory>>(emptyList())

    fun loadRechargeHistory(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        memberId: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            val userId = sessionManager.userId.first()

            if(userId.isNullOrEmpty()) {
                onFailure("user not found")
                return@launch
            }

            val householdId = sessionManager.householdId.first()

            if(householdId.isNullOrEmpty()) {
                onFailure("household not found")
                return@launch
            }

            var currentMemberId = memberId

            if (currentMemberId.isNullOrEmpty()) {
                currentMemberId = sessionManager.memberId.first()
            }

            if(currentMemberId.isNullOrEmpty()) {
                onFailure("member not found")
                return@launch
            }

            var currentMobileNumber = mobileNumber

            if(currentMobileNumber.isNullOrEmpty()) {
                currentMobileNumber = sessionManager.mobileNumber.first()
            }

            if(currentMobileNumber.isNullOrEmpty()) {
                onFailure("mobile number not found")
                return@launch
            }

            FirebaseFirestore
                .getInstance()

                .collection("users")
                .document(userId)

                .collection("households")
                .document(householdId)

                .collection("members")
                .document(currentMemberId)

                .collection("mobileNumbers")
                .document(currentMobileNumber)

                .collection("recharges")
                .get()
                .addOnSuccessListener { result ->
                    val members = result.documents.mapNotNull { document ->
                        document.toObject(RechargeHistory::class.java)
                    }

                    mobileRechargeHistory = members
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it.message)
                }
        }
    }
}