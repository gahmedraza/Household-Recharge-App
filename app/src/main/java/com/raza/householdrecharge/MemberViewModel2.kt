package com.raza.householdrecharge

import androidx.lifecycle.ViewModel
import com.raza.householdrecharge.data.Member
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Deprecated("MemberViewModel is latest")
class MemberViewModel2: ViewModel() {

    private val _members = MutableStateFlow(
        listOf(
            Member(
                id = 1,
                name = "Rahul",
                mobileNumber = "9876543210",
                planDurationDays = 28,
                lastRechargeDate = null,
                planExpiryDate = null
            ),
            Member(
                id = 2,
                name = "Payal",
                mobileNumber = "9876543211",
                planDurationDays = 28,
                lastRechargeDate = null,
                planExpiryDate = null
            )
        )
    )

    val members: StateFlow<List<Member>> = _members.asStateFlow()

    fun requestRecharge(memberId: Long) {
        _members.value = _members.value.map { member ->
            if(member.id == memberId) {
                member.copy(rechargeRequested = true)
            } else {
                member
            }
        }
    }

    fun markRechargeDone(memberId: Long) {
        val today = System.currentTimeMillis()

        _members.value = _members.value.map { member ->
            if(member.id == memberId) {
                member.copy(
                    lastRechargeDate = today,
                    planExpiryDate =
                        today + (member.planDurationDays * 24L * 60 * 60 * 1000),
                    rechargeRequested = false
                )
            } else {
                member
            }
        }
    }
}