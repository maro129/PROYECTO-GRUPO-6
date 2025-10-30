package com.example.cyberlearnapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyberlearnapp.ui.theme.*
import com.example.cyberlearnapp.ui.components.ProgressCard
import com.example.cyberlearnapp.ui.components.CourseCard
import com.example.cyberlearnapp.viewmodel.UserViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun DashboardScreen(
    userViewModel: UserViewModel,
    onCourseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val userProgress by userViewModel.userProgress.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
    ) {
        // Mostrar loading mientras carga
        if (isLoading || userProgress == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AccentCyan)
            }
            return
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "CyberLearn",
                style = MaterialTheme.typography.headlineLarge,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Aprende. Hackea. Protege.",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentCyan,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Tarjeta de Bienvenida CON DATOS REALES
            ProgressCard(
                progress = userProgress!!,  // ← DATOS REALES del backend
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Cursos en progreso
            Text(
                text = "Continúa aprendiendo",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CourseCard(
                title = "Criptografía Básica",
                description = "Principiante • +120 XP",
                progress = 0.65f,
                onClick = { onCourseClick("Criptografía Básica") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CourseCard(
                title = "Ethical Hacking",
                description = "Intermedio • +80 XP",
                progress = 0.30f,
                onClick = { onCourseClick("Ethical Hacking") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CourseCard(
                title = "IA para Seguridad",
                description = "Avanzado",
                progress = 0f,
                onClick = { onCourseClick("IA para Seguridad") }
            )
        }
    }
}