package com.raza.householdrecharge.v2.setuphousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.HouseholdDto
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.data.dto.*
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import com.raza.householdrecharge.v2.util.cleanString
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
            isLoading = true

            val invitation = getInvitation(invitationCode)

            if(invitation == null) {
                isLoading = false
                onFailure("error")
            }

            if(invitation?.status != "pending") {
                isLoading = false
                onFailure("error")
            }

            val invitationExpiry = invitation?.expiresAt?.toLong() ?: 0L

            if(invitationExpiry < System.currentTimeMillis()) {
                isLoading = false
                onFailure("error")
            }

            val household = getHousehold(invitation?.householdId.cleanString())

            if(household == null) {
                isLoading = false
                onFailure("error")
            }

            isLoading = true
            onSuccess(household)
        }
    }

    fun onJoinHousehold(
        householdId: String,
        invitationCode: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true

            try {

                val userId = FirebaseAuth
                    .getInstance()
                    .currentUser
                    ?.uid
                    .cleanString()

                FirestoreRepository.joinHousehold(
                    userId = userId,
                    householdId = householdId,
                    invitationCode = invitationCode,
                    onSuccess = {
                        isLoading = false
                    },
                    onFailure = {
                        isLoading = false
                    }
                )

            } catch (e: Exception) {

                isLoading = false
                onFailure("unable to join household")

            }
        }
    }
}

data class InvitationResultDto(
    val bool: Boolean = false,
    val householdId: String?,
    val householdName: String?,
    val message: String?
)