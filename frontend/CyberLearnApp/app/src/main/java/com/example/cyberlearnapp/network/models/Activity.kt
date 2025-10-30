package com.example.cyberlearnapp.network.models

import com.google.gson.annotations.SerializedName

data class CompleteActivityRequest(
    @SerializedName("type") val type: String,
    @SerializedName("lesson_id") val lessonId: String? = null,
    @SerializedName("difficulty") val difficulty: Int = 1
)

data class CompleteActivityResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("activity_result") val activityResult: ActivityResult,
    @SerializedName("new_badges") val newBadges: List<String> = emptyList()
)

data class ActivityResult(
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("new_total_xp") val newTotalXp: Int,
    @SerializedName("new_level") val newLevel: Int,
    @SerializedName("streak") val streak: Int
)
