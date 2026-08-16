package com.raza.householdrecharge

import android.app.Application
import androidx.room.Room
import com.raza.householdrecharge.data.UserPreferences

class HouseholdRechargeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this, AppDatabase::class.java,
            "household_recharge.db"
        ).build()
    }

    val userPreferences by lazy {
        UserPreferences(this)
    }

    val repository by lazy {
        MemberRepository(database.memberDao(), userPreferences)
    }
}