package com.raza.householdrecharge.v1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RechargeRequestDao {

    @Query("""
           SELECT * FROM recharge_requests 
                WHERE membersId = :memberId 
                AND status = 'PENDING' 
                ORDER BY requestedAt 
                DESC LIMIT 1 
        """)
    fun observeActiveRequest(memberId: Long): Flow<RechargeRequestEntity?>

    @Insert
    suspend fun insert(request: RechargeRequestEntity)

    @Query("""
        UPDATE recharge_requests 
            SET completedAt = :completedAt, 
            status = 'COMPLETED' 
            WHERE id = :requestId
    """)
    suspend fun completeRequest(requestId: Long, completedAt: Long)

    @Query(
        """
        SELECT * FROM recharge_requests
        WHERE membersId = :memberId
        ORDER BY requestedAt DESC
    """
    )
    fun observeRequestHistory(memberId: Long): Flow<List<RechargeRequestEntity>>
}