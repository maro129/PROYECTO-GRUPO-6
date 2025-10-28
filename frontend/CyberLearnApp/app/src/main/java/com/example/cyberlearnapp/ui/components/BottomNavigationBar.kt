package com.example.cyberlearnapp.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cyberlearnapp.navigation.Screens
import com.example.cyberlearnapp.ui.theme.*

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = CardBg,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon("🏠", contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == Screens.Dashboard.route,
            onClick = {
                navController.navigate(Screens.Dashboard.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                selectedTextColor = AccentCyan,
                unselectedIconColor = TextGray,
                unselectedTextColor = TextGray,
                indicatorColor = CardBg
            )
        )

        NavigationBarItem(
            icon = { Icon("📚", contentDescription = "Cursos") },
            label = { Text("Cursos") },
            selected = currentRoute == Screens.Courses.route,
            onClick = {
                navController.navigate(Screens.Courses.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                selectedTextColor = AccentCyan,
                unselectedIconColor = TextGray,
                unselectedTextColor = TextGray,
                indicatorColor = CardBg
            )
        )

        NavigationBarItem(
            icon = { Icon("🏆", contentDescription = "Logros") },
            label = { Text("Logros") },
            selected = currentRoute == Screens.Achievements.route,
            onClick = {
                navController.navigate(Screens.Achievements.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                selectedTextColor = AccentCyan,
                unselectedIconColor = TextGray,
                unselectedTextColor = TextGray,
                indicatorColor = CardBg
            )
        )

        NavigationBarItem(
            icon = { Icon("👤", contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = currentRoute == Screens.Profile.route,
            onClick = {
                navController.navigate(Screens.Profile.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                selectedIconColor = AccentCyan,
                selectedTextColor = AccentCyan,
                unselectedIconColor = TextGray,
                unselectedTextColor = TextGray,
                indicatorColor = CardBg
            )
        )
    }
}

// Componente Icon simple para emojis
@Composable
fun Icon(emoji: String, contentDescription: String) {
    Text(text = emoji)
}