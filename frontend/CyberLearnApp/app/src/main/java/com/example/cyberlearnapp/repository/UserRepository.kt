package com.example.cyberlearnapp.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.cyberlearnapp.network.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// DataStore name
private val Context.dataStore by preferencesDataStore(name = "user_preferences")

@Singleton
class UserRepository @Inject constructor(
    private val context: Context
) {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    // Guardar token de autenticación
    suspend fun saveAuthToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    // Obtener token de autenticación
    val authToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY]
        }

    // Guardar información del usuario
    suspend fun saveUserInfo(user: User) {
        context.dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = user.email
            preferences[USER_NAME_KEY] = user.name
            preferences[TOKEN_KEY] = user.token
            preferences[IS_LOGGED_IN_KEY] = true
        }
    }

    // Obtener información del usuario
    val userInfo: Flow<User?> = context.dataStore.data
        .map { preferences ->
            val email = preferences[USER_EMAIL_KEY]
            val name = preferences[USER_NAME_KEY]
            val token = preferences[TOKEN_KEY]

            if (email != null && name != null && token != null) {
                User(email = email, name = name, token = token)
            } else {
                null
            }
        }

    // Verificar si el usuario está logueado
    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    // Limpiar todos los datos (logout)
    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    // Obtener token de forma síncrona (para casos especiales)
    suspend fun getAuthTokenSync(): String? {
        return context.dataStore.data
            .map { it[TOKEN_KEY] }
            .firstOrNull()
    }
}