package com.raza.householdrecharge.v2.setuphousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.HouseholdDto
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.AppUserDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.repository.InvitationDto
import com.raza.householdrecharge.v2.repository.cleanString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HouseholdViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var invitationCode by mutableStateOf("")

    fun onAddHousehold(
        householdName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            val userId = sessionManager.authId.first()
            val accountId = sessionManager.accountId.first()

            val userNotFound = userId.isEmpty()

            if (userNotFound) {
                isLoading = false
                onFailure("user not found")
                return@launch
            }

            val householdDto = HouseholdDto(
                householdName = householdName
            )

            val appUserDto = AppUserDto(
                authId = userId.cleanString(),
                accountId = accountId.cleanString(),
                householdId = ""
            )

            FirestoreRepository.addHouseholdAndUpdateAccount(
                appUserDto = appUserDto,

                householdDto = householdDto,

                onSuccess = { householdId ->

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)

                        isLoading = false
                        onSuccess(householdId)
                    }
                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                }
            )
        }
    }

    suspend fun getInvitation(code: String): InvitationDto? {
        try {

            val snapshot = FirebaseFirestore
                .getInstance()
                .collection("invitations")
                .document(code.uppercase())
                .get()
                .await()

            if(!snapshot.exists()) {
                return null
            }

            return snapshot.toObject(InvitationDto::class.java)
        } catch (e: Exception) {

            return null
        }
    }

    suspend fun getHousehold(householdId: String): HouseholdDto? {

        val snapshot = FirebaseFirestore
            .getInstance()
            .collection("households")
            .document(householdId)
            .get()
            .await()

        if(!snapshot.exists()) {
            return null
        }

        return snapshot.toObject(HouseholdDto::class.java)
    }

    fun validateInvitationCode(
        invitationCode: String,
        onSuccess: (HouseholdDto?) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        viewModelScope.launch {
            val invitation = getInvitation(invitationCode)

            if(invitation == null) {
                onFailure("error")
            }

            if(invitation?.status != "pending") {
                onFailure("error")
            }

            val invitationExpiry = invitation?.expiresAt?.toLong() ?: 0L

            if(invitationExpiry < System.currentTimeMillis()) {
                onFailure("error")
            }

            val household = getHousehold(invitation?.householdId.cleanString())

            if(household == null) {
                onFailure("error")
            }

            onSuccess(household)
        }
    }
}

data class InvitationResultDto(
    val bool: Boolean = false,
    val householdId: String?,
    val householdName: String?,
    val message: String?
)