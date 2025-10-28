package com.example.cyberlearnapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.cyberlearnapp.ui.screens.AchievementsScreen
import com.example.cyberlearnapp.ui.screens.CoursesScreen
import com.example.cyberlearnapp.ui.screens.DashboardScreen
import com.example.cyberlearnapp.ui.screens.ProfileScreen
import com.example.cyberlearnapp.viewmodel.AuthViewModel
import com.example.cyberlearnapp.viewmodel.UserViewModel

fun NavGraphBuilder.mainGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel
) {
    navigation(
        startDestination = Screens.Dashboard.route,
        route = Screens.Main.route
    ) {
        composable(Screens.Dashboard.route) {
            DashboardScreen(
                userViewModel = userViewModel,
                onCourseClick = { courseName ->
                    println("🎯 Curso seleccionado: $courseName")
                }
            )
        }

        composable(Screens.Courses.route) {
            CoursesScreen(
                onCourseClick = { courseId ->
                    println("📚 Curso clickeado: $courseId")
                }
            )
        }

        composable(Screens.Achievements.route) {
            AchievementsScreen()
        }

        composable(Screens.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel,
                onEditProfile = {
                    println("✏️ Editar perfil")
                },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Screens.Auth.route) {
                        popUpTo(Screens.Main.route) { inclusive = true }
                    }
                }
            )
        }
    }
}