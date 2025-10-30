package com.example.cyberlearnapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyberlearnapp.ui.theme.*
import com.example.cyberlearnapp.ui.components.CourseCard

@Composable
fun CoursesScreen(
    onCourseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Por ahora datos mock - luego conectaremos con ViewModel
    val courses = listOf(
        CourseItem(
            id = "crypto_basics",
            title = "Criptografía Básica",
            description = "Principiante • +120 XP",
            progress = 0.65f,
            level = "Principiante",
            xpReward = 120,
            lessonsTotal = 5,
            lessonsCompleted = 3,
            image = "🔐"
        ),
        CourseItem(
            id = "ethical_hacking",
            title = "Ethical Hacking",
            description = "Intermedio • +80 XP",
            progress = 0.30f,
            level = "Intermedio",
            xpReward = 80,
            lessonsTotal = 8,
            lessonsCompleted = 2,
            image = "🛡️"
        ),
        CourseItem(
            id = "ai_security",
            title = "IA para Seguridad",
            description = "Avanzado • +150 XP",
            progress = 0f,
            level = "Avanzado",
            xpReward = 150,
            lessonsTotal = 6,
            lessonsCompleted = 0,
            image = "🤖"
        ),
        CourseItem(
            id = "malware_analysis",
            title = "Análisis de Malware",
            description = "Avanzado • +200 XP",
            progress = 0f,
            level = "Avanzado",
            xpReward = 200,
            lessonsTotal = 7,
            lessonsCompleted = 0,
            image = "🦠",
            locked = true
        ),
        CourseItem(
            id = "cloud_security",
            title = "Seguridad en la Nube",
            description = "Intermedio • +100 XP",
            progress = 0f,
            level = "Intermedio",
            xpReward = 100,
            lessonsTotal = 4,
            lessonsCompleted = 0,
            image = "☁️",
            locked = true
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "Cursos Disponibles",
                style = MaterialTheme.typography.headlineLarge,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Domina la ciberseguridad paso a paso",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentCyan,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Cursos en progreso
            Text(
                text = "En progreso",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            courses.filter { it.progress > 0 }.forEach { course ->
                CourseCard(
                    title = course.title,
                    description = course.description,
                    progress = course.progress,
                    onClick = { onCourseClick(course.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Cursos disponibles
            Text(
                text = "Disponibles",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            courses.filter { it.progress == 0f && !it.locked }.forEach { course ->
                CourseCard(
                    title = course.title,
                    description = course.description,
                    progress = course.progress,
                    onClick = { onCourseClick(course.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Cursos bloqueados
            Text(
                text = "Próximamente",
                style = MaterialTheme.typography.headlineSmall,
                color = TextGray,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            courses.filter { it.locked }.forEach { course ->
                CourseCard(
                    title = course.title,
                    description = course.description,
                    progress = course.progress,
                    onClick = { onCourseClick(course.id) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

// Data class para los cursos
data class CourseItem(
    val id: String,
    val title: String,
    val description: String,
    val progress: Float,
    val level: String,
    val xpReward: Int,
    val lessonsTotal: Int,
    val lessonsCompleted: Int,
    val image: String,
    val locked: Boolean = false
)