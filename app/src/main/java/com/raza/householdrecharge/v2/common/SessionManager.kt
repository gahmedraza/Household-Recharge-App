package com.raza.householdrecharge.v2.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.datastore by preferencesDataStore("session")

class SessionManager(private val context: Context) {
    val authId = context.datastore.data.map {
        it[stringPreferencesKey("authId")] ?: ""
    }

    val accountId = context.datastore.data.map {
        it[stringPreferencesKey("accountId")] ?: ""
    }

    val householdId = context.datastore.data.map {
        it[stringPreferencesKey("householdId")] ?: ""
    }

    val householdName = context.datastore.data.map {
        it[stringPreferencesKey("householdName")] ?: ""
    }

    val isManager = context.datastore.data.map {
        it[booleanPreferencesKey("isManager")] ?: false
    }

    val memberId = context.datastore.data.map {
        it[stringPreferencesKey("memberId")] ?: ""
    }

    val memberName = context.datastore.data.map {
        it[stringPreferencesKey("memberName")] ?: ""
    }

    val mobileNumber = context.datastore.data.map {
        it[stringPreferencesKey("mobileNumber")] ?: ""
    }

    suspend fun saveUserId(userId: String) {
        context.datastore.edit {
            it[stringPreferencesKey("authId")] = userId
        }
    }

    suspend fun saveAccountId(accountId: String) {
        context.datastore.edit {
            it[stringPreferencesKey("accountId")] = accountId
        }
    }

    suspend fun saveHouseholdId(householdId: String) {
        context.datastore.edit {
            it[stringPreferencesKey("householdId")] = householdId
        }
    }

    suspend fun saveHouseholdName(householdName: String) {
        context.datastore.edit {
            it[stringPreferencesKey("householdName")] = householdName
        }
    }

    suspend fun saveIsManager(isManager: Boolean) {
        context.datastore.edit {
            it[booleanPreferencesKey("isManager")] = isManager
        }
    }

    suspend fun saveMemberId(memberId: String) {
        context.datastore.edit {
            it[stringPreferencesKey("memberId")] = memberId
        }
    }

    suspend fun saveMemberName(memberName: String) {
        context.datastore.edit {
            it[stringPreferencesKey("memberName")] = memberName
        }
    }

    suspend fun saveMobileNumber(mobileNumber: String) {
        context.datastore.edit {
            it[stringPreferencesKey("mobileNumber")] = mobileNumber
        }
    }

    suspend fun clear() {
        context.datastore.edit {
            it.clear()
        }
    }
}