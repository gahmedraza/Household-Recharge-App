package com.raza.householdrecharge.ui.dashbord

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.common.BaseViewModel
import com.raza.householdrecharge.common.SessionManager
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.repository.MemberRepository
import com.raza.householdrecharge.domain.model.Member
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.repository.MobileNumberRepository

class DashboardViewModel(
    private val sessionManager: SessionManager,
    private val memberRepository: MemberRepository,
    private val mobileNumberRepository: MobileNumberRepository
) : BaseViewModel() {
    var members by mutableStateOf<List<Member>>(emptyList())
    var mobileNumbers by mutableStateOf<List<MobileNumberDto>>(emptyList())

    fun loadMobileNumbers(
        onSuccess: (List<MobileNumberDto>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            val result = mobileNumberRepository.getAllMobileNumbers()

            when(result) {
                is Result.Success -> {
                    mobileNumbers = result.data

                    onSuccess(mobileNumbers)
                }

                is Result.Failure -> {

                    onFailure("failure")
                }
            }
        }
    }

    fun loadMembers(onSuccess: () -> Unit, onFailure: (String?) -> Unit) {
        viewModelScope.launch {
            val householdId = sessionManager.householdId.first()

            val householdNotFound = householdId.isEmpty()

            if (householdNotFound) {
                onFailure("No household found")
                return@launch
            }

            val userId = sessionManager.authId.first()

            if (userId.isEmpty()) {
                onFailure("No user found")
                return@launch
            }

            Log.d("TAG", "householdId= $householdId")

            val appUserDto = AppUserDto(
                authId = userId,
                householdId = householdId
            )

            val result = memberRepository.fetchMembers(
                appUserDto = appUserDto
            )

            when(result) {

                is Result.Success<List<Member>> -> {

                    this@DashboardViewModel.members = result.data
                    onSuccess()
                }

                is Result.Failure<String> -> {

                    onFailure(result.error)
                }
            }
        }
    }
}