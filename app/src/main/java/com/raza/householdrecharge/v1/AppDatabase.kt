package com.raza.householdrecharge.v1

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.v1.data.HouseholdEntity
import com.raza.householdrecharge.v1.data.MemberEntity
import com.raza.householdrecharge.v1.data.RechargeRequestDao
import com.raza.householdrecharge.v1.data.RechargeRequestEntity

@Database(
    entities = [
        HouseholdEntity::class,
        MemberEntity::class,
        RechargeRequestEntity::class
    ],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun rechargeRequestDao(): RechargeRequestDao
}