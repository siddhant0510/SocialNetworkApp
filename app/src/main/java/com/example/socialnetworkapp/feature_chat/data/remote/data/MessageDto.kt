package com.example.socialnetworkapp.feature_chat.data.remote.data

import com.example.socialnetworkapp.feature_chat.domain.model.Message
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formatedTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(Date(timestamp)),
            chatId = chatId
        )
    }
}
