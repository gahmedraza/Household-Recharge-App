package com.raza.householdrecharge

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.data.HouseholdEntity
import com.raza.householdrecharge.data.MemberEntity
import com.raza.householdrecharge.data.RechargeRequestDao
import com.raza.householdrecharge.data.RechargeRequestEntity

@Database(
    entities = [
        HouseholdEntity::class,
        MemberEntity::class,
        RechargeRequestEntity::class
               ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    abstract fun rechargeRequestDao(): RechargeRequestDao
}