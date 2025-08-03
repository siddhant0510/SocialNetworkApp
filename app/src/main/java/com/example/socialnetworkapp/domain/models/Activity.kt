package com.example.socialnetworkapp.domain.models

import com.example.socialnetworkapp.domain.util.ActivityAction

data class Activity(
    val username: String,
    val actionType: ActivityAction,
    val formatedTime: String,
)
