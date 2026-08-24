package com.raza.householdrecharge.v2

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.datastore by preferencesDataStore("session")

class SessionManager(private val context: Context) {
    val userId = context.datastore.data.map {
        it[stringPreferencesKey("userId")] ?: ""
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

    suspend fun saveUserId(userId: String) {
        context.datastore.edit {
            it[stringPreferencesKey("userId")] = userId
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

    suspend fun clear() {
        context.datastore.edit {
            it.clear()
        }
    }
}