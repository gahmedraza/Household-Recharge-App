package com.raza.householdrecharge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.data.local.dao.AccountDao
import com.raza.householdrecharge.data.local.dao.HouseholdDao
import com.raza.householdrecharge.data.local.dao.InvitationDao
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.local.dao.RechargeDao
import com.raza.householdrecharge.data.local.dao.UserDao
import com.raza.householdrecharge.data.local.entity.AccountEntity
import com.raza.householdrecharge.data.local.entity.HouseholdEntity
import com.raza.householdrecharge.data.local.entity.InvitationEntity
import com.raza.householdrecharge.data.local.entity.MobileNumberEntity
import com.raza.householdrecharge.data.local.entity.RechargeEntity
import com.raza.householdrecharge.data.local.entity.UserEntity

@Database(
    entities =[
        MobileNumberEntity::class,
        RechargeEntity::class,
        AccountEntity::class,
        InvitationEntity::class,
        HouseholdEntity::class,
        UserEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HouseholdRechargeDatabase: RoomDatabase() {
    abstract fun mobileNumberDao(): MobileNumberDao
    abstract fun rechargeDao(): RechargeDao
    abstract fun accountDao(): AccountDao
    abstract fun invitationDao(): InvitationDao
    abstract fun householdDao(): HouseholdDao
    abstract fun userDao(): UserDao
}