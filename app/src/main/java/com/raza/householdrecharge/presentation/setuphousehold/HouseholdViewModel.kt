package com.raza.householdrecharge.presentation.setuphousehold

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.raza.householdrecharge.core.logging.Logger.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.dto.HouseholdDto
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.domain.validator.request.HouseholdRequestValidator
import com.raza.householdrecharge.presentation.common.HouseholdNameValidator
import com.raza.householdrecharge.presentation.common.InvitationCodeValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
import com.raza.householdrecharge.presentation.error.ResponseErrorMapper
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseholdViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val householdRequestValidator: HouseholdRequestValidator,
    private val householdNameValidator: HouseholdNameValidator,
    private val invitationCodeValidator: InvitationCodeValidator,
    private val responseErrorMapper: ResponseErrorMapper,
    private val requestErrorMapper: RequestErrorMapper
) : ViewModel() {

    var householdUIState = MutableStateFlow(HouseholdUIState())

    fun onAddHousehold(
        householdName: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            householdUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            //validate the input fields
            householdUIState.update {
                it.copy(
                    householdNameError = householdNameValidator.validate(
                        householdUIState.value.householdName,
                        householdUIState.value.householdNameError
                    )
                )
            }

            if(householdUIState.value.householdNameError.isNotEmpty()) {

                return@launch
            }

            val userId = sessionManager.authId.first()
            val accountId = sessionManager.accountId.first()

            val userNotFound = userId.isEmpty()

            if (userNotFound) {
                householdUIState.update {
                    it.copy(
                        apiResponse = "user not found",
                        shouldProceed = false,
                        isLoading = false
                    )
                }

                return@launch
            }

            val validationResult = householdRequestValidator.validate(
                accountId = accountId,
                authId = userId
            )

            if(validationResult is Result.Failure) {
                householdUIState.update {
                    it.copy(
                        apiResponse = requestErrorMapper.map(validationResult.error),
                    )
                }

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

                    viewModelScope.launch(Dispatchers.IO) {
                        sessionManager.saveHouseholdName(householdName)
                        sessionManager.saveHouseholdId(householdId)

                        householdUIState.update {
                            it.copy(
                                apiResponse = "household created with id: householdId",
                                shouldProceed = true,
                                isLoading = false
                            )
                        }
                    }
                }

                is Result.Failure -> {

                    householdUIState.update {
                        it.copy(
                            apiResponse = "household creation failure\n${responseErrorMapper.map(result.error)}",
                            shouldProceed = false,
                            isLoading = false
                        )
                    }
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
    ) {

        viewModelScope.launch(Dispatchers.IO) {
            //
            //validate the input fields
            householdUIState.update {
                it.copy(
                    invitationCodeError = invitationCodeValidator.validate(
                        householdUIState.value.invitationCode,
                        householdUIState.value.invitationCodeError
                    )
                )
            }

            if(householdUIState.value.invitationCodeError.isNotEmpty()) {

                return@launch
            }
            //

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

            when(result) {
                is Result.Success -> {

                    val findHouseholdResponse = result.data
                    sessionManager.saveHouseholdId(findHouseholdResponse?.householdId.cleanString())
                    sessionManager.saveHouseholdName(findHouseholdResponse?.householdName.cleanString())

                    householdUIState.update {
                        it.copy(
                            apiResponse = "invitation code found: ${result.data}",
                            shouldProceed = true,
                            isLoading = false,
                            findHouseholdResponse = result.data
                        )
                    }
                }

                is Result.Failure -> {

                    householdUIState.update {
                        it.copy(
                            apiResponse = responseErrorMapper.map(result.error),
                            shouldProceed = false,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }



    fun onJoinHousehold(
        householdId: String,
        invitationCode: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
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
                                isLoading = false,
                                shouldProceed = true,
                                apiResponse = "you have been added to the household"
                            )
                        }
                    }

                    is Result.Failure -> {

                        householdUIState.update {
                            it.copy(
                                isLoading = false,
                                shouldProceed = false,
                                apiResponse = "unable to add you to the household"
                            )
                        }
                    }
                }

            } catch (e: Exception) {

                householdUIState.update {
                    it.copy(
                        isLoading = false,
                        apiResponse = "unable to join household",
                        shouldProceed = false
                    )
                }
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

    fun resetInvitationCodeError() {
        householdUIState.update {
            it.copy(
                invitationCodeError = ""
            )
        }
    }

    fun onHouseholdNameChanged(householdName: String) {
        householdUIState.update {
            it.copy(
                householdName = householdName
            )
        }
    }

    fun resetHouseholdNameError() {
        householdUIState.update {
            it.copy(
                householdNameError = ""
            )
        }
    }
}