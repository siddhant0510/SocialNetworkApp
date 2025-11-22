package com.example.socialnetworkapp.feature_chat.data.remote.data

data class WsClientMessage(
    val toId: String,
    val text: String,
    val chatId: String?
)