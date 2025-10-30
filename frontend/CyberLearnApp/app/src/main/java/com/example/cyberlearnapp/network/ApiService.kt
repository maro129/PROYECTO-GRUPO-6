package com.example.cyberlearnapp.network

import com.example.cyberlearnapp.network.models.Progress
import com.example.cyberlearnapp.network.models.User
import com.example.cyberlearnapp.network.models.CompleteActivityRequest
import com.example.cyberlearnapp.network.models.CompleteActivityResponse
import com.example.cyberlearnapp.network.models.BadgeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @GET("api/user/progress")
    suspend fun getUserProgress(@Header("Authorization") token: String): Response<Progress>

    @POST("/api/user/complete-activity")
    suspend fun completeActivity(
        @Header("Authorization") token: String,
        @Body request: CompleteActivityRequest
    ): Response<CompleteActivityResponse>

    @GET("/api/user/badges")
    suspend fun getUserBadges(
        @Header("Authorization") token: String
    ): Response<BadgeResponse>
}


data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val user: User?
)