package com.example.frontend.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

data class UserIdPref(val id:String)

class UserIdRepo(private val context: Context) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

    private object PreferenceKeys{
        val userIdKey = stringPreferencesKey("userid")
    }
    val userPreferencesFlow: Flow<UserIdPref> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { settings ->
            val userKey = settings[PreferenceKeys.userIdKey]?: ""
            UserIdPref(userKey)
        }
    suspend fun update(userId: String) {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.userIdKey] = userId
        }
    }

    companion object {
        // Constant for naming our DataStore - you can change this if you want
        private const val USER_PREFERENCES_NAME = "user_preferences"

        // The usual for debugging
        private val TAG: String = "UserIdRepo"

        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: UserIdRepo? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         * @param context to init the datastore
         */
        fun getInstance(context: Context): UserIdRepo {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserIdRepo(context)
                INSTANCE = instance
                instance
            }
        }
    }

}