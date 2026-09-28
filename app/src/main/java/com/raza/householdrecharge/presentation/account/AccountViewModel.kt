package com.raza.householdrecharge.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                        errorMessage = errorMessage
                    )
                }

                return@launch
            }

            val result = profileUseCase.fetchProfileByAccountId(accountId)

            if(result is Result.Failure) {
                val error = responseErrorMapper.map(result.error)

                //Logger.log(error)

                accountUIState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error
                    )
                }

                return@launch
            }

            val account = (result as Result.Success).data

            accountUIState.update {
                it.copy(
                    profileName = account.accountName.cleanString(),
                    profileHousehold = sessionManager.householdName.first(),
                    isLoading = false
                )
            }
        }
    }
}