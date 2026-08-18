package com.raza.householdrecharge

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.raza.householdrecharge.data.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Query("""
        SELECT * FROM members 
            WHERE householdId = :householdId 
            ORDER BY id
    """)
    fun observeMembers(householdId: Long): Flow<List<MemberEntity>>

    @Insert
    suspend fun insert(member: MemberEntity)

    @Query("""
        UPDATE members 
            SET rechargeRequested = 1 
            WHERE id = :id
    """)
    suspend fun requestRecharge(id: Long)

    @Query(""" 
        UPDATE members 
            SET lastRechargeDate = :rechargeDate, 
                planExpiryDate = :expiryDate, 
                rechargeRequested = 0 
                WHERE id = :id 
        """)
    suspend fun markRechargeDone(id: Long, rechargeDate: Long, expiryDate: Long)
}