package com.example.socialnetworkapp.presentation.activity.presentation

import com.example.socialnetworkapp.domain.models.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false
)
