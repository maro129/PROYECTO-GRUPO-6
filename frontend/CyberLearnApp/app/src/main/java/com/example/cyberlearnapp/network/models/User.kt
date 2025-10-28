package com.example.cyberlearnapp.network.models

data class User(
    val email: String,
    val name: String,
    val token: String = ""
)