package com.raza.householdrecharge.ui.setuphousehold

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.HouseholdDto
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.repository.HouseholdRepository
import com.raza.householdrecharge.data.repository.InvitationRepository
import com.raza.householdrecharge.domain.error.HouseholdUseCaseError
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.util.cleanString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HouseholdViewModel(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val householdRepository: HouseholdRepository,
    private val invitationRepository: InvitationRepository
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

            val result = householdUseCase.createHouseholdAndUpdateAccount(
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

    /**
     * Joins the user to a household using the provided invitation code.
     *
     * First checks whether the user's account is already associated with a household.
     * If no household is associated, the invitation is marked as used and the
     * household ID is assigned to the user's account.
     */
    fun findHousehold(
        invitationCode: String,
        onSuccess: (HouseholdDto?) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        viewModelScope.launch {

            isLoading = true

            val authId = sessionManager.authId.first()

            val result = householdUseCase.validateInvitationAndFindLinkedHousehold(
                invitationCode = invitationCode,
                authId = authId
            )

            if(result is Result.Success) {
                isLoading = false
                onSuccess(result.data)

            } else {
                isLoading = false
                val error = (result as Result.Failure).error

                when(error) {
                    is HouseholdUseCaseError.Invitation -> {
                        onFailure(error.error.toString())
                    }
                    is HouseholdUseCaseError.Household -> {
                        onFailure(error.error.toString())
                    }
                    is HouseholdUseCaseError.Unknown -> {
                        onFailure(error.error)
                    }
                }
            }
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