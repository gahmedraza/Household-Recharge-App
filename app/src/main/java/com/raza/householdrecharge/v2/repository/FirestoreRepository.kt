package com.raza.householdrecharge.v2.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v1.data.Member
import com.raza.householdrecharge.v2.common.HouseholdDto
import com.raza.householdrecharge.v2.common.MemberDto
import com.raza.householdrecharge.v2.common.getDateInMillis
import com.raza.householdrecharge.v2.common.log
import com.raza.householdrecharge.v2.invitation.Invitation
import com.raza.householdrecharge.v2.rechargehistory.RechargeHistory
import kotlinx.coroutines.tasks.await

object FirestoreRepository {

    suspend fun addHousehold(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto
    ): Result<String> {

        return try {
            householdDto.authId = appUserDto.authId

            val documentReference = FirebaseFirestore
                .getInstance()

                .collection("households")
                .add(householdDto)
                .await()

            val householdId = documentReference.id.cleanString()

            if (householdId.isEmpty()) {
                Result.Failure("household id was not generated in households collection")
            }

            Result.Success(householdId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    suspend fun addHouseholdAndUpdateAccount(
        appUserDto: AppUserDto,
        householdDto: HouseholdDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {

            //add household and receive householdId
            val addHouseholdResult = addHousehold(appUserDto, householdDto)
            val householdId: String

            when (addHouseholdResult) {
                is Result.Success -> {

                    householdId = addHouseholdResult.s.cleanString()
                }

                is Result.Failure -> {

                    onFailure("household id was not generated in household collection")
                    return
                }
            }

            //update user with householdId
            updateAccount(appUserDto.accountId, householdId)

            onSuccess("user collection updated with householdId")

        } catch (e: Exception) {

            onFailure(e.message.cleanString())
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

    fun fetchRechargeHistory(
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

    private suspend fun signup(
        authDto: AuthDto
    ): Result<String> {

        return try {
            val result = FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(
                    authDto.mobileNumber,
                    authDto.password
                )
                .await()

            val userId = result.user?.uid.cleanString()

            if (userId.isEmpty()) {
                Result.Failure<String>("user id was not created during signup")
            }

            Result.Success<String>(userId)

        } catch (e: Exception) {
            Result.Failure<String>(e.message.cleanString())
        }
    }

    suspend fun signupAndAddAccount(
        authDto: AuthDto,
        onSuccess: (OnBoardingDto) -> Unit,
        onFailure: (String) -> Unit
    ) {
        try {

            val onBoardingDto: OnBoardingDto

            log("a1")

            var accountDto: AccountDto? = null

            val signupResult = signup(authDto = authDto)

            var userId = ""

            when (signupResult) {
                is Result.Success -> {
                    log("a2")

                    userId = signupResult.s.cleanString()

                    accountDto = AccountDto(
                        accountName = authDto.accountName,
                        accountId = signupResult.s.cleanString()
                    )
                }

                is Result.Failure -> {
                    log("a3")

                    onFailure(signupResult.s.cleanString())
                    return
                    //onFailure("user id was not generated in user collection")
                }
            }

            if (accountDto == null) {
                onFailure("account cannot be added since account dto is empty")
                log("b1")
                return
            }

            log("b2")

            val addAccountResult = addAccount(accountDto = accountDto)

            if (addAccountResult is Result.Success) {
                log("b3")
                onBoardingDto = OnBoardingDto(
                    authId = userId,
                    accountId = addAccountResult.s.cleanString()
                )
                onSuccess(onBoardingDto)
            } else {
                log("b4")
                onFailure("account id was not generated in accounts collection")
                return
            }

            log("c")
            return

        } catch (e: Exception) {

            log("d")

            onFailure(e.message.cleanString())
            return
        }
    }

    private suspend fun addAccount(
        accountDto: AccountDto?
    ): Result<String> {
        return try {

            if (accountDto == null) {
                return Result.Failure("account collection cannot be updated with empty account dto")
            }

            val documentReference = FirebaseFirestore
                .getInstance()

                .collection("accounts")
                .document(accountDto.accountId.cleanString())
                .set(accountDto)

                .await()

            val accountId = accountDto.accountId.cleanString()

            if (accountId.isEmpty()) {
                Result.Failure("account id was not generated in accounts collection")
            }

            Result.Success(accountId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    private suspend fun updateAccount(
        accountId: String, householdId: String
    ): Result<String> {
        return try {

            FirebaseFirestore
                .getInstance()

                .collection("accounts")
                .document(accountId)

                .update("householdId", householdId)

                .await()

            Result.Success(accountId)
        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
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

    suspend fun createInvitation(
        invitation: InvitationDto
    ): Result<String> {

        return try {

            val documentReference = FirebaseFirestore
                .getInstance()
                .collection("invitations")
                .document(invitation.code)
                .set(invitation)
                .await()

            Result.Success("success")

        } catch (e: Exception) {

            Result.Failure(e.message.cleanString())
        }
    }

    suspend fun createInvitationFacade(
        invitation: InvitationDto,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val invitationResult = createInvitation(invitation)

        when(invitationResult) {
            is Result.Success -> {
                onSuccess("success")
            }
            is Result.Failure -> {
                onFailure("failure")
            }
        }
    }

}

data class AuthDto(
    val accountName: String? = null,
    val mobileNumber: String,
    val password: String
)

data class AppUserDto(
    val authId: String,
    val accountId: String = "",
    val householdId: String,
    val memberId: String = "",
    val mobileNumber: String = ""
)

fun String?.cleanString(): String {
    return this ?: ""
}

data class AccountDto(
    val accountId: String? = null,
    val accountName: String? = null,
    val householdId: String? = null,
)

sealed class Result<T> {
    data class Success<T>(val s: T) : Result<T>()
    data class Failure<T>(val s: T) : Result<T>()
}

data class OnBoardingDto(
    val authId: String? = null,
    val accountId: String? = null
)

data class InvitationDto(
    val code: String = "",
    val householdId: String = "",
    val createdBy: String = "",
    val createdAt: String = "",
    val status: String = ""
)