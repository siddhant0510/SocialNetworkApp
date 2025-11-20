package com.example.socialnetworkapp.feature_chat.presentation.message

sealed class MessageEvent {
    object SendMessage: MessageEvent()
    data class EnterMessage(val message: String): MessageEvent()
}