package com.raza.householdrecharge.v2.dashbord

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.v1.data.Member
import com.raza.householdrecharge.v2.common.BaseViewModel
import com.raza.householdrecharge.v2.common.SessionManager
import com.raza.householdrecharge.v2.repository.AppUserDto
import com.raza.householdrecharge.v2.repository.FirestoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel() {
    var members by mutableStateOf<List<Member>>(emptyList())

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

            FirestoreRepository.fetchMembers(
                appUserDto = appUserDto,

                onSuccess = { memberList ->

                    this@DashboardViewModel.members = memberList
                    onSuccess()
                },
                onFailure = { error ->

                    onFailure(error)
                }
            )
        }
    }
}