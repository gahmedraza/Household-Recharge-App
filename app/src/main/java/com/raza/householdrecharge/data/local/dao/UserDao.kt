package com.raza.householdrecharge.data.local.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.raza.householdrecharge.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertUser(userEntity: UserEntity)
}