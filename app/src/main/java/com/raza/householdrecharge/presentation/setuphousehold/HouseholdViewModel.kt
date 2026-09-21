package com.raza.householdrecharge.presentation.setuphousehold

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.core.logging.Logger.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.error.HouseholdUseCaseError
import com.raza.householdrecharge.domain.model.FindHouseholdResponse
import com.raza.householdrecharge.domain.model.Household
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.domain.validator.HouseholdRequestValidator
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseholdViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val householdRequestValidator: HouseholdRequestValidator
) : ViewModel() {

    var householdUIState = MutableStateFlow(HouseholdUIState())

    fun onAddHousehold(
        householdName: String,
        onSuccess: (String?) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            householdUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val userId = sessionManager.authId.first()
            val accountId = sessionManager.accountId.first()

            val userNotFound = userId.isEmpty()

            if (userNotFound) {
                householdUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                onFailure("user not found")
                return@launch
            }

            val validationResult = householdRequestValidator.validate(
                accountId = accountId,
                authId = userId
            )

            if(validationResult is Result.Failure) {
                log(validationResult.error.toString())
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

                        householdUIState.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        onSuccess(householdId)
                    }
                }

                is Result.Failure<String> -> {

                    householdUIState.update {
                        it.copy(
                            isLoading = false
                        )
                    }
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
        onSuccess: (FindHouseholdResponse?) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        viewModelScope.launch {

            householdUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val authId = sessionManager.authId.first()

            val result = householdUseCase.validateInvitationAndFindLinkedHousehold(
                invitationCode = invitationCode,
                authId = authId
            )

            if(result is Result.Success) {
                householdUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }

                val findHouseholdResponse = result.data
                sessionManager.saveHouseholdId(findHouseholdResponse?.householdId.cleanString())
                sessionManager.saveHouseholdName(findHouseholdResponse?.householdName.cleanString())

                onSuccess(result.data)

            } else {
                householdUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }
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
            householdUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            try {

                val userId = FirebaseAuth
                    .getInstance()
                    .currentUser
                    ?.uid
                    .cleanString()

                val result = householdUseCase.validateInvitationAndUpdateAccountAndMarkUsed(
                    userId = userId,
                    householdId = householdId,
                    invitationCode = invitationCode
                )

                when(result) {

                    is Result.Success<String> -> {

                        householdUIState.update {
                            it.copy(
                                isLoading = false
                            )
                        }

                        onSuccess("you have been added to the household")
                    }

                    is Result.Failure<String> -> {

                        householdUIState.update {
                            it.copy(
                                isLoading = false
                            )
                        }

                        onFailure("unable to add you to the household")
                    }
                }

            } catch (e: Exception) {

                householdUIState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                onFailure("unable to join household")

            }
        }
    }

    fun onInvitationCodeChanged(invitationCode: String) {
        householdUIState.update {
            it.copy(
                invitationCode = invitationCode
            )
        }
    }

    //todo primitive
    fun onHouseholdNameChanged(householdName: String) {
        householdUIState.update {
            it.copy(
                household = it.household.copy(
                    name = householdName
                )
            )
        }
    }
}