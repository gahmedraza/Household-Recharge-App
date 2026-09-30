package com.raza.householdrecharge.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.ProfileUseCase
import com.raza.householdrecharge.domain.validator.request.AccountRequestValidator
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
class AccountViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val profileUseCase: ProfileUseCase,
    private val accountRequestValidator: AccountRequestValidator,
    private val requestErrorMapper: RequestErrorMapper,
    private val responseErrorMapper: ResponseErrorMapper
): ViewModel() {

    var accountUIState = MutableStateFlow(AccountUIState())

    fun getProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            accountUIState.update {
                it.copy(
                    isLoading = true
                )
            }

            val accountId = sessionManager.authId.first()

            val validationResult = accountRequestValidator.validate(
                accountId = accountId
            )

            if(validationResult is Result.Failure) {
                //propagate the error to the composable and to the UI
                val errorMessage = requestErrorMapper.map(validationResult.error)

                //Logger.log(errorMessage.)

                accountUIState.update {
                    it.copy(
                        isLoading = false,
                        apiStatus = errorMessage
                    )
                }

                return@launch
            }

            val result = profileUseCase.fetchProfileByAccountId(accountId)

            when(result) {
                is Result.Failure -> {
                    //Logger.log(error)

                    accountUIState.update {
                        it.copy(
                            isLoading = false,
                            apiStatus = "failure: ${responseErrorMapper.map(result.error)}",
                            shouldProceed = false
                        )
                    }

                    return@launch
                }

                is Result.Success -> {

                    val householdName = sessionManager.householdName.first()
                    Logger.log("AccountViewModel", "householdName = $householdName")

                    accountUIState.update {
                        it.copy(
                            profileName = result.data.accountName.cleanString(),
                            profileHousehold = householdName,
                            isLoading = false,
                            shouldProceed = true
                        )
                    }
                }
            }
        }
    }
}