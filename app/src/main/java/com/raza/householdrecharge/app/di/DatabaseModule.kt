package com.raza.householdrecharge.app.di

import android.content.Context
import androidx.room.Room
import com.raza.householdrecharge.data.local.dao.AccountDao
import com.raza.householdrecharge.data.local.dao.HouseholdDao
import com.raza.householdrecharge.data.local.dao.InvitationDao
import com.raza.householdrecharge.data.local.dao.MobileNumberDao
import com.raza.householdrecharge.data.local.dao.RechargeDao
import com.raza.householdrecharge.data.local.dao.UserDao
import com.raza.householdrecharge.data.local.database.HouseholdRechargeDatabase
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

    @Provides
    fun provideRechargeDao(
        database: HouseholdRechargeDatabase
    ): RechargeDao {

        return database.rechargeDao()
    }

    @Provides
    fun provideAccountDao(
        database: HouseholdRechargeDatabase
    ): AccountDao {

        return database.accountDao()
    }

    @Provides
    fun provideInvitationDao(
        database: HouseholdRechargeDatabase
    ): InvitationDao {

        return database.invitationDao()
    }

    @Provides
    fun provideHouseholdDao(
        database: HouseholdRechargeDatabase
    ): HouseholdDao {

        return database.householdDao()
    }

    @Provides
    fun provideUserDao(
        database: HouseholdRechargeDatabase
    ): UserDao {

        return database.userDao()
    }
}