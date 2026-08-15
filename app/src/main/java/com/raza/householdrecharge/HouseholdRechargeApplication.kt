package com.raza.householdrecharge

import android.app.Application
import androidx.room.Room

class HouseholdRechargeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this, AppDatabase::class.java,
            "household_recharge.db"
        ).build()
    }

    val repository by lazy {
        MemberRepository(database.memberDao())
    }
}