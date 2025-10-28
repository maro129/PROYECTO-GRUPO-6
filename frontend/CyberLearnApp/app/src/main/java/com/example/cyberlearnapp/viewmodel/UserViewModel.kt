package com.example.cyberlearnapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyberlearnapp.network.ApiService
import com.example.cyberlearnapp.network.models.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _userProgress = MutableStateFlow<Progress?>(null)
    val userProgress: StateFlow<Progress?> = _userProgress

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUserProgress(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = apiService.getUserProgress(token)
                if (response.isSuccessful) {
                    _userProgress.value = response.body()
                }
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }
}