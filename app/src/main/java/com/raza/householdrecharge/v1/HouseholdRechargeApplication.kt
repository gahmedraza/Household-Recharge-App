package com.raza.householdrecharge.v1

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import com.raza.householdrecharge.v1.data.UserPreferences
import com.raza.householdrecharge.v1.notification.AppNotificationManager
import com.raza.householdrecharge.v2.common.SessionManager

class HouseholdRechargeApplication : Application() {
    val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "household_recharge.db"
        )
            .setDriver(BundledSQLiteDriver())
            .addMigrations(
            MIGRATION_1_2,
            MIGRATIONS_2_3,
            MIGRATION_3_4,
            MIGRATION_4_5,
            MIGRATION_5_6
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

    val sessionManager by lazy {
        SessionManager(this)
    }

    override fun onCreate() {
        super.onCreate()

        AppNotificationManager.createChannel(this)
    }
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
                ALTER TABLE members 
                    ADD COLUMN householdID 
                        INTEGER NOT NULL DEFAULT 1
            """.trimIndent()
        )

        connection.execSQL("""
                CREATE TABLE IF NOT EXISTS households (
                    id INTEGER NOT NULL PRIMARY KEY, 
                    name TEXT NOT NULL)
            """.trimIndent()
        )
    }
}

private val MIGRATIONS_2_3 = object : Migration(2, 3) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
               CREATE TABLE IF NOT EXISTS recharge_requests(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    memberId INTEGER NOT NULL,
                    requestedAt INTEGER NOT NULL,
                    completedAt INTEGER)
            """.trimIndent()
        )
    }
}

private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
               CREATE TABLE members_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    householdId INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    mobileNumber TEXT NOT NULL,
                    planDurationDays INTEGER NOT NULL,
                    lastRechargeDate INTEGER,
                    planExpiryDate INTEGER)
                """.trimIndent()
        )

        connection.execSQL("""
                INSERT INTO members_new (
                    id, 
                    householdId, 
                    name, 
                    mobileNumber,
                    planDurationDays,
                    lastRechargeDate, 
                    planExpiryDate
                )
                SELECT
                    id, 
                    householdId, 
                    name, 
                    mobileNumber, 
                    planDurationDays, 
                    lastRechargeDate, 
                    planExpiryDate 
                FROM members
            """.trimIndent()
        )

        connection.execSQL("DROP TABLE members")
        connection.execSQL("ALTER TABLE members_new RENAME TO members")
    }
}

private val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
                   ALTER TABLE recharge_requests
                   ADD COLUMN status TEXT NOT NULL DEFAULT 'PENDING'
                """.trimIndent()
        )
    }
}

private val MIGRATION_5_6 = object: Migration(5,6) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL("""
                ALTER TABLE members
                ADD COLUMN rechargeRequested INTEGER NOT NULL DEFAULT 0
            """.trimIndent()
        )
    }
}