package com.raza.householdrecharge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raza.householdrecharge.data.MemberEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MemberViewModel(
    private val repository: MemberRepository
) : ViewModel() {

    val members: StateFlow<List<MemberEntity>> =
        repository.observerMembers()
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

    fun markRechargeDone(id: Long, planDurationDays: Int) {
        viewModelScope.launch {
            val rechargeDate = System.currentTimeMillis()

            val expiryDate = rechargeDate +
                    planDurationDays * 24L * 60L * 60L * 1000L

            repository.markRechargeDone(
                id = id,
                rechargeDate = rechargeDate,
                expiryDate = expiryDate
            )
        }
    }

    class Factory(
        private val repository: MemberRepository
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T: ViewModel> create(
            modelCLass: Class<T>
        ): T {
            return MemberViewModel(repository) as T
        }
    }
}