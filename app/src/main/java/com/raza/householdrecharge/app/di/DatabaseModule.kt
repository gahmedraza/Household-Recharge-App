package com.raza.householdrecharge.app.di

import android.content.Context
import androidx.room.Room
import com.raza.householdrecharge.app.HouseholdRechargeDatabase
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): HouseholdRechargeDatabase {

        return Room.databaseBuilder(
            context = context,
            HouseholdRechargeDatabase::class.java,
            "household_recharge.db"
        ).build()
    }

    @Provides
    fun provideMobileNumberDao(
        database: HouseholdRechargeDatabase
    ): MobileNumberDao {

        return database.mobileNumberDao()
    }
}