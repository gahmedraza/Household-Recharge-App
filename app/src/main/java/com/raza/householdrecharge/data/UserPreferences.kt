package com.raza.householdrecharge.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferences(
    private val context: Context
) {
    private object Keys {
        val USER_ROLE = stringPreferencesKey("user_role")
    }

    val userRole: Flow<UserRole?> =
        context.datastore.data.map { preferences ->
            preferences[Keys.USER_ROLE]?.let {
                UserRole.valueOf(it)
            }
        }

    suspend fun setUserRole(role: UserRole) {
        context.datastore.edit { preferences ->
            preferences[Keys.USER_ROLE] = role.name
        }
    }
}