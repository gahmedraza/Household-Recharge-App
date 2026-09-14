package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.RechargeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RechargeDao {

    @Upsert
    suspend fun upsertRecharge(rechargeEntity: RechargeEntity)

    @Upsert
    suspend fun upsertRecharges(rechargeEntityList: List<RechargeEntity>)

    @Query("select * from recharges")
    fun observeRecharges(): Flow<List<RechargeEntity>>
}