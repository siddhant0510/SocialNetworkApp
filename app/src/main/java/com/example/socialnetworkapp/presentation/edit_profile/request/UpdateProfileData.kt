package com.example.socialnetworkapp.presentation.edit_profile.request

import com.example.socialnetworkapp.domain.models.Skill

data class UpdateProfileData(
    val username: String,
    val bio: String,
    val gitHubUrl: String,
    val instagramUrl: String,
    val linkedInUrl: String,
    val skills: List<Skill>
)
