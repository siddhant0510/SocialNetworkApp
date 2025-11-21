package com.example.socialnetworkapp.feature_chat.presentation.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.ImageLoader
import com.example.socialnetworkapp.feature_chat.domain.model.Chat
import com.example.socialnetworkapp.presentation.theme.SpaceLarge
import com.example.socialnetworkapp.presentation.theme.SpaceMedium
import com.example.socialnetworkapp.utli.Screen
import kotlin.collections.listOf


@Composable
fun ChatScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
){
    val chats = remember {
        listOf(
            Chat(
                username = "Sid Rox",
                remoteUserProfileUrl = "",
                lastMessage = "Hello, this is the last message for sid",
                lastMessageFormatedTime = "10:00 am"
            )
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chats) { chat ->
                ChatItem(
                    item = chat,
                    imageLoader = imageLoader,
                    onItemClick = {
                        onNavigate(Screen.MessageScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
            }
        }
    }
}