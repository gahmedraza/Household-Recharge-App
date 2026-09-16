package com.raza.householdrecharge.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.repository.AccountRepository
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.util.cleanString
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val accountRepository: AccountRepository
): BaseViewModel() {

    var profileName by mutableStateOf("")
    var profileHousehold by mutableStateOf("")

    fun getAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true

            val accountId = sessionManager.authId.first()

            val result = accountRepository.fetchAccountByAccountId(accountId)

            if(result is Result.Failure) {
                log(result.error.toString())
                return@launch
            }

            val account = (result as Result.Success).data

            profileName = account.accountName.cleanString()
            profileHousehold = sessionManager.householdName.first()
        }
    }
}