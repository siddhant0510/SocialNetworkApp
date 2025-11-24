package com.example.socialnetworkapp.feature_chat.data.remote.data

import com.example.socialnetworkapp.feature_chat.domain.model.Message
import java.text.DateFormat
import java.util.Date

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
            formatedTime = DateFormat
                .getDateInstance(DateFormat.DEFAULT)
                .format(Date(timestamp)),
            chatId = chatId
        )
    }
}
