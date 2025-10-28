package com.example.cyberlearnapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyberlearnapp.ui.theme.*
import com.example.cyberlearnapp.network.models.Progress

@Composable
fun ProgressCard(
    progress: Progress,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CardBg)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "¡Hola, ${progress.name}! 👋",
                style = MaterialTheme.typography.headlineSmall,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Nivel ${progress.level} - Hacker novato",
                style = MaterialTheme.typography.bodyMedium,
                color = TextGray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Estadísticas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(
                    value = progress.xpTotal.toString(),
                    label = "XP Total"
                )
                StatItem(
                    value = progress.lessonsCompleted.toString(),
                    label = "Lecciones"
                )
                StatItem(
                    value = progress.badges.size.toString(),
                    label = "Insignias"
                )
            }

            // Racha
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Warning.copy(alpha = 0.1f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔥", style = MaterialTheme.typography.headlineMedium)
                    Text(
                        text = "Racha de ${progress.streak} días",
                        color = Warning,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "¡Sigue así!",
                        color = TextGray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = AccentCyan,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = TextGray
        )
    }
}