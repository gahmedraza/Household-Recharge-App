package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.AccountEntity

@Dao
interface AccountDao {

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Query("update accounts set householdId = :householdId where accountId = :accountId")
    fun updateAccount(accountId: String, householdId: String)
}