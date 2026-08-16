package com.raza.householdrecharge

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raza.householdrecharge.data.UserPreferences

class HouseholdRechargeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "household_recharge.db"
        ).addMigrations(
            MIGRATION_1_2,
            MIGRATIONS_2_3
        ).build()
    }

    val userPreferences by lazy {
        UserPreferences(this)
    }

    val repository by lazy {
        MemberRepository(
            memberDao = database.memberDao(),
            rechargeRequestDao = database.rechargeRequestDao(),
            userPreferences = userPreferences
        )
    }
}

private val MIGRATION_1_2 = object: Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        super.migrate(database)

        database.execSQL(
            "ALTER TABLE members ADD COLUMN householdID INTEGER NOT NULL DEFAULT 1"
        )

        database.execSQL(
            "CREATE TABLE IF NOT EXISTS households (id INTEGER NOT NULL PRIMARY KEY, name TEXT NOT NULL)"
        )
    }
}

private val MIGRATIONS_2_3 = object: Migration(2,3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
               CREATE TABLE IF NOT EXISTS recharge_requests(
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                memberId INTEGER NOT NULL,
                requestedAt INTEGER NOT NULL,
                completedAt INTEGER)
            """
        )
    }
}