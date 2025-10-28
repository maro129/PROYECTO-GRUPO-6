package com.example.cyberlearnapp.network.models

data class Progress(
    val name: String,
    val email: String,
    val level: Int,
    val xpTotal: Int,
    val streak: Int,
    val badges: List<String>,
    val lessonsCompleted: Int,
    val coursesCompleted: Int,
    val nextLevelXp: Int,
    val progressPercentage: Double
)