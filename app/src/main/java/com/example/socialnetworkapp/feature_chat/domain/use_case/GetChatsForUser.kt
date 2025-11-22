package com.example.socialnetworkapp.feature_chat.domain.use_case

import com.example.socialnetworkapp.feature_chat.domain.model.Chat
import com.example.socialnetworkapp.feature_chat.domain.repository.ChatRepository
import com.example.socialnetworkapp.utli.Resource

class GetChatsForUser(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(): Resource<List<Chat>> {
        return repository.getChatForUser()
    }
}