package com.raza.householdrecharge.presentation.addmember

import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.presentation.components.getPrintableDate
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.repository.MemberRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.MemberDto
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val memberRepository: MemberRepository
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

            val result = memberRepository.addMember(
                appUserDto = appUserDto,

                member = member
            )

            when(result) {

                is Result.Success<String> -> {
                    viewModelScope.launch {
                        sessionManager.saveMemberId(result.data)
                    }

                    isLoading = false
                    onSuccess("member create: ${result.data}")
                }

                is Result.Failure<String> -> {

                    isLoading = false
                    onFailure(result.error)
                }
            }
        }
    }
}