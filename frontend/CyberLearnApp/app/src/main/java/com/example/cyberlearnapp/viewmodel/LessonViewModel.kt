package com.example.cyberlearnapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyberlearnapp.network.ApiService
import com.example.cyberlearnapp.network.models.CompleteActivityRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LessonUiState(
    val lessonTitle: String = "",
    val lessonContent: String = "",
    val isCompleting: Boolean = false,
    val xpEarned: Int = 0,
    val errorMessage: String? = null
)

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    /*var uiState by mutableStateOf(LessonUiState())
        private set*/
    private val _uiState = MutableStateFlow(LessonUiState())
    val uiState: StateFlow<LessonUiState> = _uiState.asStateFlow()

    fun loadLessonContent(lessonId: String) {
        viewModelScope.launch {
            // Por ahora contenido estático
            _uiState.value = _uiState.value.copy( // Accedemos con .value
                lessonTitle = when (lessonId) {
                    "crypto_1" -> "Introducción a Criptografía"
                    "crypto_2" -> "Tipos de Encriptación"
                    else -> "Lección $lessonId"
                },
                lessonContent = "Este es el contenido de la lección $lessonId..."
            )
        }
    }

    fun completeLesson(lessonId: String, difficulty: Int = 1, onCompleted: (Int) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCompleting = true, errorMessage = null)
            try {
                // ✅ SOLUCIÓN TEMPORAL: Usar el token que sabemos que funciona
                val token = "Bearer token-test@ejemplo.com"

                val response = apiService.completeActivity(
                    token = token,
                    request = CompleteActivityRequest(
                        type = "lesson_completed",
                        lessonId = lessonId,
                        difficulty = difficulty
                    )
                )

                if (response.isSuccessful) {
                    val xpEarned = response.body()?.activityResult?.xpEarned ?: 0
                    _uiState.value = _uiState.value.copy(xpEarned = xpEarned, isCompleting = false)
                    onCompleted(xpEarned)
                } else {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Error al completar lección: ${response.code()}",
                        isCompleting = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Error de conexión: ${e.message}",
                    isCompleting = false
                )
            }
        }
    }

    private fun getToken(): String {
        // Temporal - Persona 4 implementará la persistencia real
        return "token-usuario@ejemplo.com"
    }
}
