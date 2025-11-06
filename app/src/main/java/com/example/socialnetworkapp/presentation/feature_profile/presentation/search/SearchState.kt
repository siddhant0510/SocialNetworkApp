package com.example.socialnetworkapp.presentation.feature_profile.presentation.search

import com.example.socialnetworkapp.domain.models.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
