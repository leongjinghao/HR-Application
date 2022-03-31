package com.example.frontend.Utilities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.*
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore : DataStore<Preferences> by
                                preferencesDataStore("card_details")

class CardDetailsManager (context : Context) {
    private val mDataStore : DataStore<Preferences> = context.datastore

    companion object {
        val NAME_KEY = stringPreferencesKey("CARD_NAME")
        val DEPARTMENT_KEY = stringPreferencesKey("CARD_DEPARTMENT")
        val PHONE_NUMBER_KEY = stringPreferencesKey("CARD_PHONE")
        val OFFICE_NUMBER_KEY = stringPreferencesKey("CARD_OFFICE")
        val EMAIL_KEY = stringPreferencesKey("CARD_EMAIL")
        val WEBSITE_KEY = stringPreferencesKey("CARD_WEBSITE")
    }

    suspend fun storeDetails(name : String, department : String, phone : String, office : String,
                             email : String, website : String) {
        mDataStore.edit { preferences->
            preferences[NAME_KEY] = name
            preferences[DEPARTMENT_KEY] = department
            preferences[PHONE_NUMBER_KEY] = phone
            preferences[OFFICE_NUMBER_KEY] = office
            preferences[EMAIL_KEY] = email
            preferences[WEBSITE_KEY] = website

        }
    }

    val nameFlow : Flow<String> = mDataStore.data.map {
        it[NAME_KEY] ?: ""
    }

    val departmentFlow : Flow<String> = mDataStore.data.map {
        it[DEPARTMENT_KEY] ?: ""
    }

    val phoneFlow : Flow<String> = mDataStore.data.map {
        it[PHONE_NUMBER_KEY] ?: ""
    }

    val officeFlow : Flow<String> = mDataStore.data.map {
        it[OFFICE_NUMBER_KEY] ?: ""
    }

    val emailFlow : Flow<String> = mDataStore.data.map {
        it[EMAIL_KEY] ?: ""
    }

    val websiteFlow : Flow<String> = mDataStore.data.map {
        it[WEBSITE_KEY] ?: ""
    }
}