package com.mastutor.tutoreal.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mastutor.tutoreal.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionPreferences @Inject constructor(private val dataStore: DataStore<Preferences>){
    val userExist = booleanPreferencesKey(Constants.USER_EXIST)
    val userToken = stringPreferencesKey(Constants.USER_TOKEN)

    fun getUserExist(): Flow<Boolean> {
        return dataStore.data.map {
            it[userExist] ?: false
        }
    }

    fun getUserToken(): Flow<String>{
        return dataStore.data.map {
            it[userToken] ?: ""
        }
    }

    suspend fun startSession(
        userChecked: Boolean,
        tokenChecked: String
    ){
        dataStore.edit {
            it[userExist] = userChecked
            it[userToken] = tokenChecked
        }
    }
    suspend fun deleteSession(){
        dataStore.edit {
            it.clear()
        }
    }
}