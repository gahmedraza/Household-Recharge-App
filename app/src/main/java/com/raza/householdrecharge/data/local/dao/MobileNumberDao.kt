package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MobileNumberDao {
    @Upsert
    suspend fun upsertMobileNumber(mobileNumber: MobileNumberEntity)

    @Upsert
    suspend fun upsertMobileNumbers(mobileNumberList: List<MobileNumberEntity>)

    @Query("select * from mobile_numbers")
    fun observeMobileNumbers(): Flow<List<MobileNumberEntity>>

    @Query("update mobile_numbers set lastRechargeId = :lastRechargeId where id = :id")
    fun updateMobileNumber(id: String, lastRechargeId: String)
}