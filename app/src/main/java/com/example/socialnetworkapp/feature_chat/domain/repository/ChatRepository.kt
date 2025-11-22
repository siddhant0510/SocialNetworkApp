package com.example.socialnetworkapp.feature_chat.domain.repository

import com.example.socialnetworkapp.feature_chat.domain.model.Chat
import com.example.socialnetworkapp.feature_chat.domain.model.Message
import com.example.socialnetworkapp.utli.Resource
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun getChatForUser(): Resource<List<Chat>>

    fun observeChatEvents(): Flow<WebSocket.Event>

    fun observeMessage(): Flow<Message>

    fun sendMessage(toId: String, text: String, chatId: String?)
}