package com.raza.householdrecharge.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.MemberDto
import com.raza.householdrecharge.common.getDateInMillis
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.model.Member
import com.raza.householdrecharge.util.cleanString

class MemberRepository() {

    fun fetchMembers(
        appUserDto: AppUserDto,
        onSuccess: (List<Member>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.authId)

            .collection("households")
            .document(appUserDto.householdId)

            .collection("members")
            .get()

            .addOnSuccessListener { result ->
                val memberList = mutableListOf<Member>()

                result.documents.mapNotNull { document ->
                    val memberDto = document.toObject(MemberDto::class.java)

                    val member = Member(
                        id = document.id,

                        name = memberDto?.name ?: "",

                        mobileNumber = memberDto?.mobileNumber ?: "",

                        planDurationDays =
                            if (memberDto?.planDurationDays?.isEmpty() ?: false) {
                                0
                            } else {
                                memberDto?.planDurationDays?.toInt() ?: 0
                            },

                        lastRechargeDate =
                            if (memberDto?.lastRechargeDate?.isEmpty() ?: false) {
                                0
                            } else {
                                getDateInMillis(
                                    memberDto?.lastRechargeDate
                                )
                            },

                        planExpiryDate =
                            if (memberDto?.planExpiryDate?.isEmpty() ?: false) {
                                0
                            } else {
                                getDateInMillis(
                                    memberDto?.planExpiryDate
                                )
                            },

                        rechargeRequested = false,

                        planAmount =
                            if (memberDto?.planAmount?.isEmpty() ?: false) {
                                0
                            } else {
                                memberDto?.planAmount?.toInt() ?: 0
                            }
                    )

                    memberList.add(member)
                }

                onSuccess(memberList)
            }
            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun addMember(
        appUserDto: AppUserDto,
        member: MemberDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("accounts")
            .document(appUserDto.authId)

            .collection("households")
            .document(appUserDto.householdId)

            .collection("members")
            .add(member)

            .addOnSuccessListener { documentReference ->
                val memberId = documentReference.id

                onSuccess(memberId)
            }

            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }
}