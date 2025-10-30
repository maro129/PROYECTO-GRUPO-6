package com.example.cyberlearnapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.cyberlearnapp.ui.theme.*
import com.example.cyberlearnapp.viewmodel.LessonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    lessonId: String,
    courseName: String,
    onLessonCompleted: (xpEarned: Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: LessonViewModel = hiltViewModel()
    //val uiState by viewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(lessonId) {
        viewModel.loadLessonContent(lessonId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(courseName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Text("←")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Contenido de la lección
            Text(
                text = "Lección: ${uiState.lessonTitle}",
                style = MaterialTheme.typography.headlineMedium,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = uiState.lessonContent.ifEmpty { "Contenido de la lección..." },
                style = MaterialTheme.typography.bodyLarge,
                color = TextGray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para completar lección
            Button(
                onClick = {
                    viewModel.completeLesson(lessonId, difficulty = 2) { xpEarned ->
                        onLessonCompleted(xpEarned)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentCyan)
            ) {
                Text(
                    text = if (uiState.isCompleting) "Completando..." else "Completar Lección",
                    color = TextWhite
                )
            }

            // Mostrar XP ganado
            if (uiState.xpEarned > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎉 ", style = MaterialTheme.typography.headlineMedium)
                        Text(
                            text = "¡Ganaste ${uiState.xpEarned} XP!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Success,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
