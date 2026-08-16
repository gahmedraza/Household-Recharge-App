package com.raza.householdrecharge

import com.raza.householdrecharge.data.MemberEntity
import com.raza.householdrecharge.data.RechargeRequestDao
import com.raza.householdrecharge.data.RechargeRequestEntity
import com.raza.householdrecharge.data.UserPreferences
import com.raza.householdrecharge.data.UserRole
import kotlinx.coroutines.flow.Flow

class MemberRepository(private val memberDao: MemberDao,
                       private val rechargeRequestDao: RechargeRequestDao,
                       private val userPreferences: UserPreferences) {

    val userRole: Flow<UserRole?> = userPreferences.userRole

    suspend fun setUserRole(role: UserRole) = userPreferences.setUserRole(role)

    fun observerMembers(householdId: Long): Flow<List<MemberEntity>> = memberDao.observeMembers(householdId)

    suspend fun insert(member: MemberEntity) = memberDao.insert(member)

    fun observeActiveRequest(memberId: Long): Flow<RechargeRequestEntity?> = rechargeRequestDao.observeActiveRequest(memberId)

    suspend fun requestRecharge(memberId: Long) {
        rechargeRequestDao.insert(
            RechargeRequestEntity(
                membersId = memberId,
                requestedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun markRechargeDone(id: Long, rechargeDate: Long, expiryDate: Long) =
        memberDao.markRechargeDone(id = id, rechargeDate = rechargeDate, expiryDate = expiryDate)
}