package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MobileNumberDao {

    @Insert
    suspend fun insertMobileNumber(mobileNumber: MobileNumberEntity)

    @Insert
    suspend fun insertMobileNumbers(mobileNumberList: List<MobileNumberEntity>)

    @Update
    suspend fun updateMobileNumber(mobileNumber: MobileNumberEntity)

    @Delete
    suspend fun deleteMobileNumber(mobileNumber: MobileNumberEntity)

    @Query("select * from mobile_numbers where id = :id")
    suspend fun getMobileNumbersById(id: Long): MobileNumberEntity

    @Query("select * from mobile_numbers")
    suspend fun getAllMobileNumbers(): List<MobileNumberEntity>

    @Query("select * from mobile_numbers")
    fun observeMobileNumbers(): Flow<List<MobileNumberEntity>>
}