package com.raza.householdrecharge.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.logging.Logger.log
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.AccountUseCase
import com.raza.householdrecharge.domain.validator.AccountRequestValidator
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
    private val accountUseCase: AccountUseCase,
    private val accountRequestValidator: AccountRequestValidator
): ViewModel() {

    var accountUIState = MutableStateFlow(AccountUIState())

    fun getAccount() {
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
                //propogate the error to the composable and to the UI
                log(validationResult.error.toString())
                return@launch
            }

            val result = accountUseCase.fetchAccountByAccountId(accountId)

            if(result is Result.Failure) {
                Logger.log(result.error.toString())
                accountUIState.update {
                    it.copy(
                        isLoading = false
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