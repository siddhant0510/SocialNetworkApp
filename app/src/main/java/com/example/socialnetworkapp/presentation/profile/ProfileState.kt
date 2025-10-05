package com.example.socialnetworkapp.presentation.profile

import com.example.socialnetworkapp.domain.models.Profile

data class ProfileState(
    val profile: Profile? = null,
    val isLoading: Boolean = false
)
