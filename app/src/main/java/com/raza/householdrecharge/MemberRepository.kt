package com.raza.householdrecharge

import com.raza.householdrecharge.data.MemberEntity
import kotlinx.coroutines.flow.Flow

class MemberRepository(private val memberDao: MemberDao) {
    fun observerMembers(): Flow<List<MemberEntity>> = memberDao.observeMembers()

    suspend fun insert(member: MemberEntity) = memberDao.insert(member)

    suspend fun requestRecharge(id: Long) = memberDao.requestRecharge(id)

    suspend fun markRechargeDone(id: Long, rechargeDate: Long, expiryDate: Long) =
        memberDao.markRechargeDone(id = id, rechargeDate = rechargeDate, expiryDate = expiryDate)
}