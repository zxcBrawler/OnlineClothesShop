package com.example.onlineshoppoizon.response

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.clear
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map


class UserPreferences (
    context: Context
) {
    private val applicationContext = context.applicationContext

    private val dataStore : DataStore<Preferences>
    = applicationContext.createDataStore(name = "my_data_store")


    val authToken: Flow<String?>
    get() = dataStore.data.map { it ->
          it[KEY_AUTH]
    }

    val userId: Flow<Int?>
        get() = dataStore.data.map { it ->
            it[USER_ID]

        }


    suspend fun saveAuthToken(token: String, id : Int){
        dataStore.edit { it ->
            it[KEY_AUTH] = token
            it[USER_ID] = id
        }
    }

    fun get() : Flow<Int>{
        return  dataStore.data
            .map {
                val id = it[USER_ID] ?: -1
                id


            }
    }
    fun getToken() : Flow<String>{
        return  dataStore.data
            .map {
                val token = it[KEY_AUTH] ?: ""
                token
            }
    }


    suspend fun clear(){
        dataStore.edit { it.clear() }
    }

    companion object {
        val KEY_AUTH =  preferencesKey<String>("key_auth")
        val USER_ID =  preferencesKey<Int>("user_id")
    }
}