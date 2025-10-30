package com.example.cyberlearnapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cyberlearnapp.ui.theme.*
import com.example.cyberlearnapp.viewmodel.AuthViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    //val currentUser = authViewModel.currentUser.value
    val currentUser by authViewModel.currentUser.collectAsState()
    //val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()

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
                text = "Perfil",
                style = MaterialTheme.typography.headlineLarge,
                color = TextWhite,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tu información y configuración",
                style = MaterialTheme.typography.bodyMedium,
                color = AccentCyan,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Tarjeta de información del usuario
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
                    // Avatar del usuario
                    Surface(
                        modifier = Modifier.size(80.dp),
                        shape = MaterialTheme.shapes.extraLarge,
                        color = AccentCyan
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "👤",
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                    }

                    Text(
                        text = currentUser?.name ?: "Usuario",
                        style = MaterialTheme.typography.headlineSmall,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = currentUser?.email ?: "usuario@ejemplo.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AccentCyan,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Nivel y XP
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = AccentCyan.copy(alpha = 0.1f),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Text(
                                    text = "5",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = AccentCyan,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Nivel",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextGray
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Text(
                                    text = "1250",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = AccentCyan,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "XP Total",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextGray
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(horizontal = 12.dp)
                            ) {
                                Text(
                                    text = "7",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Warning,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Racha",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextGray
                                )
                            }
                        }
                    }

                    // Barra de progreso de nivel
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nivel 5",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextWhite
                            )
                            Text(
                                text = "Nivel 6",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextGray
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(TextGray.copy(alpha = 0.3f), MaterialTheme.shapes.small)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(8.dp)
                                    .background(AccentCyan, MaterialTheme.shapes.small)
                            )
                        }

                        Text(
                            text = "Faltan 750 XP para el nivel 6",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextGray,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Button(
                        onClick = onEditProfile,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentCyan,
                            contentColor = TextWhite
                        )
                    ) {
                        Text("Editar perfil")
                    }
                }
            }

            // Estadísticas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📊 Estadísticas",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Lista de estadísticas
                    Column {
                        StatItem(
                            label = "Lecciones completadas",
                            value = "23",
                            color = AccentCyan
                        )
                        StatItem(
                            label = "Insignias obtenidas",
                            value = "8 / 15",
                            color = Success
                        )
                        StatItem(
                            label = "Progreso general",
                            value = "53%",
                            color = Warning
                        )
                        StatItem(
                            label = "Cursos en progreso",
                            value = "2",
                            color = AccentCyan
                        )
                        StatItem(
                            label = "Cursos completados",
                            value = "1",
                            color = Success
                        )
                    }
                }
            }

            // Actividad reciente
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📚 Actividad Reciente",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Lista de actividad
                    Column {
                        ActivityItem(
                            course = "Criptografía Básica",
                            activity = "Última actividad: Hoy, 15:30"
                        )
                        ActivityItem(
                            course = "Ethical Hacking",
                            activity = "Última actividad: Ayer, 18:45"
                        )
                        ActivityItem(
                            course = "Fundamentos de Seguridad",
                            activity = "Completado: Hace 3 días"
                        )
                    }
                }
            }

            // Configuración
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CardBg)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "⚙️ Configuración",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextWhite,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        onClick = onLogout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Danger,
                            contentColor = TextWhite
                        )
                    ) {
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    }
}

// Componente de item de estadística
@Composable
fun StatItem(label: String, value: String, color: androidx.compose.ui.graphics.Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextGray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

// Componente de item de actividad
@Composable
fun ActivityItem(course: String, activity: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = course,
            style = MaterialTheme.typography.bodyMedium,
            color = TextWhite,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = activity,
            style = MaterialTheme.typography.bodySmall,
            color = TextGray,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}