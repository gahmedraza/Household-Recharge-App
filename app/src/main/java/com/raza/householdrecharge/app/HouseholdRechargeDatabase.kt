package com.raza.householdrecharge.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity

@Database(
    entities =[
        MobileNumberEntity::class
    ],
    version=1,
    exportSchema = false
)
abstract class HouseholdRechargeDatabase: RoomDatabase() {
    abstract fun mobileNumberDao(): MobileNumberDao
}