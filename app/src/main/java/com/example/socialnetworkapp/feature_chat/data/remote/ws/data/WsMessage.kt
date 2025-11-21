package com.example.socialnetworkapp.feature_chat.data.remote.ws.data

import com.example.socialnetworkapp.feature_chat.domain.model.Message
import java.text.DateFormat
import java.util.Date

data class WsMessage(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val charId: String?
) {
    fun toMessage(): Message {
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formattedTime = DateFormat
                .getDateInstance(DateFormat.DEFAULT)
                .format(Date(timestamp)),
            chatId = charId
        )
    }
}
