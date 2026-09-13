package com.raza.householdrecharge.presentation.dashbord

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.core.logging.log
import com.raza.householdrecharge.data.session.SessionManager
import com.raza.householdrecharge.data.remote.dto.AppUserDto
import com.raza.householdrecharge.data.repository.MemberRepository
import com.raza.householdrecharge.domain.model.Member
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.raza.householdrecharge.core.result.Result
import com.raza.householdrecharge.data.remote.dto.MobileNumberDto
import com.raza.householdrecharge.data.remote.mapper.MobileNumberDtoMapper
import com.raza.householdrecharge.data.repository.MobileNumberRepository
import com.raza.householdrecharge.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val memberRepository: MemberRepository,
    private val mobileNumberRepository: MobileNumberRepository
) : BaseViewModel() {
    var members by mutableStateOf<List<Member>>(emptyList())
    val mobileNumberList = MutableStateFlow<List<MobileNumberDto>>(emptyList())

    init {
        log("viewmodel init called...")
        observeMobileNumbers()
    }

    fun syncMobileNumbers(
    ) {
        viewModelScope.launch {
            mobileNumberRepository.syncMobileNumbers()
        }
    }

    fun observeMobileNumbers(
    ) {
        viewModelScope.launch {
            mobileNumberRepository.observeMobileNumbers().collect { mobileNumberEntityList ->

                val mobileNumberDtoList = MobileNumberDtoMapper.map(mobileNumberEntityList)

                mobileNumberList.value = mobileNumberDtoList
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