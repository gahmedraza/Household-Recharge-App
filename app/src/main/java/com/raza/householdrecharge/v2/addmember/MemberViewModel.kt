package com.raza.householdrecharge.v2.addmember

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.BaseViewModel

class MemberViewModel: BaseViewModel() {

    fun onAddMember(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        val member = Member(
            id = 1002,
            name = name,
            mobileNumber = mobileNumber,
            planDurationDays = planDurationDays,
            planExpiryDate = planExpiryDate,
            lastRechargeDate = lastRechargeDate
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
}