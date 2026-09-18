package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.HouseholdEntity

@Dao
interface HouseholdDao {

    @Upsert
    suspend fun upsertHousehold(householdEntity: HouseholdEntity)
}