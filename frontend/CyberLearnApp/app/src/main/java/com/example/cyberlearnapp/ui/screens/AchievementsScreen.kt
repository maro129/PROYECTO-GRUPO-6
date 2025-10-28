package com.example.cyberlearnapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyberlearnapp.ui.theme.*

@Composable
fun AchievementsScreen(
    modifier: Modifier = Modifier
) {
    // Datos mock de logros - luego conectaremos con ViewModel
    val achievements = listOf(
        AchievementItem(
            id = "first_lesson",
            title = "Primera Lección",
            description = "Completaste tu primera lección",
            icon = "📚",
            unlocked = true,
            progress = 100,
            xpReward = 50
        ),
        AchievementItem(
            id = "weekly_streak",
            title = "Racha Semanal",
            description = "7 días consecutivos aprendiendo",
            icon = "🔥",
            unlocked = true,
            progress = 100,
            xpReward = 100
        ),
        AchievementItem(
            id = "thousand_xp",
            title = "1000 XP",
            description = "Alcanzaste 1000 puntos de experiencia",
            icon = "💪",
            unlocked = true,
            progress = 100,
            xpReward = 200
        ),
        AchievementItem(
            id = "course_master",
            title = "Maestro del Curso",
            description = "Completa un curso al 100%",
            icon = "🎓",
            unlocked = false,
            progress = 65,
            xpReward = 150
        ),
        AchievementItem(
            id = "monthly_streak",
            title = "Racha Mensual",
            description = "30 días consecutivos aprendiendo",
            icon = "⭐",
            unlocked = false,
            progress = 23,
            xpReward = 300
        ),
        AchievementItem(
            id = "five_courses",
            title = "Aprendiz Avanzado",
            description = "Completa 5 cursos diferentes",
            icon = "🚀",
            unlocked = false,
            progress = 20,
            xpReward = 250
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
                text = "Logros",
                style = MaterialTheme.typography.headlineLarge,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tus conquistas en CyberLearn",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentCyan,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Progreso general
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "8 de 15 desbloqueados",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "53%",
                        style = MaterialTheme.typography.headlineMedium,
                        color = AccentCyan,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Barra de progreso general
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(TextGray.copy(alpha = 0.3f), MaterialTheme.shapes.small)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.53f)
                                .height(8.dp)
                                .background(AccentCyan, MaterialTheme.shapes.small)
                        )
                    }

                    Text(
                        text = "Progreso general",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextGray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            // Logros desbloqueados
            Text(
                text = "Desbloqueados",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            achievements.filter { it.unlocked }.forEach { achievement ->
                AchievementCard(achievement = achievement)
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Próximos logros
            Text(
                text = "En progreso",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            achievements.filter { !it.unlocked }.forEach { achievement ->
                AchievementCard(achievement = achievement)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

// Data class para logros
data class AchievementItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: String,
    val unlocked: Boolean,
    val progress: Int,
    val xpReward: Int
)

// Componente de tarjeta de logro
@Composable
fun AchievementCard(achievement: AchievementItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.unlocked) CardBg else CardBg.copy(alpha = 0.6f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono del logro
            Text(
                text = achievement.icon,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 16.dp)
            )

            // Información del logro
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (achievement.unlocked) TextWhite else TextGray,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (achievement.unlocked) TextGray else TextGray.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                if (!achievement.unlocked) {
                    // Barra de progreso para logros no desbloqueados
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .background(TextGray.copy(alpha = 0.3f), MaterialTheme.shapes.small)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(achievement.progress / 100f)
                                .height(6.dp)
                                .background(AccentCyan, MaterialTheme.shapes.small)
                        )
                    }

                    Text(
                        text = "${achievement.progress}% completado",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextGray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                } else {
                    // XP ganado para logros desbloqueados
                    Text(
                        text = "+${achievement.xpReward} XP",
                        style = MaterialTheme.typography.bodySmall,
                        color = AccentCyan,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Badge de estado
            Surface(
                shape = MaterialTheme.shapes.small,
                color = if (achievement.unlocked) Success else TextGray.copy(alpha = 0.3f)
            ) {
                Text(
                    text = if (achievement.unlocked) "Desbloqueado" else "Bloqueado",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (achievement.unlocked) TextWhite else TextGray,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}