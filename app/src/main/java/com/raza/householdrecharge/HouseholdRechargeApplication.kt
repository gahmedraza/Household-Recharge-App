package com.raza.householdrecharge

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raza.householdrecharge.data.UserPreferences
import com.raza.householdrecharge.notification.AppNotificationManager

class HouseholdRechargeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "household_recharge.db"
        ).addMigrations(
            MIGRATION_1_2,
            MIGRATIONS_2_3,
            MIGRATION_3_4,
            MIGRATION_4_5
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

    override fun onCreate() {
        super.onCreate()

        AppNotificationManager.createChannel(this)
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

private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
               CREATE TABLE members_new (
                 id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                 householdId INTEGER NOT NULL,
                 name TEXT NOT NULL,
                 mobileNumber TEXT NOT NULL,
                 planDurationDays INTEGER NOT NULL,
                 lastRechargeDate INTEGER,
                 planExpiryDate INTEGER
                )
                """
        )

        database.execSQL(
            """
                INSERT INTO members_new (
                id, householdId, name, mobileNumber,planDurationDays
                lastRechargeDate, planExpiryDate
                )
                SELECT
                id, householdId, name, mobileNumber, planDurationDays, 
                lastRechargeDate, planExpiryDate FROM members
            """
        )

        database.execSQL("DROP TABLE members")
        database.execSQL("ALTER TABLE members_new RENAME TO members")
    }

    private val MIGRATION_4_5 = object: Migration(4,5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                """
                   ALTER TABLE recharge_requests
                    ADD COLUMN status TEXT NOT NULL DEFAULT 'PENDING'
                """
            )
        }
    }
}