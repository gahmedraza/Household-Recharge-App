package com.raza.householdrecharge.ui.addrecharge

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.RechargeRepository
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.domain.model.RechargeHistory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddRechargeViewModel(
    private val sessionManager: SessionManager,
    private val rechargeRepository: RechargeRepository
) : BaseViewModel() {
    var amount by mutableStateOf("")
    var date by mutableStateOf("")
    var rechargedBy by mutableStateOf("")

    fun addRecharge(
        memberId: String,
        mobileNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val rechargeHistory =
                RechargeHistory(
                    amount = amount,
                    date = date,
                    rechargedBy = rechargedBy
                )

            val userId = sessionManager.authId.first()

            if(userId.isEmpty()) {
                onFailure("user not found")
                return@launch
            }

            val householdId = sessionManager.householdId.first()

            if(householdId.isEmpty()) {
                onFailure("household not found")
                return@launch
            }

            if(memberId.isNullOrEmpty()) {
                onFailure("No member found")
            }

            Log.d("TAG", "memberId: $memberId")

            val mobileNumberNotFound = mobileNumber.isEmpty()

            if(mobileNumberNotFound) {
                onFailure("No mobile number found")
            }

            val appUserDto = AppUserDto(
                authId = userId,
                householdId = householdId,
                memberId = memberId,
                mobileNumber = mobileNumber
            )

            rechargeRepository.addRecharge(
                rechargeHistory = rechargeHistory,

                appUserDto = appUserDto,

                onSuccess = { rechargeHistoryId ->

                    onSuccess()
                },

                onFailure = { error ->

                    onFailure(error)
                }
            )
        }
    }

}