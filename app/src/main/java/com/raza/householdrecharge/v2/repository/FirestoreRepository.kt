package com.raza.householdrecharge.v2.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.data.Member
import com.raza.householdrecharge.v2.HouseholdDto
import com.raza.householdrecharge.v2.common.MemberDto
import com.raza.householdrecharge.v2.common.getDateInMillis
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory

object FirestoreRepository {

    fun addHousehold2(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(appUserDto.userId)
            .collection("households")
            .add(householdDto)
            .addOnSuccessListener { documentReference ->
                val householdId = documentReference.id
                onSuccess(householdId)

            }

            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun addRechargeHistory(
        rechargeHistory: RechargeHistory,
        appUserDto: AppUserDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.userId)

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

    fun fetchRechargeHistory(
        appUserDto: AppUserDto,
        onSuccess: (List<RechargeHistory>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.userId)

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

    fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()
            .collection("users")
            .document(appUserDto.userId)
            .collection("households")
            .add(householdDto)
            .addOnSuccessListener { documentReference ->
                val householdId = documentReference.id
                onSuccess(householdId)

            }

            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun signup(
        authDto: AuthDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(authDto.mobileNumber, authDto.password)
            .addOnSuccessListener { documentReference ->

                val userId = documentReference.user?.uid
                onSuccess(userId.cleanString())
            }
            .addOnFailureListener { error ->

                onFailure(error.message.cleanString())
            }
    }

    fun signIn(
        authDto: AuthDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(authDto.mobileNumber, authDto.password)
            .addOnSuccessListener { documentReference ->

                val userId = documentReference?.user?.uid.cleanString()
                onSuccess(userId)
            }
            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun fetchHousehold(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firebaseUser = FirebaseAuth
            .getInstance()
            .currentUser

        val firebaseUserIdNotFound = firebaseUser?.uid?.isEmpty() ?: false

        if (firebaseUserIdNotFound) {
            onFailure("Firebase user id does not exist")
            return
        }


        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(firebaseUser?.uid.cleanString())

            .get()

            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    onFailure("User data not found")
                }

                val householdId = document.getString("householdId")
                onSuccess(householdId.cleanString())
            }

            .addOnFailureListener {

                onFailure(it.message.cleanString())
            }
    }

    fun fetchMembers(
        appUserDto: AppUserDto,
        onSuccess: (List<Member>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore
            .getInstance()

            .collection("users")
            .document(appUserDto.userId)

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
                                getDateInMillis(memberDto?.lastRechargeDate)
                            },

                        planExpiryDate =
                            if (memberDto?.planExpiryDate?.isEmpty() ?: false) {
                                0
                            } else {
                                getDateInMillis(memberDto?.planExpiryDate)
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

            .collection("users")
            .document(appUserDto.userId)

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

data class AuthDto(
    val mobileNumber: String,
    val password: String
)

data class AppUserDto(
    val userId: String,
    val householdId: String,
    val memberId: String = "",
    val mobileNumber: String = ""
)

fun String?.cleanString(): String {
    return this ?: ""
}