package com.raza.householdrecharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.MemberEntity
import com.raza.householdrecharge.data.RechargeRequestEntity
import com.raza.householdrecharge.data.UserRole
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemberViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    private val householdId = 1L

    val members: StateFlow<List<MemberEntity>> =
        repository.observerMembers(householdId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun requestRecharge(id: Long) {
        viewModelScope.launch {
            repository.requestRecharge(id)
        }
    }

    fun markRechargeDone(id: Long, planDurationDays: Int, requestId: Long?) {
        viewModelScope.launch {
            val rechargeDate = System.currentTimeMillis()

            val expiryDate = java.time.Instant
                .ofEpochMilli(rechargeDate)
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate()
                .plusDays(planDurationDays.toLong())
                .atStartOfDay(java.time.ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

            repository.markRechargeDone(
                id = id,
                rechargeDate = rechargeDate,
                expiryDate = expiryDate
            )

            requestId?.let {
                repository.completeRechargeRequest(
                    requestId = it,
                    completedAt = rechargeDate
                )
            }
        }
    }

    class Factory(
        private val repository: MemberRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelCLass: Class<T>
        ): T {
            return MemberViewModel(repository) as T
        }
    }

    fun addMember(
        name: String,
        mobileNumber: String,
        planDurationDays: Int
    ) {
        viewModelScope.launch {
            repository.insert(
                MemberEntity(
                    householdId = householdId,
                    name = name,
                    mobileNumber = mobileNumber,
                    planDurationDays = planDurationDays,
                    lastRechargeDate = null,
                    planExpiryDate = null
                )
            )
        }
    }

    val userRole: StateFlow<UserRole?> =
        repository.userRole
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )

    fun setUserRole(role: UserRole) {
        viewModelScope.launch {
            repository.setUserRole(role)
        }
    }

    fun observeActiveRequest(memberId: Long): StateFlow<RechargeRequestEntity?> {
        return repository.observeActiveRequest(memberId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = null
            )
    }

    fun observeRequestHistory(memberId: Long): StateFlow<List<RechargeRequestEntity>> {
        return repository
            .observeRequestHistory(memberId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
    }
}