package com.raza.householdrecharge.v2.addmember

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.MemberDto
import com.raza.householdrecharge.v2.SessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MemberViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun onAddMember(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            val member = MemberDto(
                name = name,
                mobileNumber = mobileNumber,
                planDurationDays = planDurationDays,
                planExpiryDate = planExpiryDate,
                lastRechargeDate = lastRechargeDate
            )

            val householdId = sessionManager.householdId.first()

            if (householdId.isEmpty()) {
                onFailure("household not found")
            }

            FirebaseFirestore
                .getInstance()

                .collection("households")
                .document(householdId)

                .collection("members")
                .add(member)

                .addOnSuccessListener { documentReference ->
                    val memberId = documentReference.id
                    viewModelScope.launch {
                        sessionManager.saveMemberId(memberId)
                    }

                    onSuccess()
                }

                .addOnFailureListener {

                    onFailure(it.message)
                }
        }
    }
}