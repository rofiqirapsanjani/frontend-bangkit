package com.example.projectcapstone.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[NAME_KEY] ?: "",
                it[EMAIL_KEY] ?: "",
                it[PASSWORD_KEY] ?: "",
                it[USERID_KEY] ?: "",
                it[TOKEN_KEY] ?: "",
                it[STATE_KEY] ?: false
            )
        }
    }

    suspend fun getPreferences():Preferences{
        return dataStore.data.first()

    }

    suspend fun saveUser(user: UserModel) {
        dataStore.edit {
            it[NAME_KEY] = user.name
            it[EMAIL_KEY] = user.email
            it[PASSWORD_KEY] = user.password
            it[USERID_KEY] = user.userId
            it[TOKEN_KEY] = user.token
            it[STATE_KEY] = user.isLogin
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[STATE_KEY] = false
            it[NAME_KEY] = ""
            it[EMAIL_KEY] = ""
            it[USERID_KEY] = ""
            it[TOKEN_KEY] = ""
            it[PASSWORD_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        val NAME_KEY = stringPreferencesKey("name")
        val EMAIL_KEY = stringPreferencesKey("email")
        val PASSWORD_KEY = stringPreferencesKey("password")
        val USERID_KEY = stringPreferencesKey("userId")
        val TOKEN_KEY = stringPreferencesKey("token")
        val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

//    fun getUser(): Flow<UserModel> {
//        return dataStore.data.map {
//            UserModel(
//                it[USERNAME_KEY] ?: "",
//                it[EMAIL_KEY] ?: "",
//                it[PASSWORD_KEY] ?: "",
//                it[ISADMIN_KEY] ?: true,
//                it[_ID_KEY] ?: "",
//                it[CREATEDAT_KEY] ?: "",
//                it[UPDATEDAT_KEY] ?: "",
//                it[__V_KEY] ?: 0
//            )
//        }
//    }
//
//    suspend fun saveUser(user: UserModel) {
//        dataStore.edit {
//            it[USERNAME_KEY] = user.username
//            it[EMAIL_KEY] = user.email
//            it[PASSWORD_KEY] = user.password
//            it[ISADMIN_KEY] = user.isAdmin
//            it[_ID_KEY] = user._id
//            it[CREATEDAT_KEY] = user.createdAt
//            it[UPDATEDAT_KEY] = user.updatedAt
//            it[__V_KEY] = user.__v
//        }
//    }
//
//
//    suspend fun logout() {
//        dataStore.edit {
//            it[USERNAME_KEY] = ""
//            it[EMAIL_KEY] = ""
//            it[PASSWORD_KEY] = ""
//            it[ISADMIN_KEY] = false
//            it[_ID_KEY] = ""
//            it[CREATEDAT_KEY] = ""
//            it[UPDATEDAT_KEY] = ""
//            it[__V_KEY] = 0
//        }
//    }
//
//    suspend fun login() {
//        dataStore.edit { preferences ->
//            preferences[ISADMIN_KEY] = true
//        }
//    }
//
//
//    companion object {
//        @Volatile
//        private var INSTANCE: UserPreference? = null
//        private val USERNAME_KEY = stringPreferencesKey("username")
//        private val EMAIL_KEY = stringPreferencesKey("email")
//        private val PASSWORD_KEY = stringPreferencesKey("password")
//        private val ISADMIN_KEY = booleanPreferencesKey("isAdmin")
//        private val _ID_KEY = stringPreferencesKey("_id")
//        private val CREATEDAT_KEY = stringPreferencesKey("createdAt")
//        private val UPDATEDAT_KEY = stringPreferencesKey("updatedAt")
//        private val __V_KEY = intPreferencesKey("__v")
//
//        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
//            return INSTANCE ?: synchronized(this) {
//                val instance = UserPreference(dataStore)
//                INSTANCE = instance
//                instance
//            }
//        }
//    }

}