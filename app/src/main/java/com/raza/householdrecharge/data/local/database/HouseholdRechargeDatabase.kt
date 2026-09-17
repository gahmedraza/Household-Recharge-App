package com.raza.householdrecharge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.local.dao.RechargeDao
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.local.entity.RechargeEntity

@Database(
    entities =[
        MobileNumberEntity::class,
        RechargeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HouseholdRechargeDatabase: RoomDatabase() {
    abstract fun mobileNumberDao(): MobileNumberDao
    abstract fun rechargeDao(): RechargeDao
}