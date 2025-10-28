package com.example.cyberlearnapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment  // ← AGREGAR ESTE IMPORT
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cyberlearnapp.ui.theme.*

@Composable
fun CourseCard(
    title: String,
    description: String,
    progress: Float,
    onClick: () -> Unit,
    locked: Boolean = false
) {
    // ENFOQUE CORRECTO: Solo aplicar el Card con onClick si no está bloqueado
    if (!locked) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBg),
            onClick = onClick  // ← Solo cuando no está bloqueado
        ) {
            CourseCardContent(title, description, progress, onClick, locked)
        }
    } else {
        // Cuando está bloqueado, Card sin onClick
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CardBg.copy(alpha = 0.5f))
        ) {
            CourseCardContent(title, description, progress, onClick, locked)
        }
    }
}

// Extraer el contenido a una función separada
@Composable
private fun CourseCardContent(
    title: String,
    description: String,
    progress: Float,
    onClick: () -> Unit,
    locked: Boolean
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // Header del curso
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (locked) TextGray else TextWhite,
                fontWeight = FontWeight.Bold
            )

            if (locked) {
                Text(
                    text = "🔒",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = if (locked) TextGray.copy(alpha = 0.7f) else TextGray,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (progress > 0 && !locked) {
            ProgressBar(progress = progress)
            Text(
                text = "${(progress * 100).toInt()}% completado",
                style = MaterialTheme.typography.bodySmall,
                color = AccentCyan,
                modifier = Modifier.padding(top = 4.dp)
            )
        } else if (!locked) {
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentCyan,
                    contentColor = TextWhite
                )
            ) {
                Text("Iniciar curso")
            }
        } else {
            Text(
                text = "Completa cursos anteriores para desbloquear",
                style = MaterialTheme.typography.bodySmall,
                color = TextGray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ProgressBar(progress: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(TextGray.copy(alpha = 0.3f), MaterialTheme.shapes.small)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(8.dp)
                .background(AccentCyan, MaterialTheme.shapes.small)
        )
    }
}