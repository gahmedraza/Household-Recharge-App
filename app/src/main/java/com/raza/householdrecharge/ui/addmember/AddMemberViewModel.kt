package com.raza.householdrecharge.ui.addmember

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.MemberDto
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.common.getPrintableDate
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.remote.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddMemberViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {

    fun onAddMember(onSuccess: (String?) -> Unit, onFailure: (String?) -> Unit) {
        isLoading = true

        viewModelScope.launch {
            val member = MemberDto(
                name = accountName,
                mobileNumber = mobileNumber,
                planDurationDays = planDurationDays,
                planExpiryDate = getPrintableDate(
                    planExpiryDate
                ),
                lastRechargeDate = getPrintableDate(
                    lastRechargeDate
                ),
                planAmount = planAmount
            )

            val userId = sessionManager.authId.first()

            if(userId.isEmpty()) {
                isLoading = false
                onFailure("user not found")
            }

            val householdId = sessionManager.householdId.first()

            if (householdId.isEmpty()) {
                isLoading = false
                onFailure("household not found")
            }

            val appUserDto = AppUserDto(
                authId = userId,
                householdId = householdId
            )

            FirestoreRepository.addMember(
                appUserDto = appUserDto,

                member = member,

                onSuccess = { memberId ->
                    viewModelScope.launch {
                        sessionManager.saveMemberId(memberId)
                    }

                    isLoading = false
                    onSuccess("member create: $memberId")

                },

                onFailure = { error ->

                    isLoading = false
                    onFailure(error)
                }
            )
        }
    }
}