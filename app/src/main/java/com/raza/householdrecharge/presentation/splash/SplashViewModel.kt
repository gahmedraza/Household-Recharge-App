package com.raza.householdrecharge.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.logging.Logger.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.AuthUseCase
import com.raza.householdrecharge.domain.usecase.HouseholdUseCase
import com.raza.householdrecharge.domain.validator.request.InvitationRequestValidator
import com.raza.householdrecharge.domain.validator.request.SplashRequestValidator
import com.raza.householdrecharge.presentation.error.RequestErrorMapper
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val householdUseCase: HouseholdUseCase,
    private val authUseCase: AuthUseCase,
    private val requestValidator: SplashRequestValidator,
    private val requestErrorMapper: RequestErrorMapper
): ViewModel() {

    var splashUIState = MutableStateFlow(SplashUIState())

    fun getStartDestination(
    ) {

        viewModelScope.launch(Dispatchers.IO) {

            val result51 = authUseCase.getUser()

            if(result51 is Result.Failure) {
                splashUIState.update {
                    it.copy(
                        destination = SplashDestination.Login
                    )
                }

                return@launch
            }

            val user = (result51 as Result.Success).data

            //
            val validationResult = requestValidator.validate(
                accountId = user.userId,
            )

            if(validationResult is Result.Failure) {
                splashUIState.update {
                    it.copy(
                        apiResponse = requestErrorMapper.map(validationResult.error),
                    )
                }

                log(validationResult.error.toString())
                return@launch
            }
            //

            val result53 = householdUseCase.isAccountEligibleToJoinHousehold(
                accountId = user.userId
            )

            if(result53 is Result.Failure) {
                splashUIState.update {
                    it.copy(
                        destination = SplashDestination.Dashboard
                    )
                }

                return@launch
            }

            val accountEligibilityDto = (result53 as Result.Success).data

            viewModelScope.launch(Dispatchers.IO) {
                sessionManager.saveHouseholdLinkStatus(
                    accountEligibilityDto.isEligible
                )
            }

            if(accountEligibilityDto.isEligible) {
                splashUIState.update {
                    it.copy(
                        destination = SplashDestination.SetupHousehold
                    )
                }

                return@launch
            }

            //
            val validationResult2 = requestValidator.validate2(
                householdId = accountEligibilityDto.account.householdId.cleanString(),
            )

            if(validationResult2 is Result.Failure) {
                splashUIState.update {
                    it.copy(
                        apiResponse = requestErrorMapper.map(validationResult2.error),
                    )
                }

                log(validationResult2.error.toString())
                return@launch
            }
            //

            val result54 = householdUseCase.getHouseholdByHouseholdId(
                accountEligibilityDto.account.householdId.cleanString()
            )

            when(result54) {
                is Result.Success -> {
                    viewModelScope.launch(Dispatchers.IO) {
                        sessionManager.saveHouseholdName(result54.data.householdName.cleanString())
                        sessionManager.saveHouseholdId(result54.data.householdId.cleanString())
                    }
                }

                is Result.Failure -> {
                    //todo
                }
            }

            splashUIState.update {
                it.copy(
                    destination = SplashDestination.Dashboard
                )
            }
        }
    }
}