package com.example.cyberlearnapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
//import com.example.cyberlearnapp.navigation.NavGraph
import com.example.cyberlearnapp.navigation.Screens
import com.example.cyberlearnapp.ui.components.BottomNavigationBar
import com.example.cyberlearnapp.ui.screens.AuthScreen
import com.example.cyberlearnapp.ui.screens.DashboardScreen
import com.example.cyberlearnapp.ui.screens.AchievementsScreen
import com.example.cyberlearnapp.ui.screens.CoursesScreen
import com.example.cyberlearnapp.ui.screens.ProfileScreen
import com.example.cyberlearnapp.ui.theme.CyberLearnAppTheme
import com.example.cyberlearnapp.viewmodel.AuthViewModel
import com.example.cyberlearnapp.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberLearnAppTheme {
                CyberLearnApp()
            }
        }
    }
}

@Composable
fun CyberLearnApp() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()

    // Obtener la ruta actual para mostrar/ocultar BottomBar
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determinar si mostrar BottomBar (solo en pantallas principales)
    val showBottomBar = currentRoute in listOf(
        Screens.Dashboard.route,
        Screens.Courses.route,
        Screens.Achievements.route,
        Screens.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Auth.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla de Autenticación
            composable(Screens.Auth.route) {
                AuthScreen(
                    viewModel = authViewModel,
                    onLoginSuccess = {
                        userViewModel.loadUserProgress()

                        navController.navigate(Screens.Dashboard.route) {
                            popUpTo(Screens.Auth.route) { inclusive = true }
                        }
                    }
                )
            }

            // Pantallas principales CON BottomBar
            composable(Screens.Dashboard.route) {
                DashboardScreen(
                    userViewModel = userViewModel,
                    onCourseClick = { courseName ->
                        println("🎯 Curso seleccionado: $courseName")
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Screens.Courses.route) {
                CoursesScreen(  // ✅ PANTALLA REAL DE CURSOS
                    onCourseClick = { courseId ->
                        println("📚 Curso clickeado: $courseId")
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Screens.Achievements.route) {
                AchievementsScreen(  // ✅ PANTALLA REAL DE LOGROS
                    modifier = Modifier.fillMaxSize()
                )
            }

            composable(Screens.Profile.route) {
                ProfileScreen(
                    authViewModel = authViewModel,
                    userViewModel = userViewModel,
                    onEditProfile = {
                        println("✏️ Editar perfil")
                    },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Screens.Auth.route) {
                            popUpTo(Screens.Dashboard.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}