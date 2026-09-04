package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.model.RechargeHistory
import com.raza.householdrecharge.util.cleanString

class RechargeRepository() {

    fun addRecharge(
        rechargeHistory: RechargeHistory,
        appUserDto: AppUserDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.authId)

            .collection("households")
            .document(appUserDto.householdId)

            .collection("members")
            .document(appUserDto.memberId)

            .collection("mobileNumbers")
            .document(appUserDto.mobileNumber)

            .collection("recharges")
            .add(rechargeHistory)

            .addOnSuccessListener { documentReference ->
                val rechargeHistoryId = documentReference.id

                onSuccess(rechargeHistoryId)
            }
            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun fetchRechargeHistoryList(
        appUserDto: AppUserDto,
        onSuccess: (List<RechargeHistory>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.authId)

            .collection("households")
            .document(appUserDto.householdId)

            .collection("members")
            .document(appUserDto.memberId)

            .collection("mobileNumbers")
            .document(appUserDto.mobileNumber)

            .collection("recharges")
            .get()
            .addOnSuccessListener { result ->
                val rechargeHistoryList = result.documents.mapNotNull { document ->
                    document.toObject(RechargeHistory::class.java)
                }

                onSuccess(rechargeHistoryList)
            }
            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }
}