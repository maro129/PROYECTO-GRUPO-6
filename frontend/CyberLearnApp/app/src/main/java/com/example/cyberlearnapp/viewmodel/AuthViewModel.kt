package com.example.cyberlearnapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyberlearnapp.network.ApiService
import com.example.cyberlearnapp.network.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.cyberlearnapp.repository.UserRepository
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        // Cargar usuario almacenado al iniciar
        loadStoredUser()
    }

    fun loadStoredUser() {
        viewModelScope.launch {
            userRepository.userInfo.collect { user ->
                _currentUser.value = user
            }
        }
    }

    fun register(email: String, password: String, name: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = apiService.register(
                    com.example.cyberlearnapp.network.RegisterRequest(email, password, name)
                )

                if (response.isSuccessful && response.body()?.success == true) {
                    val userData = response.body()?.user
                    val token = response.body()?.token ?: ""

                    if (userData != null) {
                        val user = User(
                            email = userData.email,
                            name = userData.name,
                            token = token
                        )

                        // GUARDAR EN REPOSITORIO (PERSISTENCIA)
                        userRepository.saveUserInfo(user)
                        _currentUser.value = user
                    }
                } else {
                    _errorMessage.value = response.body()?.message ?: "Error en el registro"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = apiService.login(
                    com.example.cyberlearnapp.network.LoginRequest(email, password)
                )

                if (response.isSuccessful && response.body()?.success == true) {
                    val userData = response.body()?.user
                    val token = response.body()?.token ?: ""

                    if (userData != null) {
                        val user = User(
                            email = userData.email,
                            name = userData.name,
                            token = token
                        )

                        // GUARDAR EN REPOSITORIO (PERSISTENCIA)
                        userRepository.saveUserInfo(user)
                        _currentUser.value = user
                    }
                } else {
                    _errorMessage.value = response.body()?.message ?: "Error en el login"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            // LIMPIAR DATOS DEL REPOSITORIO
            userRepository.clearUserData()
            _currentUser.value = null
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}