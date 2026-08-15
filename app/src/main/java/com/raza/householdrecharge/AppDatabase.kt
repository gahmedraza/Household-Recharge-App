package com.raza.householdrecharge

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raza.householdrecharge.data.MemberEntity

@Database(
    entities = [MemberEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
}