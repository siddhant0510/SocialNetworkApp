package com.example.socialnetworkapp.feature_chat.data.repository

import coil.network.HttpException
import com.example.socialnetworkapp.R
import com.example.socialnetworkapp.feature_chat.data.remote.ChatApi
import com.example.socialnetworkapp.feature_chat.data.remote.ChatService
import com.example.socialnetworkapp.feature_chat.data.remote.data.WsClientMessage
import com.example.socialnetworkapp.feature_chat.di.ScarletInstance
import com.example.socialnetworkapp.feature_chat.domain.model.Chat
import com.example.socialnetworkapp.feature_chat.domain.model.Message
import com.example.socialnetworkapp.feature_chat.domain.repository.ChatRepository
import com.example.socialnetworkapp.utli.Resource
import com.example.socialnetworkapp.utli.UiText
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.OkHttpClient
import okio.IOException

class ChatRepositoryImpl(
    private val chatApi: ChatApi,
    private val okHttpClient: OkHttpClient
): ChatRepository {

    private var chatService: ChatService? = null

    override fun initialize() {
        chatService = ScarletInstance.getNewInstance(okHttpClient)
    }

    override suspend fun getChatForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi
                .getChatsForUser()
                .mapNotNull { it.toChat() }
            Resource.Success(data = chats)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getMessagesForChat(
        chatId: String,
        page: Int,
        pageSize: Int
    ): Resource<List<Message>> {
        return try {
            val messages = chatApi
                .getMessagesForChat(chatId, page, pageSize)
                .map { it.toMessage() }
            Resource.Success(data = messages)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun observeChatEvents(): Flow<WebSocket.Event> {
        return chatService?.observeEvents()
            ?.receiveAsFlow() ?: flow { }
    }

    override fun observeMessage(): Flow<Message> {
        return chatService
            ?.observeMessages()
            ?.receiveAsFlow()
            ?.map { it.toMessage() } ?: flow {  }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
        chatService?.sendMessage(
            WsClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }
}