package com.example.socialnetworkapp.feature_post.presentation.person_list

import com.example.socialnetworkapp.domain.models.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
