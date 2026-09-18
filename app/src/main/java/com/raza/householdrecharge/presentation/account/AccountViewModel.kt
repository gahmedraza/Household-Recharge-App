package com.raza.householdrecharge.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.Logger
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.domain.usecase.AccountUseCase
import com.raza.householdrecharge.presentation.common.BaseViewModel
import com.raza.householdrecharge.util.cleanString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val accountUseCase: AccountUseCase
): ViewModel() {

    var accountUIState by mutableStateOf(AccountUIState())

    fun getAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            accountUIState.isLoading = true

            val accountId = sessionManager.authId.first()

            val result = accountUseCase.fetchAccountByAccountId(accountId)

            if(result is Result.Failure) {
                Logger.log(result.error.toString())
                accountUIState.isLoading = false
                return@launch
            }

            val account = (result as Result.Success).data

            accountUIState.profileName = account.accountName.cleanString()
            accountUIState.profileHousehold = sessionManager.householdName.first()
            accountUIState.isLoading = false
        }
    }
}