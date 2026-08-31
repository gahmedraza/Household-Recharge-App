package com.raza.householdrecharge.v2.rechargehistory

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.AppUserDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RechargeListingViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    var mobileRechargeHistory by mutableStateOf<List<RechargeHistory>>(emptyList())

    fun loadRechargeHistory(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        memberId: String,
        mobileNumber: String
    ) {
        viewModelScope.launch {
            val userId = sessionManager.authId.first()

            if(userId.isNullOrEmpty()) {
                onFailure("user not found")
                return@launch
            }

            val householdId = sessionManager.householdId.first()

            if(householdId.isNullOrEmpty()) {
                onFailure("household not found")
                return@launch
            }

            var currentMemberId = memberId

            if (currentMemberId.isNullOrEmpty()) {
                currentMemberId = sessionManager.memberId.first()
            }

            if(currentMemberId.isNullOrEmpty()) {
                onFailure("member not found")
                return@launch
            }

            var currentMobileNumber = mobileNumber

            if(currentMobileNumber.isNullOrEmpty()) {
                currentMobileNumber = sessionManager.mobileNumber.first()
            }

            if(currentMobileNumber.isNullOrEmpty()) {
                onFailure("mobile number not found")
                return@launch
            }

            val appUserDto = AppUserDto(
                authId = userId,
                householdId = householdId,
                memberId = currentMemberId,
                mobileNumber = currentMobileNumber
            )

            FirestoreRepository.fetchRechargeHistory(
                appUserDto = appUserDto,

                onSuccess = { rechargeHistoryList ->

                    mobileRechargeHistory = rechargeHistoryList
                    onSuccess()
                },

                onFailure = { error ->

                    onFailure(error)
                }
            )
        }
    }
}