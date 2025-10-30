package com.example.cyberlearnapp.network.models

import com.google.gson.annotations.SerializedName

data class BadgeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("badges") val badges: List<UserBadge>
)

data class UserBadge(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("earned_at") val earnedAt: String?
)
