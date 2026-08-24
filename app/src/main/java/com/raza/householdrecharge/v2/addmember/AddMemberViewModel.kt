package com.raza.householdrecharge.v2.addmember

import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.BaseViewModel
import com.raza.householdrecharge.v2.common.MemberDto
import com.raza.householdrecharge.v2.SessionManager
import com.raza.householdrecharge.v2.common.getPrintableDate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddMemberViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun onAddMember(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        viewModelScope.launch {
            val member = MemberDto(
                name = name,
                mobileNumber = mobileNumber,
                planDurationDays = planDurationDays,
                planExpiryDate = getPrintableDate(planExpiryDate),
                lastRechargeDate = getPrintableDate(lastRechargeDate),
                planAmount = planAmount
            )

            val userId = sessionManager.userId.first()

            if(userId.isEmpty()) {
                isLoading = false
                onFailure("user not found")
            }

            val householdId = sessionManager.householdId.first()

            if (householdId.isEmpty()) {
                isLoading = false
                onFailure("household not found")
            }

            FirebaseFirestore
                .getInstance()

                .collection("users")
                .document(userId)

                .collection("households")
                .document(householdId)

                .collection("members")
                .add(member)

                .addOnSuccessListener { documentReference ->
                    val memberId = documentReference.id
                    viewModelScope.launch {
                        sessionManager.saveMemberId(memberId)
                    }

                    isLoading = false
                    onSuccess("member create: $memberId")
                }

                .addOnFailureListener {

                    isLoading = false
                    onFailure(it.message)
                }
        }
    }
}