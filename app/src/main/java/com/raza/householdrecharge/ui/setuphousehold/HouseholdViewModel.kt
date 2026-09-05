package com.raza.householdrecharge.ui.setuphousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.InvitationDto
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HouseholdViewModel(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val householdRepository: HouseholdRepository
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

            val result = householdUseCase.addHouseholdAndUpdateAccount(
                appUserDto = appUserDto,

                householdDto = householdDto
            )

            when(result) {

                is Result.Success<String> -> {

                    val householdId = result.data

                    viewModelScope.launch {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)

                        isLoading = false
                        onSuccess(householdId)
                    }
                }

                is Result.Failure<String> -> {

                    isLoading = false
                    onFailure(result.error)
                }
            }
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

        val householdDto = snapshot.toObject(HouseholdDto::class.java)
        householdDto?.householdId=householdId

        return householdDto
    }

    /**
     * Joins the user to a household using the provided invitation code.
     *
     * First checks whether the user's account is already associated with a household.
     * If no household is associated, the invitation is marked as used and the
     * household ID is assigned to the user's account.
     */
    fun joinHousehold(
        invitationCode: String,
        onSuccess: (HouseholdDto?) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        viewModelScope.launch {

        }
    }

    fun validateAccountAndJoinHousehold(
        invitationCode: String,
        onSuccess: (HouseholdDto?) -> Unit,
        onFailure: (String?) -> Unit
    ) : Task<Result<HouseholdDto?, String?>> {

        val source = TaskCompletionSource<Result<HouseholdDto?, String?>>()

        viewModelScope.launch {

            //validateaccount

            val authId = sessionManager.authId.first()

            val result = householdUseCase.validateAccountAndJoinHousehold(
                accountId = authId
            )

            when(result) {

                is Result.Success<String> -> {

                    validateInvitationCode(
                        invitationCode = invitationCode,

                        onSuccess = { householdDto ->

                            onSuccess(householdDto)
                        },

                        onFailure = { error ->

                            onFailure(error)
                        }
                    )
                }

                is Result.Failure<String> -> {

                    onFailure("error")
                }
            }

            //join household
        }

        return source.task
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
                onFailure("invitation code not found")
                return@launch
            }

            if(invitation?.status != "pending") {
                isLoading = false
                onFailure("invitation code already used")
                return@launch
            }

            val invitationExpiry = invitation?.expiresAt?.toLong() ?: 0L

            if(invitationExpiry < System.currentTimeMillis()) {
                isLoading = false
                onFailure("invitation code has expired")
                return@launch
            }

            val household = getHousehold(invitation?.householdId.cleanString())
            household?.invitationCode=invitationCode

            if(household == null) {
                isLoading = false
                onFailure("failure")
                return@launch
            }

            isLoading = false
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

                val result = householdRepository.joinHousehold(
                    userId = userId,
                    householdId = householdId,
                    invitationCode = invitationCode
                )

                when(result) {

                    is Result.Success<String> -> {

                        isLoading = false

                        onSuccess("you have been added to the household")
                    }

                    is Result.Failure<String> -> {

                        isLoading = false

                        onFailure("unable to add you to the household")
                    }
                }

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