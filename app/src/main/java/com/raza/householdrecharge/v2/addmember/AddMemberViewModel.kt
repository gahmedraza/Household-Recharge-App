package com.raza.householdrecharge.v2.addmember

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.MemberDto
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.common.getPrintableDate
import com.raza.householdrecharge.v2.data.dto.*
import com.raza.householdrecharge.v2.repository.FirestoreRepository
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
                planExpiryDate = getPrintableDate(planExpiryDate),
                lastRechargeDate = getPrintableDate(lastRechargeDate),
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