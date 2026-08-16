package com.raza.householdrecharge.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RechargeRequestDao {

    @Query("SELECT * FROM recharge_requests WHERE membersId = :memberId AND completedAt IS NULL ORDER BY requestedAt DESC LIMIT 1")
    fun observeActiveRequest(memberId: Long): Flow<RechargeRequestEntity>

    @Insert
    suspend fun insert(request: RechargeRequestEntity)

    @Query("UPDATE recharge_requests SET completedAt = :completedAt WHERE id = :requestId")
    suspend fun completeRequest(requestId: Long, completedAt: Long)
}