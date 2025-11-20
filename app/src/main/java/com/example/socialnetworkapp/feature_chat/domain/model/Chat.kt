package com.example.socialnetworkapp.feature_chat.domain.model

data class Chat(
    val username: String,
    val remoteUserProfileUrl: String,
    val lastMessage: String,
    val lastMessageFormatedTime: String
)
